import type {History} from "../types/historyTypes.ts";

const SEGMENT_DURATION_SECONDS = 10;

export function getProgressInfo(history: History): {
  progressPercent: number;
  label: string;
  finished: boolean;
} {
  const totalSeconds = history.movie.durationInMinutes * 60;
  const watchedSeconds = history.lastWatchedAt * SEGMENT_DURATION_SECONDS;
  const progressPercent = Math.min((watchedSeconds / totalSeconds) * 100, 100);
  const finished = watchedSeconds >= totalSeconds;

  if (finished) {
    return {progressPercent: 100, label: "Finished", finished: true};
  }

  const remainingSeconds = totalSeconds - watchedSeconds;
  const remainingMinutes = Math.round(remainingSeconds / 60);

  const label =
      remainingMinutes < 1
          ? "Less than a minute left"
          : remainingMinutes === 1
              ? "1 min left"
              : `${remainingMinutes} min left`;

  return {progressPercent, label, finished: false};
}