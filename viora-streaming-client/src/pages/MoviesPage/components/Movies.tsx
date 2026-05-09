import {Box, Chip, Grid, Typography} from "@mui/material";
import type {MovieSummary} from "../../../types/movieTypes.ts";
import {useTheme} from "@mui/material/styles";
import {useNavigate} from "react-router-dom";
import {API_PAGE} from "../../../constants/routingConstants.ts";

type MoviesProps = {
  movies: MovieSummary[]
}

export function Movies({movies}: MoviesProps) {
  const theme = useTheme();
  const navigate = useNavigate();

  return (
      <Grid container spacing={3}>
        {movies.map((movie) => (
            <Grid
                key={movie.id}
                onClick={() => navigate(`${API_PAGE.Movies}/${movie.id}`)}
                sx={{
                  cursor: 'pointer',
                  maxWidth: "250px"
                }}
            >
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
                              sx={{
                                fontWeight: 'bold',
                                textWrap: "noWrap",
                                maxWidth: "220px",
                                overflow: "hidden",
                                textOverflow: "ellipsis"
                              }}
                  >{movie.title}</Typography>
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