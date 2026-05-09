import type { MovieDetails } from "../types/movieTypes.ts";
import { fetchMovieById } from "../api/contentApi.ts";
import { useQuery } from "@tanstack/react-query";

export const movieKeys = {
  all: ["movies"] as const,
  detail: (id: number) => ["movies", id] as const,
};

export function useMovie(id: number) {
  const { data: movie = null, isLoading } = useQuery({
    queryKey: movieKeys.detail(id),
    queryFn: () => fetchMovieById(id),
  });

  return { movie: movie as MovieDetails | null, isLoading };
}