import type { Account, UpdateAccountRequest } from "../types/accountTypes.ts";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { deleteAccount, getAccount, updateAccount } from "../api/accountApi.ts";
import { useDispatch } from "react-redux";
import type { AppDispatch } from "../store/store.ts";
import { openModal } from "../store/modals.ts";
import { nanoid } from "@reduxjs/toolkit";
import { ModalTypes } from "../types/modalTypes.ts";
import { useNavigate } from "react-router-dom";
import { removeToken } from "../utils/apiUtils.ts";
import { API_PAGE } from "../constants/routingConstants.ts";

export const ACCOUNT_QUERY_KEY = ["account"] as const;

type UseAccountOptions = {
  account: Account | undefined;
  isLoading: boolean;
  updateAccount: (request: UpdateAccountRequest) => void;
  onDelete: () => void;
};

export const useAccount = (): UseAccountOptions => {
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const { data: account, isLoading } = useQuery({
    queryKey: ACCOUNT_QUERY_KEY,
    queryFn: getAccount,
  });

  const { mutate: updateAccountMutation } = useMutation({
    mutationFn: (request: UpdateAccountRequest) => updateAccount(request),
    onSuccess(updatedAccount) {
      // Directly write the server response into the cache — no refetch needed
      queryClient.setQueryData(ACCOUNT_QUERY_KEY, updatedAccount);
    },
  });

  const { mutate: deleteAccountMutation } = useMutation({
    mutationFn: deleteAccount,
    onSuccess() {
      queryClient.removeQueries({ queryKey: ACCOUNT_QUERY_KEY });
      removeToken();
      navigate(API_PAGE.Auth, { replace: true });
    },
  });

  const onDelete = () => {
    dispatch(
        openModal({
          data: { onDelete: deleteAccountMutation },
          type: ModalTypes.deleteAccountModal,
          id: nanoid(),
        })
    );
  };

  return { account, updateAccount: updateAccountMutation, isLoading, onDelete };
};