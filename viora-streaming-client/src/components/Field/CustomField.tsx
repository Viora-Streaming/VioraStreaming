import {Box, InputBase, Typography} from "@mui/material";
import {forwardRef} from "react";
import * as React from "react";

type CustomFieldProps = {
  label: string;
  placeholder?: string;
  error?: {
    message?: string;
  };
  value?: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
  onBlur?: (e: React.FocusEvent<HTMLInputElement>) => void;
  name?: string;
  fullWidth?: boolean;
  type?: 'text' | 'password' | 'email';
};

export const CustomField = forwardRef<HTMLInputElement, CustomFieldProps>(
    ({label, placeholder, error, value, onChange, onBlur, name, fullWidth, type = 'text'}, ref) => {
      return (
          <Box sx={{
            width: fullWidth ? "400px" : "100%",
          }}>
            <Typography
                variant="body2"
                color={"text.primary"}
                sx={{
                  mb: "8px",
                }}
            >
              {label}
            </Typography>

            <Box
                sx={{
                  borderRadius: "12px",
                  border: error && "2px solid",
                  borderColor: error && "error.main",
                  transition: "0.2s ease",
                  backgroundColor: "secondary.main",

                  "&:focus-within": {
                    border: error && "2px solid",
                    borderColor: "background.primary",
                  }
                }}
            >
              <InputBase
                  ref={ref}
                  type={type}
                  name={name}
                  placeholder={placeholder || (type === 'password' ? '••••••••' : '')}
                  value={value}
                  onChange={onChange}
                  onBlur={onBlur}
                  fullWidth
                  sx={{
                    p: "8px",
                    fontSize: "1.0rem",
                    color: error ? "error.main" : "text.primary",

                    "&::placeholder": {
                      color: "text.secondary",
                      opacity: 1,
                    },
                  }}
              />
            </Box>

          </Box>)
    }
)
