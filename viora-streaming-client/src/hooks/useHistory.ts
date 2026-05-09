import { useQuery } from "@tanstack/react-query";
import { getHistoryByMovie, getUserHistories } from "../api/historyApi.ts";
import type { History } from "../types/historyTypes.ts";

export const historyKeys = {
  all: ["history"] as const,
  byMovie: (movieId: number) => ["history", "movie", movieId] as const,
};

export const useHistory = () => {
  const { data: histories = [], isLoading } = useQuery({
    queryKey: historyKeys.all,
    queryFn: getUserHistories,
  });

  return { histories: histories as History[], isLoading };
};

export const useHistoryByMovie = (movieId: number) => {
  const { data: history = null, isLoading } = useQuery({
    queryKey: historyKeys.byMovie(movieId),
    queryFn: () => getHistoryByMovie(movieId),
  });

  return { history: history as History | null, isLoading };
};