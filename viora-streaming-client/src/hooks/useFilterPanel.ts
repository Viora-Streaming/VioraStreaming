import * as React from "react";
import {
  resetFilters,
  setDuration,
  setGenres,
  setRating,
  setReleaseYear,
} from "../store/filterSlice.ts";
import type {AppDispatch, RootState} from "../store/store.ts";
import {useDispatch, useSelector} from "react-redux";

export function useFilterPanel() {
  const dispatch = useDispatch<AppDispatch>();
  const {genres, rating, releaseYear, duration} = useSelector(
      (state: RootState) => state.filters
  );

  // Local states for smooth slider dragging
  const [localRating, setLocalRating] = React.useState<number>(rating);
  const [localReleaseYear, setLocalReleaseYear] = React.useState<number | number[]>(releaseYear);

  // Sync local state if Redux is reset externally (e.g. resetFilters)
  React.useEffect(() => {
    setLocalRating(rating);
  }, [rating]);
  React.useEffect(() => {
    setLocalReleaseYear(releaseYear);
  }, [releaseYear]);

  const handleGenreChange = (genre: number) => {
    const updated = genres.includes(genre)
        ? genres.filter((g) => g !== genre)
        : [...genres, genre];
    dispatch(setGenres(updated));
  };

  const handleRatingChange = (_: Event, value: number | number[]) => {
    setLocalRating(value as number);
  };

  const handleRatingCommit = (_: React.SyntheticEvent | Event, value: number | number[]) => {
    dispatch(setRating(value as number));
  };

  const handleReleaseYearChange = (_: Event, value: number | number[]) => {
    setLocalReleaseYear(value as number[]);
  };

  const handleReleaseYearCommit = (_: React.SyntheticEvent | Event, value: number | number[]) => {
    dispatch(setReleaseYear(value as number[]));
  };

  const handleDurationChange = (option: string) => {
    dispatch(setDuration(option));
  };

  const handleReset = () => {
    dispatch(resetFilters());
  };

  return {
    // values
    genres,
    rating: localRating,
    releaseYear: localReleaseYear,
    committedRating: rating,
    committedReleaseYear: releaseYear,
    duration,
    // handlers
    handleGenreChange,
    handleRatingChange,
    handleRatingCommit,
    handleReleaseYearChange,
    handleReleaseYearCommit,
    handleDurationChange,
    handleReset,
  };
}