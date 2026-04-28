import { Box } from "@mui/material";
import { FilterPanel } from "./components/FilterPanel.tsx";
import { MovieContent } from "./components/MovieContent.tsx";

export default function MoviesPage() {
  return (
      <Box
          sx={{
            minHeight: "100vh",
            display: "flex",
            flexDirection: "column",
            backgroundColor: "background.default",
          }}
      >
        <Box sx={{ display: "flex", flex: 1 }}>
          <FilterPanel />
          <MovieContent />
        </Box>
      </Box>
  );
}