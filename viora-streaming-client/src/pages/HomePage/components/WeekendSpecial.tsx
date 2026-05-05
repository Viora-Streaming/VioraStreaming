import type {MovieSummary} from "../../../types/movieTypes.ts";
import {Box, Stack, Typography} from "@mui/material";

type WeekendSpecialProps = {
  movie: MovieSummary;
  onClick?: () => void;
}

export function WeekendSpecial({movie, onClick}: WeekendSpecialProps) {
  return (
      <Box sx={{
        p: "32px",
        bgcolor: "background.paper",
        borderRadius: "12px",
        minHeight: "400px",
        maxWidth: "523px",
        cursor: "pointer",
      }} onClick={onClick}>
        <Stack spacing="10px" sx={{
          height: "100%",
          justifyContent: "end",
        }}>
          <Typography sx={{
            color: "primary.main",
            textTransform: "uppercase",
          }}>Weekend Special</Typography>
          <Typography variant="h4" sx={{
            fontWeight: "bold",
          }}>
            {movie.title}
          </Typography>
          <Typography variant="body2" sx={{color: "text.secondary"}}>
            {movie.plot}
          </Typography>
        </Stack>
      </Box>
  )
}