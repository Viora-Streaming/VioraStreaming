import { useMutation } from "@tanstack/react-query";
import { sendResetPasswordLink, updatePassword } from "../api/accountApi.ts";
import { openModal } from "../store/modals.ts";
import { AUTH_CONSTANTS } from "../constants/authConstants.ts";
import { ModalTypes } from "../types/modalTypes.ts";
import { nanoid } from "@reduxjs/toolkit";
import { useDispatch } from "react-redux";
import type { AppDispatch } from "../store/store.ts";
import { useNavigate } from "react-router-dom";

export const useDropPassword = () => {
  const dispatch = useDispatch<AppDispatch>();

  return useMutation({
    mutationFn: (email: string) => sendResetPasswordLink(email),
    onSuccess(_, email) {
      dispatch(
          openModal({
            data: { description: `${AUTH_CONSTANTS.DROP_PASSWORD_MESSAGE}${email}` },
            type: ModalTypes.confirmEmailModal,
            id: nanoid(),
          })
      );
    },
  });
};

export const useResetPassword = () => {
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();

  return useMutation({
    mutationFn: (password: string) => updatePassword(password),
    onSuccess() {
      dispatch(
          openModal({
            data: { description: AUTH_CONSTANTS.PASSWORD_RESET_SUCCESS_MESSAGE },
            type: ModalTypes.confirmEmailModal,
            id: nanoid(),
          })
      );
      navigate("/auth");
    },
  });
};