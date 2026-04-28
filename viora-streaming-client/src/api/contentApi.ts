import { apiFetch } from "../utils/apiUtils.ts";
import { API_PATHS } from "../constants/apiConstants.ts";

export interface MovieSummary {
  id: number;
  title: string;
  poster: string;
  releaseDate: string; // ISO string from LocalDate
  genres: string[];
  rating: number;
}

export interface Page<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  number: number;       // current page index (0-based)
  last: boolean;
}

export async function fetchMoviesPage(
    page: number,
    size: number = 10
): Promise<Page<MovieSummary>> {
  return apiFetch(
      `${API_PATHS.movies}?page=${page}&size=${size}`
  );
}