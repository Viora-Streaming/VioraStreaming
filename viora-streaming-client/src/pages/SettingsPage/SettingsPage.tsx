import { Container, Stack } from "@mui/material";
import { useAccount } from "../../hooks/useAccount.ts";
import { SettingHeaderSection } from "./components/SettingsHeaderSection.tsx";
import { SettingsBodySection } from "./components/SettingsBodySection.tsx";
import { SettingsBottomSection } from "./components/SettingsBottomSection.tsx";
import { useEffect, useState } from "react";
import {
  DeleteAccountModal
} from "../../components/Modals/DeleteAccountModal/DeleteAccountModal.tsx";

export function SettingsPage() {
  const { account, updateAccount, onDelete } = useAccount();

  const [formValues, setFormValues] = useState({
    fullName: "",
    bio: "",
  });

  useEffect(() => {
    if (account) {
      setFormValues({
        fullName: account.fullName,
        bio: account.bio,
      });
    }
  }, [account]);

  const handleChange = (field: keyof typeof formValues) => (value: string) => {
    setFormValues((prev) => ({ ...prev, [field]: value }));
  };

  const handleSave = () => {
    updateAccount({
      fullName: formValues.fullName,
      bio: formValues.bio,
    });
  };

  const handleDiscard = () => {
    if (account) {
      setFormValues({
        fullName: account.fullName,
        bio: account.bio,
      });
    }
  };

  return (
      <Container sx={{ p: "32px" }} maxWidth="xl">
        <Stack spacing="48px" sx={{ alignItems: "center" }}>
          <SettingHeaderSection />
          {account && (
              <SettingsBodySection
                  account={account}
                  formValues={formValues}
                  onChange={handleChange}
                  onDeleteClick={() => {onDelete()}}
              />
          )}
          <SettingsBottomSection onSave={handleSave} onDiscard={handleDiscard} />
        </Stack>
      </Container>
  );
}