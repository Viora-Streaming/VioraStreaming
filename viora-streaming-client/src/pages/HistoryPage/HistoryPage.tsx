import {useHistory} from "../../hooks/useHistory.ts";
import {Box, Button, LinearProgress, Stack, Typography} from "@mui/material";
import {useNavigate} from "react-router";
import type {History} from "../../types/historyTypes.ts";
import {API_PAGE} from "../../constants/routingConstants.ts";
import {EmptyHistoryPage} from "./components/EmptyHistoryPage.tsx";

const SEGMENT_DURATION_SECONDS = 10;

function getProgressInfo(history: History): {
  progressPercent: number;
  label: string;
  finished: boolean;
} {
  const totalSeconds = history.movie.durationInMinutes * 60;
  const watchedSeconds = history.lastWatchedAt * SEGMENT_DURATION_SECONDS;
  const progressPercent = Math.min((watchedSeconds / totalSeconds) * 100, 100);
  const finished = watchedSeconds >= totalSeconds;

  if (finished) {
    return {progressPercent: 100, label: "Finished", finished: true};
  }

  const remainingSeconds = totalSeconds - watchedSeconds;
  const remainingMinutes = Math.round(remainingSeconds / 60);

  const label =
      remainingMinutes < 1
          ? "Less than a minute left"
          : remainingMinutes === 1
              ? "1 min left"
              : `${remainingMinutes} min left`;

  return {progressPercent, label, finished: false};
}

export function HistoryPage() {
  const {histories, isLoading} = useHistory();
  const navigate = useNavigate();

  if (isLoading) {
    return <>Loading...</>;
  }

  if (!isLoading && histories.length === 0) {
    return (
        <EmptyHistoryPage onBrowse={() => navigate(API_PAGE.Movies)}/>
    )
  }

  return (
      <Stack sx={{p: "50px 32px"}} spacing="32px">
        <Box>
          <Typography variant="h3" sx={{fontWeight: "bold"}}>
            Watch History
          </Typography>
          <Typography variant="body1" sx={{fontWeight: "bold", color: "#CCC3D8"}}>
            Pick up where you left off
          </Typography>
        </Box>

        <Stack spacing="16px">
          {histories.map((history) => {
            const {progressPercent, label, finished} = getProgressInfo(history);

            return (
                <Box
                    key={history.movie.id}
                    sx={{
                      display: "flex",
                      alignItems: "center",
                      bgcolor: "#201F21",
                      p: "20px 16px",
                      borderRadius: "12px",
                    }}
                >
                  <Box
                      sx={{
                        flexShrink: 0,
                        backgroundColor: "secondary.main",
                        backgroundImage: `url(${history.movie.poster})`,
                        backgroundSize: "cover",
                        backgroundPosition: "center",
                        borderRadius: "8px",
                        height: "85px",
                        width: "150px",
                      }}
                  />

                  <Stack sx={{
                    flex: 1,
                    ml: "24px",
                    minWidth: 0,
                    position: "relative"
                  }}>


                    <Stack direction="row" sx={{justifyContent: "space-between"}}>
                      <Stack sx={{minWidth: 0, flex: 1}}>
                        <Typography variant="h6" sx={{fontWeight: "bold"}} noWrap>
                          {history.movie.title}
                        </Typography>

                        <Stack direction="row" spacing="6px">
                          <Typography variant="body2" sx={{color: "#CCC3D8"}}>
                            {history.movie.genres[0].name}
                          </Typography>
                          <Typography variant="body2" sx={{color: "text.secondary"}}>·</Typography>
                          <Typography variant="body2" sx={{color: "text.secondary"}}>
                            {label}
                          </Typography>
                        </Stack>
                      </Stack>

                      <Button
                          variant="contained"
                          color="primary"
                          onClick={() =>
                              navigate(`/${API_PAGE.Movies}/${history.movie.id}/player`)
                          }
                          sx={{
                            ml: "24px",
                            flexShrink: 0,
                            borderRadius: "12px",
                            p: "12px 32px",
                            fontWeight: "bold",
                            textTransform: "none",
                          }}
                      >
                        {finished ? "Watch Again" : "Resume"}
                      </Button>
                    </Stack>

                    <LinearProgress
                        variant="determinate"
                        value={progressPercent}
                        sx={{
                          width: "100%",
                          top: "80px",
                          height: "4px",
                          borderRadius: 2,
                          position: "absolute",
                          bgcolor: "rgba(255,255,255,0.1)",
                          "& .MuiLinearProgress-bar": {
                            borderRadius: 2,
                            bgcolor: finished ? "success.main" : "primary.main",
                          },
                        }}
                    />
                  </Stack>
                </Box>
            );
          })}
        </Stack>
      </Stack>
  );
}