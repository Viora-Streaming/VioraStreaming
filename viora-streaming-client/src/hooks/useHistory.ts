import {useCallback, useLayoutEffect, useState} from "react";
import {getUserHistories} from "../api/historyApi.ts";
import type {History} from "../types/historyTypes.ts";

export const useHistory = () => {

  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [histories, setHistories] = useState<History[]>([]);


  const fetchHistories = useCallback(async () => {
    const fetchedHistories = await getUserHistories();
    setHistories(fetchedHistories);
    setIsLoading(false);
  }, []);

  useLayoutEffect(() => {
    fetchHistories();
  }, []);

  return {histories, isLoading}
}