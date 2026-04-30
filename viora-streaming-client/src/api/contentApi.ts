import {apiFetch, makeQueryFromProps} from "../utils/apiUtils.ts";
import {API_PATHS} from "../constants/apiConstants.ts";
import type {MovieDetails, MovieFilter, MovieSummary, Pageable} from "../types/movieTypes.ts";
import type {Page} from "../types/common.ts";

export async function fetchMoviesPage(requestParams: MovieFilter): Promise<Page<MovieSummary>> {
  const api = `${API_PATHS.movies}?${makeQueryFromProps(requestParams)}`;
  return apiFetch(api);
}

export async function fetchPopularMovies(requestParams: Pageable): Promise<Page<MovieSummary>> {
  const api = `${API_PATHS.popularMovies}?${makeQueryFromProps(requestParams)}`;
  return apiFetch(api);
}

export function fetchMovieById(id: number): Promise<MovieDetails> {
  const api = `${API_PATHS.movies}/${id}`;
  return apiFetch(api);
}
