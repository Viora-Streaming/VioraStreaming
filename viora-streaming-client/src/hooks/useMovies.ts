import {useCallback, useEffect, useRef, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import type {AppDispatch, RootState} from "../store/store.ts";
import {fetchNextMoviesPage, resetMovies, setNextPage} from "../store/content.ts";
import {DURATION_VALUE_MAP} from "../constants/filterConstants.ts";
import type {MovieSummary} from "../types/movieTypes.ts";
import * as React from "react";
import {fetchMoviesPage, fetchPopularMovies} from "../api/contentApi.ts";

interface UseInfiniteMoviesResult {
  movies: MovieSummary[];
  isLoading: boolean;
  isError: boolean;
  hasMore: boolean;
  loaderRef: React.RefObject<HTMLDivElement | null>;
}

export function useInfiniteMovies(): UseInfiniteMoviesResult {
  const dispatch = useDispatch<AppDispatch>();
  const {
    movies,
    page,
    hasMore,
    isLoading,
    isError
  } = useSelector((state: RootState) => state.movies);
  const filters = useSelector((state: RootState) => state.filters);
  const loaderRef = useRef<HTMLDivElement>(null);

  const handleFetch = () => {
    dispatch(fetchNextMoviesPage({
      page: page,
      size: 10,
      duration: DURATION_VALUE_MAP[filters.duration],
      releaseYear: {from: filters.releaseYear[0], to: filters.releaseYear[1]},
      genres: filters.genres,
      rating: filters.rating,
      search: filters.title
    }));
  };

  useEffect(() => {
    dispatch(resetMovies());
    handleFetch();
  }, [filters]);

  // IntersectionObserver watches the sentinel div
  useEffect(() => {
    const sentinel = loaderRef.current;
    if (!sentinel) return;

    const observer = new IntersectionObserver(
        (entries) => {
          if (entries[0].isIntersecting && hasMore && !isLoading) {
            dispatch(setNextPage());
            handleFetch();
          }
        },
        {threshold: 0.1}
    );

    observer.observe(sentinel);
    return () => observer.disconnect();
  }, [hasMore, isLoading, page]);

  return {movies, isLoading, isError, hasMore, loaderRef};
}

export function useTrendingMovies(): UseInfiniteMoviesResult {
  const [movies, setMovies] = useState<MovieSummary[]>([]);
  const [_, setPage] = useState(0);
  const [hasMore, setHasMore] = useState(true);
  const [isLoading, setLoading] = useState(false);
  const [isError, setError] = useState(false);

  const loaderRef = useRef<HTMLDivElement>(null);

  const fetchNext = useCallback(async (pageIndex: number) => {
    setLoading(true);
    setError(false);
    try {
      const data = await fetchPopularMovies({page: pageIndex, size: 10});
      setMovies((prev) => [...prev, ...data.content]);
      setHasMore(!data.last);
    } catch {
      setError(true);
    } finally {
      setLoading(false);
    }
  }, []);

  // Fetch first page on mount
  useEffect(() => {
    fetchNext(0);
  }, [fetchNext]);

  // IntersectionObserver watches the sentinel div
  useEffect(() => {
    const sentinel = loaderRef.current;
    if (!sentinel) return;

    const observer = new IntersectionObserver(
        (entries) => {
          if (entries[0].isIntersecting && hasMore && !isLoading) {
            setPage((prev) => {
              const next = prev + 1;
              fetchNext(next);
              return next;
            });
          }
        },
        {threshold: 0.1}
    );

    observer.observe(sentinel);
    return () => observer.disconnect();
  }, [hasMore, isLoading, fetchNext]);

  return {movies, isLoading, isError, hasMore, loaderRef};
}