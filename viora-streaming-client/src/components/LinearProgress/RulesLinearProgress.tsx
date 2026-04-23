import {Box, LinearProgress, Stack, Typography} from "@mui/material";
import {useTheme} from "@mui/material/styles";

type RulesLinearProgressProps = {
  rules: number;
  fullFilled: number;
  message?: string;
}

export function RulesLinearProgress({rules, fullFilled, message}: RulesLinearProgressProps) {
  const theme = useTheme();

  return (
      <Stack spacing={"5px"}>
        <Stack spacing={"8px"} direction={"row"}>
          {Array.from({length: rules}).map((_, i) => (
              <LinearProgress
                  key={i}
                  variant="determinate"
                  value={i < fullFilled ? 100 : 0}
                  sx={{
                    width: "90px",
                    height: "4px",
                    backgroundColor: theme.palette.text.disabled,
                    "& .MuiLinearProgress-bar": {
                      backgroundColor: theme.palette.primary.main,
                    }
                  }}
              />
          ))}
        </Stack>
        {message && (
            <Typography color={"textDisabled"} variant={"subtitle2"}>
              {message}
            </Typography>
        )}
      </Stack>
  )
}