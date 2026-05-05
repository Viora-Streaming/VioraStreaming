import type {MovieSummary} from "../../../types/movieTypes.ts";
import {MovieCard} from "./MovieCard.tsx";
import {Box, Stack, Typography} from "@mui/material";

type TrendingMovieCardProps = {
  movie: MovieSummary;
  onClick?: () => void;
}

export function TrendingMovieCard({movie, onClick}: TrendingMovieCardProps) {
  return (
      <Stack onClick={onClick} spacing="16px" sx={{
        cursor: "pointer",
      }}>
        <MovieCard poster={movie.poster}/>

        <Stack spacing="4px">
          <Typography variant="subtitle2" sx={{
            fontWeight: "bold",
            textWrap: "nowrap",
            overflow: "hidden",
            textOverflow: "ellipsis",
            maxWidth: "120px",
          }}>
            {movie.title}
          </Typography>
          <Stack direction="row" spacing="2px">
            <Typography variant="body2" sx={{
              color: "text.secondary",
            }}>
              {movie.genres[0].name}
            </Typography>
            <Typography variant="body2" sx={{color: "text.secondary"}}>·</Typography>
            <Typography variant="body2" sx={{color: "text.secondary"}}>
              {movie.releaseDate.split('-').at(0)}
            </Typography>
          </Stack>
        </Stack>
      </Stack>
  )
}