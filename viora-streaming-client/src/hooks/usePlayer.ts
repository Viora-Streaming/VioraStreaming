import {useMovie} from "./useMovie.ts";

interface UsePlayerResult {
  imdbId: string | undefined;
  title: string | undefined;
  isLoading: boolean;
}

export const usePlayer = (id: number): UsePlayerResult => {
  const {movie, isLoading} = useMovie(id);

  return {
    imdbId: movie?.imdbId,
    title: movie?.title,
    isLoading,
  };
};