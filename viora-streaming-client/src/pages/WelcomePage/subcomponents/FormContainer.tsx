import {Box} from "@mui/material";

type FormContainerProps = {
  children: React.ReactNode;
}

export function FormContainer(props: FormContainerProps) {
  return (
      <Box sx={{
        p: "40px",
        borderRadius: "12px",
        bgcolor: "background.paper",
        boxShadow: "20px 20px 50px 0 rgba(130, 87, 229, 0.5)",
      }}>
        {props.children}
      </Box>
  )
}