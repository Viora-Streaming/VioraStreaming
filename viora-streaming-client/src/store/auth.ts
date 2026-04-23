import {createSlice, type PayloadAction} from "@reduxjs/toolkit";

interface AuthState {
  token: string | null;
}

const initialState: AuthState = {
  token: localStorage.getItem("JWT_TOKEN"),
};

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    setCredentials(
        state,
        action: PayloadAction<{ token: string; }>
    ) {
      state.token = action.payload.token;
      localStorage.setItem("JWT_TOKEN", action.payload.token);
    }
  },
});

export const {setCredentials} = authSlice.actions;
export default authSlice.reducer;