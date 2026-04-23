import {Box} from "@mui/material";
import {VioraLogo} from "../Logo/VioraLogo.tsx";
import * as React from "react";


type WelcomePageContainerProps = {
  children?: React.ReactNode;
};

export function WelcomePageContainer(props: WelcomePageContainerProps) {
  return (
      <Box
          sx={{
            minHeight: "100vh",
            backgroundColor: "background.default",
            display: "flex",
            flexDirection: "row",
            alignItems: "center",
            justifyContent: "center",
          }}
      >
        <Box sx={{
          width: "900px",
          display: "flex",
          alignItems: "center",
          justifyContent: "space-between",
        }}>
          <VioraLogo/>
          {props.children}
        </Box>
      </Box>
  );
}