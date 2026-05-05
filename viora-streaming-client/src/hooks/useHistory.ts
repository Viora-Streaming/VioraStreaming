import {useCallback, useLayoutEffect, useState} from "react";
import {getHistoryByMovie, getUserHistories} from "../api/historyApi.ts";
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

export const useHistoryByMovie = (movieId: number) => {

  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [history, setHistory] = useState<History>(null);


  const fetchHistories = useCallback(async () => {
    const fetchedHistory = await getHistoryByMovie(movieId);
    setHistory(fetchedHistory);
    setIsLoading(false);
  }, []);

  useLayoutEffect(() => {
    fetchHistories();
  }, []);

  return {history, isLoading}
}