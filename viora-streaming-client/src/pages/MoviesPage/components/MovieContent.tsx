import {Box, Typography, Stack, Chip, Button, Grid, CircularProgress} from "@mui/material";
import ClearIcon from '@mui/icons-material/Clear';
import NoMoviesImg from "../../../assets/no-movies.png";
import {useInfiniteMovies} from "../../../hooks/useMovies.ts";

export function MovieContent() {
  const { movies, isLoading, isError, hasMore, loaderRef } = useInfiniteMovies();

  return (
      <Box sx={{ flex: 1, p: "40px", backgroundColor: "background.default" }}>
        {/* Active Filters | For now just a mock */}
        <Stack direction="row" spacing="12px" sx={{ mb: "40px", alignItems: "center" }}>
          <Typography variant="body2" color="text.secondary">
            Active Filters:
          </Typography>
          <Chip
              label="Science Fiction"
              onDelete={() => {}}
              deleteIcon={<ClearIcon style={{color: 'white', fontSize: '16px'}} />}
              sx={{
                backgroundColor: 'secondary.main',
                color: 'text.primary',
                borderRadius: '8px',
                '& .MuiChip-deleteIcon': { color: 'text.primary' }
              }}
          />
          <Chip
              label="Rating 8.0+"
              onDelete={() => {}}
              deleteIcon={<ClearIcon style={{color: 'white', fontSize: '16px'}} />}
              sx={{
                backgroundColor: 'secondary.main',
                color: 'text.primary',
                borderRadius: '8px',
                '& .MuiChip-deleteIcon': { color: 'text.primary' }
              }}
          />
          <Button variant="text" color="primary" sx={{ textTransform: "none", p: 0 }}>
            Clear all
          </Button>
        </Stack>

        {/* No Movies Match | For now just by default */}
        <Stack
            spacing="24px"
            sx={{ minHeight: "400px", textAlign: "center", mb: '60px', alignItems: "center", justifyContent: "center" }}
        >
          <Box
              component="img"
              src={NoMoviesImg}
              alt="No movies match found"
              sx={{
                width: "96px",
                height: "auto",
                display: "block",
                mb: "20px"
              }}
          />
          <Typography variant="h4" sx={{ fontWeight: 800, fontSize: "36px" }}>
            No movies match
          </Typography>
          <Typography variant="body1" color="text.secondary" sx={{ maxWidth: "500px", fontSize: "16px", paddingBottom: "40px" }}>
            We couldn't find any titles that match your current selection. Try broadening your criteria or resetting your search.
          </Typography>
          <Button
              variant="contained"
              color="primary"
              sx={{
                textTransform: "none",
                borderRadius: "12px",
                p: "12px 24px",
                fontWeight: "bold",
              }}
          >
            Clear all filters
          </Button>
        </Stack>

        {/* Trending instead */}
        <Typography variant="h6" sx={{ fontWeight: 700, mb: "24px" }}>
          Trending instead
        </Typography>
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
                        '& .MuiChip-label': { px: '8px'}
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
                    <Typography variant="subtitle1" sx={{fontWeight: 'bold'}}>{movie.title}</Typography>
                    <Typography variant="body2" color="text.secondary">{movie.releaseDate}</Typography>
                  </Box>
                </Box>
              </Grid>
          ))}
        </Grid>

        {/* ← Sentinel: hook watches this div */}
        <Box ref={loaderRef} sx={{ py: "32px", display: "flex", justifyContent: "center" }}>
          {isLoading && <CircularProgress size={28} />}
          {isError && (
              <Typography color="error">Failed to load movies. Please try again.</Typography>
          )}
          {!hasMore && !isLoading && (
              <Typography color="text.secondary">You've reached the end</Typography>
          )}
        </Box>

      </Box>
  );
}