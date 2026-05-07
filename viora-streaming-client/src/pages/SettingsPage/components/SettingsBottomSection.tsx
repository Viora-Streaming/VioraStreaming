import { Button, Container } from "@mui/material";

type SettingsBottomSectionProps = {
  onSave: () => void;
  onDiscard: () => void;
};

export function SettingsBottomSection({ onSave, onDiscard }: SettingsBottomSectionProps) {
  return (
      <Container
          sx={{
            display: "flex",
            justifyContent: "end",
            width: "100%",
            gap: "16px",
          }}
      >
        <Button
            variant="outlined"
            sx={{
              p: "12px 32px",
              textTransform: "none",
              fontWeight: "bold",
            }}
            onClick={onDiscard}
        >
          Discard Changes
        </Button>
        <Button
            variant="contained"
            sx={{
              p: "12px 32px",
              textTransform: "none",
              fontWeight: "bold",
              color: "background.default",
            }}
            onClick={onSave}
        >
          Save Changes
        </Button>
      </Container>
  );
}