import {
  Box,
  Typography,
  Link, Stack, Alert,
} from "@mui/material";
import {useForm, Controller} from "react-hook-form";
import {FormContainer} from "./FormContainer.tsx";
import {CustomField} from "../../../components/Field/CustomField.tsx";
import {VioraButton} from "../../../components/Button/VioraButton.tsx";

type FormData = {
  email: string;
  password: string;
  remember: boolean;
};

type LoginFormProps = {
  onSubmit: (data: FormData) => void;
  isLoading?: boolean;
  error?: string;
  onCreateAccount?: () => void;
};

export default function LoginForm({onSubmit, isLoading, error, onCreateAccount}: LoginFormProps) {
  const {handleSubmit, control, formState: {errors}} = useForm<FormData>();

  return (
      <FormContainer>
        <Stack spacing={"32px"}>
          <Typography variant="h5" align="center" sx={{fontWeight: 700}}>
            Welcome back
          </Typography>

          {
              (Object.keys(errors).length > 0 || error) && (
                  <Alert severity="error" variant="filled" sx={{
                    backgroundColor: "error.main",
                    fontWeight: "bold"
                  }}>
                    Invalid email or password. Please try again later
                  </Alert>
              )
          }

          <form onSubmit={handleSubmit(onSubmit)}>
            <Stack spacing={"24px"}>
              <Controller
                  name="email"
                  control={control}
                  defaultValue=""
                  rules={{
                    required: "Email is required"
                  }}
                  render={({field, fieldState}) => (
                      <CustomField
                          {...field}
                          label="Email"
                          placeholder="name@company.com"
                          type='email'
                          error={{message: fieldState?.error?.message}}
                      />
                  )}
              />

              <Controller
                  name="password"
                  control={control}
                  defaultValue=""
                  rules={{
                    required: "Password is required"
                  }}
                  render={({field, fieldState}) => (
                      <CustomField
                          {...field}
                          fullWidth
                          type="password"
                          label="Password"
                          error={{message: fieldState?.error?.message}}
                      />
                  )}
              />

              <Box
                  sx={{
                    display: "flex",
                    justifyContent: "end",
                  }}
              >
                <Link href="#" color="primary" underline="none" sx={{
                  fontWeight: "bold"
                }}>
                  Forgot password?
                </Link>
              </Box>

              <VioraButton name="Sign In" loading={isLoading}/>
            </Stack>
          </form>

          <Stack sx={{
            justifyContent: "center",
            alignItems: "center",
          }}>
            <Typography variant="body2" color={"text.secondary"}>
              Don’t have an account?
            </Typography>
            <Link component="button"
                  color="primary"
                  underline={"none"}
                  onClick={onCreateAccount}>
              Create account
            </Link>
          </Stack>
        </Stack>
      </FormContainer>
  );
}