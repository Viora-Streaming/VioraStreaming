import {Box, Chip, Grid, Typography} from "@mui/material";
import type {MovieSummary} from "../../../types/movieTypes.ts";

type MoviesProps = {
  movies: MovieSummary[]
}

export function Movies({movies}: MoviesProps) {
  return (
      <Grid container spacing={3}>
        {movies.map((movie) => (
            <Grid key={movie.id}>
              <Box
                  sx={{
                    aspectRatio: "2/3",
                    minHeight: "300px",
                    backgroundColor: "secondary.main",
                    backgroundImage: `url(${movie.poster})`,
                    backgroundSize: "cover",
                    backgroundPosition: "center",
                    borderRadius: "12px",
                    position: "relative",
                    overflow: 'hidden',
                    '&:hover': {
                      '& .movie-info': {
                        bottom: 0,
                      }
                    }
                  }}
              >
                <Chip
                    label={movie.rating.toFixed(1)}
                    sx={{
                      position: "absolute",
                      top: "12px",
                      right: "12px",
                      backgroundColor: "primary.main",
                      color: "text.primary",
                      fontWeight: "bold",
                      borderRadius: '6px',
                      height: '24px',
                      '& .MuiChip-label': {px: '8px'}
                    }}
                />
                <Box className="movie-info" sx={{
                  position: 'absolute',
                  bottom: '-100%',
                  left: 0,
                  width: '100%',
                  p: '16px',
                  background: 'linear-gradient(to top, rgba(0,0,0,0.8), transparent)',
                  transition: '0.3s ease',
                }}>
                  <Typography variant="subtitle1"
                              sx={{fontWeight: 'bold'}}>{movie.title}</Typography>
                  <Typography variant="body2"
                              color="text.secondary">{movie.releaseDate}</Typography>
                </Box>
              </Box>
            </Grid>
        ))}
      </Grid>
  )
}