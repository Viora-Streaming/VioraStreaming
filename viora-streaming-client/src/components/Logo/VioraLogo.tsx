import { Typography, type TypographyProps } from "@mui/material";

interface VioraLogoProps extends TypographyProps {
  fontSize?: string | number;
}

export function VioraLogo({variant = "h1", fontSize, sx, ...props}: VioraLogoProps) {
  return (
      <Typography
          variant={variant}
          {...props}
          sx={{
            color: "primary.main",
            fontWeight: 800,
            ...{ fontSize },
            ...sx,
          }}
      >
        Viora
      </Typography>
  );
}