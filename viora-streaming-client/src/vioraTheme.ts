import {createTheme} from "@mui/material/styles";

declare module "@mui/material/styles" {
  interface Palette {
    customColors: {
      iconBoxBg: string;
    };
  }
  interface PaletteOptions {
    customColors?: {
      iconBoxBg: string;
    };
  }
}

export const vioraTheme = createTheme({
  palette: {
    mode: "dark",
    primary: {
      main: "#7C3AED"
    },
    secondary: {
      main: "#1A1A1F",
    },
    background: {
      default: "#0D0D0F",
      paper: "#16161A",
    },
    text: {
      primary: "#FFFFFF",
      secondary: "#9CA3AF",
      disabled: "#6B7280"
    },
    error: {
      main: "#EF4444",
    },
    success: {
      main: "#22C55E"
    }
  },
});
