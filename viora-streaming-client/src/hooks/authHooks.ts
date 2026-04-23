import {useMutation} from "@tanstack/react-query";
import {useDispatch} from "react-redux";
import type {AppDispatch} from "../store/store.ts";
import {
  type LoginPayload,
  loginRequest,
  type RegisterPayload,
  registerRequest
} from "../api/authApi.ts";
import {setCredentials} from "../store/auth.ts";
import {useNavigate} from "react-router-dom";
import {openModal} from "../store/modals.ts";
import {ModalTypes} from "../types/modalTypes.ts";
import {AUTH_CONSTANTS} from "../constants/authConstants.ts";
import {nanoid} from "@reduxjs/toolkit";
import {API_PAGE} from "../constants/routingConstants.ts";


export function useLogin() {
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();

  return useMutation({
    mutationFn: (payload: LoginPayload) => loginRequest(payload),

    onSuccess(data) {
      dispatch(setCredentials({token: data.token}));
      navigate(API_PAGE.Home);
    }
  });
}

export function useRegister() {
  const dispatch = useDispatch<AppDispatch>();

  return useMutation({
    mutationFn: (payload: RegisterPayload) => registerRequest(payload),

    onSuccess() {
      dispatch(openModal({
        data: {
          description: AUTH_CONSTANTS.MAIL_SENT_MESSAGE
        },
        type: ModalTypes.confirmEmailModal,
        id: nanoid()
      }));
    },
  });
}