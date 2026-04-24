import { Box, InputBase } from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";

export function SearchField() {
  return (
      <Box
          sx={{
            display: "flex",
            alignItems: "center",
            backgroundColor: "secondary.main",
            borderRadius: "12px",
            p: "8px 16px",
            width: "100%",
            minWidth: "300px",
            maxWidth: "700px",
          }}
      >
        <SearchIcon sx={{ color: "text.secondary", mr: "12px" }} />
        <InputBase
            placeholder="Search movies, genres..."
            fullWidth
            sx={{
              color: "text.primary",
              fontSize: "1.0rem",
              "&::placeholder": {
                color: "text.secondary",
                opacity: 1,
              },
            }}
        />
      </Box>
  );
}