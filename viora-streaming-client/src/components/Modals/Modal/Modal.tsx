import {Button, Dialog, DialogActions, DialogContent, DialogTitle, Typography} from "@mui/material";
import {useTheme} from "@mui/material/styles";

type ModalProps = {
  onClose: () => void;
  children: React.ReactNode;
}

export function Modal({onClose, children}: ModalProps) {

  const theme = useTheme();

  return (
      <Dialog
          open={true}
          onClose={onClose}
          sx={{
            '& .MuiPaper-root': {
              background: theme.palette.background.paper,
              borderRadius: "12px",
              padding: "20px",
              width: "480px",
            },
          }}>
        {children}
      </Dialog>
  )

}