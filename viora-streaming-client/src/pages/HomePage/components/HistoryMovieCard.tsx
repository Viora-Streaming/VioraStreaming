import {Box, LinearProgress, Stack, Typography} from "@mui/material";
import {MovieCard} from "./MovieCard.tsx";
import type {History} from "../../../types/historyTypes.ts";
import {getProgressInfo} from "../../../utils/historyHelpers.ts";

type HistoryMovieCardProps = {
  history: History;
  onClick: () => void;
}

export function HistoryMovieCard({history, onClick}: HistoryMovieCardProps) {
  const {progressPercent, label, finished} = getProgressInfo(history);

  return (
      <Stack onClick={onClick} spacing="16px" sx={{
        cursor: "pointer",
      }}>
        <Box sx={{position: "relative"}}>
          <MovieCard poster={history.movie.poster}/>
          <LinearProgress
              variant="determinate"
              value={progressPercent}
              sx={{
                width: "100%",
                bottom: "0",
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
        </Box>

        <Stack spacing="4px">
          <Typography variant="subtitle2" sx={{
            fontWeight: "bold",
            textWrap: "nowrap",
            overflow: "hidden",
            textOverflow: "ellipsis",
            maxWidth: "120px",
          }}>
            {history.movie.title}
          </Typography>

          <Typography variant="body2" sx={{
            color: "text.secondary",
          }}>
            {label}
          </Typography>

        </Stack>
      </Stack>
  )
}