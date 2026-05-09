import { createSlice, type PayloadAction } from "@reduxjs/toolkit";
import {
  ANY_DURATION,
  DEFAULT_RATING,
  DEFAULT_RELEASE_YEAR,
} from "../constants/filterConstants.ts";

export interface FiltersState {
  genres: number[];
  rating: number;
  releaseYear: number[];
  duration: string;
  title: string;
}

const initialState: FiltersState = {
  genres: [],
  rating: DEFAULT_RATING,
  releaseYear: DEFAULT_RELEASE_YEAR,
  duration: ANY_DURATION,
  title: "",
};

const filtersSlice = createSlice({
  name: "filters",
  initialState,
  reducers: {
    setGenres(state, action: PayloadAction<number[]>) {
      state.genres = action.payload;
    },
    setRating(state, action: PayloadAction<number>) {
      state.rating = action.payload;
    },
    setReleaseYear(state, action: PayloadAction<number[]>) {
      state.releaseYear = action.payload;
    },
    setDuration(state, action: PayloadAction<string>) {
      state.duration = action.payload;
    },
    setTitle(state, action: PayloadAction<string>) {
      state.title = action.payload;
    },
    resetFilters() {
      return initialState;
    },
  },
});

export const { setGenres, setRating, setReleaseYear, setDuration, resetFilters, setTitle } =
    filtersSlice.actions;
export default filtersSlice.reducer;