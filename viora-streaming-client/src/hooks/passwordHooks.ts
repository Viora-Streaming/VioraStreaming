import {useCallback, useState} from "react";
import {sendResetPasswordLink, updatePassword} from "../api/accountApi.ts";
import {openModal} from "../store/modals.ts";
import {AUTH_CONSTANTS} from "../constants/authConstants.ts";
import {ModalTypes} from "../types/modalTypes.ts";
import {nanoid} from "@reduxjs/toolkit";
import {useDispatch} from "react-redux";
import type {AppDispatch} from "../store/store.ts";
import {useNavigate} from "react-router-dom";

type UseDropPasswordOptions = {
  sendReset: (email: string) => void;
}

export const useDropPassword = (): UseDropPasswordOptions => {
  const dispatch = useDispatch<AppDispatch>();

  const sendReset = useCallback(async (email: string) => {
    await sendResetPasswordLink(email);

    dispatch(openModal({
      data: {
        description: `${AUTH_CONSTANTS.DROP_PASSWORD_MESSAGE}${email}`
      },
      type: ModalTypes.confirmEmailModal,
      id: nanoid()
    }));
  }, []);

  return {sendReset}
};

export const useResetPassword = () => {
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  const [isPending, setIsPending] = useState(false);

  const resetPassword = useCallback(async (password: string) => {
    setIsPending(true);
    try {
      await updatePassword(password);
      dispatch(openModal({
        data: {description: AUTH_CONSTANTS.PASSWORD_RESET_SUCCESS_MESSAGE},
        type: ModalTypes.confirmEmailModal,
        id: nanoid()
      }));
      navigate("/auth");
    } finally {
      setIsPending(false);
    }
  }, []);

  return {resetPassword, isPending};
};