import {Box, InputBase, Typography} from "@mui/material";
import * as React from "react";

type SettingsFieldProps = {
  fullWidth?: boolean;
  label: string;
  placeholder?: string;
  value?: string;
  name?: string;
  onChange?: (e: string) => void;
  onBlur?: (e: React.FocusEvent<HTMLInputElement>) => void;
  disabled?: boolean;
}

export function SettingsField({
                                fullWidth,
                                label,
                                placeholder,
                                value,
                                onChange = () => {
                                },
                                onBlur,
                                name,
                                disabled
                              }: SettingsFieldProps) {
  return (
      <Box sx={{
        width: fullWidth ? "350px" : "100%",
      }}>
        <Typography
            variant="body2"
            sx={{
              mb: "8px",
              color: "#CCC3D8"
            }}
        >
          {label}
        </Typography>

        <Box
            sx={{
              borderRadius: "12px",
              border: "1px solid",
              borderColor: "#4A4455",
              transition: "0.2s ease",
              backgroundColor: "#131315",
            }}
        >
          <InputBase
              name={name}
              placeholder={placeholder}
              value={value}
              onChange={(val) => onChange(val.target.value)}
              onBlur={onBlur}
              fullWidth
              disabled={disabled}
              sx={{
                p: "8px",
                fontSize: "1.0rem",
                color: "#E5E1E4",

                "&::placeholder": {
                  color: "#E5E1E4",
                  opacity: 1,
                },
              }}
          />
        </Box>

      </Box>)

}