import {useNavigate} from "react-router-dom";
import {
  WelcomePageContainer
} from "../../../components/WelcomePageContainer/WelcomePageContainer.tsx";
import {DropPasswordForm} from "../subcomponents/DropPasswordForm.tsx";
import {useResetPassword} from "../../../hooks/passwordHooks.ts";

export function DropPasswordPage() {
  const navigate = useNavigate();
  const {mutate: resetPassword, isPending} = useResetPassword();

  const onSubmit = (password: string) => {
    resetPassword(password);
  }

  return (
      <WelcomePageContainer>
        <DropPasswordForm
            onSubmit={data => onSubmit(data.password)}
            isLoading={isPending}
            onSignIn={() => navigate('/auth')}
        />
      </WelcomePageContainer>
  )
}