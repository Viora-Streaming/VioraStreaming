import {createSlice, createAsyncThunk} from "@reduxjs/toolkit";
import {
  fetchMoviesPage,
  fetchPopularMovies,
} from "../api/contentApi.ts";
import type {MovieFilter, MovieSummary} from "../types/movieTypes.ts";

interface MoviesState {
  movies: MovieSummary[];
  trendingMovies: MovieSummary[];
  page: number;
  hasMore: boolean;
  isLoading: boolean;
  isError: boolean;
}

const initialState: MoviesState = {
  movies: [],
  page: 0,
  hasMore: true,
  isLoading: false,
  isError: false,
  trendingMovies: [],
};

export const fetchNextMoviesPage = createAsyncThunk<
    {
      content: MovieSummary[];
      last: boolean
    }, MovieFilter>("movies/fetchNextPage", async (filter: MovieFilter, {rejectWithValue}) => {
  try {
    const data = await fetchMoviesPage(filter);
    return {content: data.content, last: data.last};
  } catch {
    return rejectWithValue("Failed to fetch movies");
  }
});

export const fetchNextPopularMoviesPage = createAsyncThunk<
    {
      content: MovieSummary[];
      last: boolean
    }>("movies/fetchNextPage", async (pageIndex, {rejectWithValue}) => {
  try {
    const data = await fetchPopularMovies({page: pageIndex, size: 10});
    return {content: data.content, last: data.last};
  } catch {
    return rejectWithValue("Failed to fetch movies");
  }
});


const moviesSlice = createSlice({
  name: "movies",
  initialState,
  reducers: {
    resetMovies(state) {
      state.movies = [];
      state.page = 0;
      state.hasMore = true;
      state.isLoading = false;
      state.isError = false;
    },
    setNextPage(state) {
      if (state.hasMore) {
        state.page += 1;
      }
    }
  },
  extraReducers: (builder) => {
    builder
    .addCase(fetchNextMoviesPage.pending, (state: MoviesState) => {
      state.isLoading = true;
      state.isError = false;
    })
    .addCase(fetchNextMoviesPage.fulfilled, (state: MoviesState, action) => {
      state.isLoading = false;
      state.movies.push(...action.payload.content);
      state.hasMore = !action.payload.last;
    })
    .addCase(fetchNextMoviesPage.rejected, (state: MoviesState) => {
      state.isLoading = false;
      state.isError = true;
    });
  },
});

export const {resetMovies, setNextPage} = moviesSlice.actions;
export default moviesSlice.reducer;