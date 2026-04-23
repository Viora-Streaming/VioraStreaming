import {FormContainer} from "./FormContainer.tsx";
import {Alert, Box, Divider, Link, Stack, Typography} from "@mui/material";
import {Controller, useForm} from "react-hook-form";
import {CustomField} from "../../../components/Field/CustomField.tsx";
import {VioraButton} from "../../../components/Button/VioraButton.tsx";
import {RulesLinearProgress} from "../../../components/LinearProgress/RulesLinearProgress.tsx";

type FormData = {
  email: string;
  password: string;
  confirmPassword: string;
};

type RegisterFormProps = {
  onSubmit: (data: FormData) => void;
  isLoading?: boolean;
  error?: string;
  onSignIn?: () => void;
};

export function RegisterForm({onSubmit, isLoading, error, onSignIn}: RegisterFormProps) {
  const {handleSubmit, control, getValues, formState: {errors}} = useForm<FormData>();

  return (
      <FormContainer>
        <Stack spacing={"32px"}>
          <Stack spacing={"5px"}>
            <Typography variant="h5" align="center" sx={{fontWeight: 700}}>
              Create your account
            </Typography>
            <Typography variant="body2" color={"text.secondary"} align="center">
              Join the premium streaming experience
            </Typography>
          </Stack>

          {
              (Object.keys(errors).length > 0 || error) && (
                  <Alert severity="error" variant="filled" sx={{
                    backgroundColor: "error.main",
                    fontWeight: "bold"
                  }}>
                    {error ? error : errors[Object.keys(errors)[0] as keyof typeof errors]?.message}
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
                          error={fieldState.error}
                      />
                  )}
              />

              <Controller
                  name="password"
                  control={control}
                  defaultValue=""
                  rules={{
                    required: "Password is required",
                    validate: (value: string) => value.length >= 8 || "Password must be at least 8 characters"
                  }}
                  render={({field, fieldState}) => (
                      <Stack spacing={"10px"} sx={{
                        alignItems: "center"
                      }}>
                        <CustomField
                            {...field}
                            fullWidth
                            type="password"
                            label="Password"
                            error={fieldState.error}
                        />
                        <RulesLinearProgress
                            rules={4}
                            fullFilled={field.value.length / 2}
                            message={fieldState.error && "Must be at least 8 characters"}/>
                      </Stack>
                  )}
              />

              <Controller
                  name="Confirm Password"
                  control={control}
                  defaultValue=""
                  rules={{
                    required: "Please confirm your password",
                    validate: (value: string) => value ===  getValues("password") || "Passwords do not match"
                  }}
                  render={({field, fieldState}) => (
                      <CustomField
                          {...field}
                          fullWidth
                          type="password"
                          label="Confirm Password"
                          error={fieldState.error}
                      />
                  )}
              />

              <VioraButton name="Create Account" loading={isLoading}/>
            </Stack>
          </form>
        </Stack>

        <Stack spacing={"32px"} sx={{mt: 2}}>
          <Divider>
            <Box sx={{
              padding: "0 16px",
              backgroundColor: "#201F21"
            }}>
              <Typography variant="body2">OR</Typography>
            </Box>
          </Divider>

          <Stack direction="row" spacing={"8px"} sx={{
            justifyContent: "center",
            alignItems: "center",
          }}>
            <Typography variant="body2" color={"textDisabled"}>
              Already have an account?
            </Typography>
            <Link component="button"
                  color="primary"
                  underline={"none"}
                  onClick={onSignIn}>
              Sign in
            </Link>
          </Stack>
        </Stack>

      </FormContainer>
  )
}
