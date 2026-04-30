import {Box, Typography, Stack, Chip, Button, Grid, CircularProgress} from "@mui/material";
import ClearIcon from '@mui/icons-material/Clear';
import NoMoviesImg from "../../../assets/no-movies.png";
import {useInfiniteMovies} from "../../../hooks/useMovies.ts";
import {useFilterPanel} from "../../../hooks/useFilterPanel.ts";
import {ActiveFilters} from "./ActiveFilters.tsx";
import {Movies} from "./Movies.tsx";
import {TrendingMovies} from "./TrendingMovies.tsx";

export function MovieContent() {
  const {movies, isLoading, isError, hasMore, loaderRef} = useInfiniteMovies();

  return (
      <Box sx={{flex: 1, p: "40px", backgroundColor: "background.default"}}>
        <ActiveFilters/>

        <Movies movies={movies}/>

        {movies.length === 0 && !isLoading && (
            <TrendingMovies />
        )}

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

      </Box>
  );
}