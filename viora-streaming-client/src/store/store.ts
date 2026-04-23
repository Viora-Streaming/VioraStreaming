import {configureStore} from "@reduxjs/toolkit";
import authReducer from "./auth.ts";
import modalsReducer from "./modals.ts";

export const store = configureStore({
  reducer: {
    auth: authReducer,
    modal: modalsReducer
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;