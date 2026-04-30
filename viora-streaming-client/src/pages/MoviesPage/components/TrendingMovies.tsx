import {Box, Button, CircularProgress, Stack, Typography} from "@mui/material";
import {Movies} from "./Movies.tsx";
import NoMoviesImg from "../../../assets/no-movies.png";
import {useTrendingMovies} from "../../../hooks/useMovies.ts";
import {useDispatch} from "react-redux";
import type {AppDispatch} from "../../../store/store.ts";
import {resetFilters} from "../../../store/filterSlice.ts";

export function TrendingMovies() {

  const {movies, isLoading, isError, hasMore, loaderRef} = useTrendingMovies();
  const dispatch = useDispatch<AppDispatch>();

  return (
      <>
        <Stack
            spacing="24px"
            sx={{
              minHeight: "400px",
              textAlign: "center",
              mb: '60px',
              alignItems: "center",
              justifyContent: "center"
            }}
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
          <Typography variant="h4" sx={{fontWeight: 800, fontSize: "36px"}}>
            No movies match
          </Typography>
          <Typography variant="body1" color="text.secondary"
                      sx={{maxWidth: "500px", fontSize: "16px", paddingBottom: "40px"}}>
            We couldn't find any titles that match your current selection. Try broadening your
            criteria or resetting your search.
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
              onClick={() => dispatch(resetFilters())}
          >
            Clear all filters
          </Button>
        </Stack>

        <Typography variant="h6" sx={{fontWeight: 700, mb: "24px"}}>
          Trending instead
        </Typography>
        <Movies movies={movies}/>

        {/* ← Sentinel: hook watches this div */}
        <Box ref={loaderRef} sx={{py: "32px", display: "flex", justifyContent: "center"}}>
          {isLoading && <CircularProgress size={28}/>}
          {isError && (
              <Typography color="error">Failed to load movies. Please try again.</Typography>
          )}
          {!hasMore && !isLoading && (
              <Typography color="text.secondary">You've reached the end</Typography>
          )}
        </Box>
      </>
  )
}