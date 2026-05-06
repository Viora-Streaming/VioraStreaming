import type { Account } from "../../../types/accountTypes.ts";
import { Box, Container, Stack, Typography } from "@mui/material";
import { UPDATE_DESCRIPTION } from "../../../constants/settingsConstants.ts";
import { SettingsField } from "../../../components/Field/SettingsField.tsx";
import { DangerZoneSection } from "./DangerZoneSection.tsx";

type FormValues = {
  fullName: string;
  bio: string;
};

type SettingsBodySectionProps = {
  account: Account;
  formValues: FormValues;
  onChange: (field: keyof FormValues) => (value: string) => void;
  onDeleteClick: () => void;
};

export function SettingsBodySection({
                                      account,
                                      formValues,
                                      onChange,
                                      onDeleteClick,
                                    }: SettingsBodySectionProps) {
  return (
      <Container
          sx={{
            py: "32px",
            borderTop: "1px solid #333",
          }}
      >
        <Stack spacing="48px">
          <Stack direction="row" spacing="32px">
            <Box sx={{ flex: 1 }}>
              <Typography variant="h6" sx={{ fontWeight: "bold" }}>
                Profile Details
              </Typography>
              <Typography sx={{ color: "#CCC3D8" }}>{UPDATE_DESCRIPTION}</Typography>
            </Box>
            <Stack sx={{ flex: 2 }} spacing="16px">
              <Stack
                  direction="row"
                  sx={{ justifyContent: "space-between" }}
                  spacing="16px"
              >
                <SettingsField
                    label="Full Name"
                    value={formValues.fullName}
                    onChange={onChange("fullName")}
                    fullWidth
                />
                <SettingsField
                    label="Email"
                    value={account.email}
                    fullWidth
                    disabled
                />
              </Stack>
              <SettingsField
                  label="Bio"
                  value={formValues.bio}
                  onChange={onChange("bio")}
              />
            </Stack>
          </Stack>

          <DangerZoneSection onClick={onDeleteClick} />
        </Stack>
      </Container>
  );
}