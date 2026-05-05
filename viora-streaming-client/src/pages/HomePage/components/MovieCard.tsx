import {Box} from "@mui/material";

type MovieCardProps = {
  poster: string;
};

export function MovieCard({poster}: MovieCardProps) {
  return (
      <Box
          sx={{
            // aspect ratio drives the height — no fixed width/height needed
            aspectRatio: "2/3",
            width: "100%",
            backgroundColor: "secondary.main",
            backgroundImage: `url(${poster})`,
            backgroundSize: "cover",
            backgroundPosition: "center",
            borderRadius: "12px",
            overflow: "hidden",
          }}
      />
  );
}