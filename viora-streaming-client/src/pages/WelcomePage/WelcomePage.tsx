import {WelcomePageContainer} from "../../components/WelcomePageContainer/WelcomePageContainer.tsx";
import LoginForm from "./subcomponents/LoginForm.tsx";
import {useLogin} from "../../hooks/authHooks.ts";
import type {LoginPayload} from "../../api/authApi.ts";
import {useNavigate} from 'react-router-dom'

export default function WelcomePage() {
  const navigate = useNavigate()
  const {mutate: login, isPending, isError, error} = useLogin();

  const onSubmit = (data: LoginPayload) => {
    login(data);
  }

  return (
      <WelcomePageContainer>
        <LoginForm onSubmit={data => onSubmit({email: data.email, password: data.password})}
                   isLoading={isPending} error={isError ? error?.message : undefined}
                   onCreateAccount={() => navigate('/auth/register')}
                   onForgotPassword={() => navigate('/auth/forgot-password')}
        />
      </WelcomePageContainer>
  )
}

