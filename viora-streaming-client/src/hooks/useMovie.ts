import type {MovieDetails} from "../types/movieTypes.ts";
import {fetchMovieById} from "../api/contentApi.ts";
import {useCallback, useEffect, useState} from "react";

export function useMovie(id: number) {
  const [isLoading, setIsLoading] = useState(true);
  const [movie, setMovie] = useState<MovieDetails | null>(null);

  const fetchMovie = useCallback(async (id: number) => {
    const movie = await fetchMovieById(id);
    setIsLoading(false);
    return movie;
  }, []);

  useEffect(() => {
    fetchMovie(id).then(resp => setMovie(resp));
  }, []);


  return {movie, isLoading}
}