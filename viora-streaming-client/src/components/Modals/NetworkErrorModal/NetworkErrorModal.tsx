import type {ModalProps} from "../../../types/modalTypes.ts";
import {Box, DialogActions, DialogContent, Typography} from "@mui/material";
import {Modal} from "../Modal/Modal.tsx";
import {VioraButton} from "../../Button/VioraButton.tsx";
import ReportGmailerrorredIcon from '@mui/icons-material/ReportGmailerrorred';
import {useTheme} from "@mui/material/styles";

export function NetworkErrorModal({data, onClose}: ModalProps) {
  const theme = useTheme();

  return (
      <Modal onClose={onClose}>

        <DialogContent sx={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          flexDirection: "column",
          gap: "12px",
        }}>
          <Box sx={{
            height: "64px",
            width: "64px",
            backgroundColor: theme.palette.customColors.iconBoxBg,
            borderRadius: "12px",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}>
            <ReportGmailerrorredIcon
                fontSize="large"
                color="error"
            />
          </Box>

          <Typography variant="h5" color={"text.primary"} sx={{
            fontWeight: "bold"
          }}>
            Network Error
          </Typography>

          <Typography variant="body1" color={"text.primary"} align="center">
            {data.message}
          </Typography>

        </DialogContent>

        <DialogActions>
          <VioraButton onClick={onClose} name={"Close"} type={"button"}/>
        </DialogActions>
      </Modal>
  )

}