import type {MovieSummary} from "./movieTypes.ts";

export interface History {
  movie: MovieSummary,
  lastWatchedAt: number
}