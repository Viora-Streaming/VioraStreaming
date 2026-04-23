import {
  WelcomePageContainer
} from "../../../components/WelcomePageContainer/WelcomePageContainer.tsx";
import {RegisterForm} from "../subcomponents/RegisterForm.tsx";
import {useNavigate} from "react-router-dom";
import {useRegister} from "../../../hooks/authHooks.ts";
import type {RegisterPayload} from "../../../api/authApi.ts";

export function RegisterPage() {
  const navigate = useNavigate();
  const {mutate: register, isPending, isError, error} = useRegister();

  const onSubmit = (data: RegisterPayload) => {
    register(data);
  }

  return (
      <WelcomePageContainer>
        <RegisterForm
            onSubmit={data => onSubmit({email: data.email, password: data.password})}
            isLoading={isPending}
            error={isError ? error?.message : undefined}
            onSignIn={() => navigate('/auth')}
        />
      </WelcomePageContainer>
  )
}