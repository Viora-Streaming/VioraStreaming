import {configureStore} from "@reduxjs/toolkit";
import authReducer from "./auth.ts";
import modalsReducer from "./modals.ts";
import moviesReducer from "./content.ts";
import filtersReducer from "./filterSlice.ts";

export const store = configureStore({
  reducer: {
    auth: authReducer,
    modal: modalsReducer,
    movies: moviesReducer,
    filters: filtersReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;