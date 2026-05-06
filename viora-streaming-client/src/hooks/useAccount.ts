import type {Account, UpdateAccountRequest} from "../types/accountTypes.ts";
import {useCallback, useEffect, useState} from "react";
import {getAccount, updateAccount} from "../api/accountApi.ts";

type UseAccountOptions = {
  account: Account;
  isLoading: boolean;
  updateAccount: (request: UpdateAccountRequest) => void
}

export const useAccount = (): UseAccountOptions => {

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

  useEffect(() => {
    fetchAccount();
  }, [])


  return {account, updateAccount: updateAcc, isLoading}
}