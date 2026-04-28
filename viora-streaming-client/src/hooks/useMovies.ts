import {useState, useEffect, useRef, useCallback} from "react";
import {fetchMoviesPage, type MovieSummary} from "../api/contentApi.ts";

interface UseInfiniteMoviesResult {
  movies: MovieSummary[];
  isLoading: boolean;
  isError: boolean;
  hasMore: boolean;
  loaderRef: React.RefObject<HTMLDivElement | null>;
}

export function useInfiniteMovies(): UseInfiniteMoviesResult {
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
      const data = await fetchMoviesPage(pageIndex, 10);
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