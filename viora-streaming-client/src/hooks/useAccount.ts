import type {Account, UpdateAccountRequest} from "../types/accountTypes.ts";
import {useCallback, useEffect, useState} from "react";
import {deleteAccount, getAccount, updateAccount} from "../api/accountApi.ts";
import {useDispatch} from "react-redux";
import type {AppDispatch} from "../store/store.ts";
import {openModal} from "../store/modals.ts";
import {nanoid} from "@reduxjs/toolkit";
import {ModalTypes} from "../types/modalTypes.ts";
import {AUTH_CONSTANTS} from "../constants/authConstants.ts";
import {useNavigate} from "react-router-dom";
import {removeToken} from "../utils/apiUtils.ts";
import {API_PAGE} from "../constants/routingConstants.ts";

type UseAccountOptions = {
  account: Account;
  isLoading: boolean;
  updateAccount: (request: UpdateAccountRequest) => void;
  onDelete: () => void;
}

export const useAccount = (): UseAccountOptions => {

  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(true);
  const [account, setAccount] = useState<Account | null>(null);

  const fetchAccount = useCallback(async () => {
    setIsLoading(true);
    const fetchedAccount = await getAccount();
    setAccount(fetchedAccount);
    setIsLoading(false);
  }, []);

  const updateAcc = useCallback(async (request: UpdateAccountRequest) => {
    setIsLoading(true);
    const updatedAccount = await updateAccount(request);
    setAccount(updatedAccount);
    setIsLoading(false);
  }, []);

  const deleteAcc = useCallback(async () => {
    setIsLoading(true);
    await deleteAccount();
    removeToken();
    navigate(API_PAGE.Auth, {replace: true});
  }, []);

  const onDelete = useCallback(() => {
    dispatch(openModal({
      data: {
        onDelete: deleteAcc,
      },
      type: ModalTypes.deleteAccountModal,
      id: nanoid()
    }));
  }, []);

  useEffect(() => {
    fetchAccount();
  }, [])


  return {account, updateAccount: updateAcc, isLoading, onDelete}
}