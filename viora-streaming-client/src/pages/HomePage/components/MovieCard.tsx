import {Box} from "@mui/material";

type MovieCardProps = {
  poster: string;
}

export function MovieCard({poster}: MovieCardProps) {
  return <Box
      sx={{
        aspectRatio: "2/3",
        height: "240px",
        width: "160px",
        backgroundColor: "secondary.main",
        backgroundImage: `url(${poster})`,
        backgroundSize: "cover",
        backgroundPosition: "center",
        borderRadius: "12px",
        overflow: 'hidden'
      }}
  />
}