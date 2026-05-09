import {
  WelcomePageContainer
} from "../../../components/WelcomePageContainer/WelcomePageContainer.tsx";
import {ForgotPasswordForm} from "../subcomponents/ForgotPasswordForm.tsx";
import {useDropPassword} from "../../../hooks/passwordHooks.ts";

export function ForgotPassword() {
  const {mutate: sendReset} = useDropPassword();

  return (
      <WelcomePageContainer>
        <ForgotPasswordForm onSubmit={val => sendReset(val)}/>
      </WelcomePageContainer>
  )
}