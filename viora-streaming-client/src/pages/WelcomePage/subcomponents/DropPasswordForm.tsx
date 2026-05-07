import {Controller, useForm} from "react-hook-form";
import {FormContainer} from "./FormContainer.tsx";
import {Alert, Link, Stack, Typography} from "@mui/material";
import {CustomField} from "../../../components/Field/CustomField.tsx";
import {RulesLinearProgress} from "../../../components/LinearProgress/RulesLinearProgress.tsx";
import {VioraButton} from "../../../components/Button/VioraButton.tsx";
import KeyboardBackspaceIcon from '@mui/icons-material/KeyboardBackspace';

type FormData = {
  password: string;
  confirmPassword: string;
};

type DropPasswordFormProps = {
  onSubmit: (data: FormData) => void;
  isLoading?: boolean;
  error?: string;
  onSignIn?: () => void;
};

export function DropPasswordForm({onSubmit, isLoading, error, onSignIn}: DropPasswordFormProps) {
  const {handleSubmit, control, getValues, formState: {errors}} = useForm<FormData>();

  return (
      <FormContainer>
        <Stack spacing={"32px"}>
          <Stack spacing={"5px"}>
            <Typography variant="h5" align="center" sx={{fontWeight: 700}}>
              Set new password
            </Typography>
            <Typography variant="body2" align="center" sx={{
              color: "#CCC3D8",
              maxWidth: "90%",
            }}>
              Your new password must be different from previous passwords
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
                            label="New password"
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
                  name="confirmPassword"
                  control={control}
                  defaultValue=""
                  rules={{
                    required: "Please confirm your password",
                    validate: (value: string) => value === getValues("password") || "Passwords do not match"
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

              <VioraButton name="Reset password" loading={isLoading}/>
            </Stack>
          </form>
        </Stack>

        <Stack spacing={"32px"} sx={{mt: 3}}>

          <Stack direction="row" spacing={"8px"} sx={{
            justifyContent: "center",
            alignItems: "center",
          }}>
            <KeyboardBackspaceIcon color="primary"/>
            <Link component="button"
                  color="primary"
                  underline={"none"}
                  onClick={onSignIn}>
              Back to login
            </Link>
          </Stack>
        </Stack>

      </FormContainer>
  )
}
