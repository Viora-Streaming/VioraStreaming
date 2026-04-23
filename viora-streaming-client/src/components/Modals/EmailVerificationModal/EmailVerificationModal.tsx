import type {ModalProps} from "../../../types/modalTypes.ts";
import {Box, DialogActions, DialogContent, Typography} from "@mui/material";
import {Modal} from "../Modal/Modal.tsx";
import {VioraButton} from "../../Button/VioraButton.tsx";
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import {useTheme} from "@mui/material/styles";


export function EmailVerificationModal({data, onClose}: ModalProps) {
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
            <CheckCircleIcon
                sx={{color: theme.palette.success.main, fontSize: "25px"}}
            />
          </Box>

          <Typography variant="h5" color={"text.primary"} sx={{
            fontWeight: "bold"
          }}>
            Check your inbox
          </Typography>

          <Typography variant="body1" color={"text.primary"} align="center">
            {data.description}
          </Typography>

        </DialogContent>

        <DialogActions>
          <VioraButton onClick={data.onBtnClick} name={"Send Again"} type={"button"}
                       loading={data.isLoading}/>
        </DialogActions>
      </Modal>
  )

}