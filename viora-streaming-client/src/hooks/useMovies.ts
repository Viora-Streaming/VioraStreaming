import {useEffect, useRef} from "react";
import {useSelector} from "react-redux";
import type {RootState} from "../store/store.ts";
import {useInfiniteQuery} from "@tanstack/react-query";
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

const PAGE_SIZE = 10;

export function useInfiniteMovies(): UseInfiniteMoviesResult {
  const filters = useSelector((state: RootState) => state.filters);
  const loaderRef = useRef<HTMLDivElement>(null);

  const queryParams = {
    size: PAGE_SIZE,
    duration: DURATION_VALUE_MAP[filters.duration],
    releaseYear: {from: filters.releaseYear[0], to: filters.releaseYear[1]},
    genresIds: filters.genres,
    rating: filters.rating,
    search: filters.title,
  };

  const {data, isLoading, isError, hasNextPage, fetchNextPage, isFetchingNextPage} =
      useInfiniteQuery({
        // Re-fetch from page 0 whenever filters change
        queryKey: ["movies", "filtered", queryParams],
        queryFn: ({pageParam = 0}) =>
            fetchMoviesPage({page: pageParam as number, ...queryParams}),
        getNextPageParam: (lastPage, allPages) =>
            lastPage.last ? undefined : allPages.length,
        initialPageParam: 0,
      });

  const movies = data?.pages.flatMap((page) => page.content) ?? [];
  const hasMore = hasNextPage ?? false;

  // IntersectionObserver triggers next page fetch
  useEffect(() => {
    const sentinel = loaderRef.current;
    if (!sentinel) return;

    const observer = new IntersectionObserver(
        (entries) => {
          if (entries[0].isIntersecting && hasMore && !isFetchingNextPage) {
            fetchNextPage();
          }
        },
        {threshold: 0.1}
    );

    observer.observe(sentinel);
    return () => observer.disconnect();
  }, [hasMore, isFetchingNextPage, fetchNextPage]);

  return {movies, isLoading, isError, hasMore, loaderRef};
}

export function useTrendingMovies(): UseInfiniteMoviesResult {
  const loaderRef = useRef<HTMLDivElement>(null);

  const {data, isLoading, isError, hasNextPage, fetchNextPage, isFetchingNextPage} =
      useInfiniteQuery({
        queryKey: ["movies", "trending"],
        queryFn: ({pageParam = 0}) =>
            fetchPopularMovies({page: pageParam as number, size: PAGE_SIZE}),
        getNextPageParam: (lastPage, allPages) =>
            lastPage.last ? undefined : allPages.length,
        initialPageParam: 0,
      });

  const movies = data?.pages.flatMap((page) => page.content) ?? [];
  const hasMore = hasNextPage ?? false;

  useEffect(() => {
    const sentinel = loaderRef.current;
    if (!sentinel) return;

    const observer = new IntersectionObserver(
        (entries) => {
          if (entries[0].isIntersecting && hasMore && !isFetchingNextPage) {
            fetchNextPage();
          }
        },
        {threshold: 0.1}
    );

    observer.observe(sentinel);
    return () => observer.disconnect();
  }, [hasMore, isFetchingNextPage, fetchNextPage]);

  return {movies, isLoading, isError, hasMore, loaderRef};
}