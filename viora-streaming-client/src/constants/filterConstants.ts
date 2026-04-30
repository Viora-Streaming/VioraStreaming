import type {Duration} from "../types/movieTypes.ts";

export const DEFAULT_RATING = 1.0;
export const DEFAULT_RELEASE_YEAR = [1990, 2024];

export const ANY_DURATION = "Any";
export const DEFAULT_DURATION = "90-120";
export const MAX_DURATION = "120+";

export const LIST_OF_DURATION = [
  ANY_DURATION,
  DEFAULT_DURATION,
  MAX_DURATION,
] as const;

export const DURATION_VALUE_MAP: Record<string, Duration> = {
  [ANY_DURATION]: {},
  [DEFAULT_DURATION]: {
    from: 90,
    to: 120,
  },
  [MAX_DURATION]: {
    from: 120
  },
}
