import {Box, Chip, Grid, Stack, Typography} from "@mui/material";
import type {MovieSummary} from "../../../types/movieTypes.ts";
import {useTheme} from "@mui/material/styles";

type MoviesProps = {
  movies: MovieSummary[]
}

export function Movies({movies}: MoviesProps) {
  const theme = useTheme();

  return (
      <Grid container spacing={3}>
        {movies.map((movie) => (
            <Grid key={movie.id}>
              <Box
                  sx={{
                    aspectRatio: "2/3",
                    minHeight: "370px",
                    backgroundColor: "secondary.main",
                    backgroundImage: `url(${movie.poster})`,
                    backgroundSize: "cover",
                    backgroundPosition: "center",
                    borderRadius: "12px",
                    position: "relative",
                    overflow: 'hidden'
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
              </Box>
              <Box className="movie-info" sx={{
                width: '100%',
                pt: '10px',
              }}>
                <Box sx={{mb: "5px"}}>
                  <Typography variant="subtitle1"
                              sx={{fontWeight: 'bold'}}>{movie.title}</Typography>
                  <Typography variant="body2"
                              sx={{
                                fontWeight: 'bold',
                                color: theme.palette.text.secondary
                              }}>
                    {movie.genres[0].name}
                  </Typography>
                </Box>
                <Typography variant="body2"
                            color="text.secondary">{movie.releaseDate.split("-")[0]}</Typography>
              </Box>
            </Grid>
        ))}
      </Grid>
  )
}