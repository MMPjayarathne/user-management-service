import { configureStore, createSlice } from "@reduxjs/toolkit";

// Redux: Auth Slice
// This is not using since the authentication is done by the backend. 
const authSlice = createSlice({
  name: "auth",
  initialState: { isAuthenticated: false, username: "" },
  reducers: {
    login: (state, action) => {
      if (action.payload.username === "haulmatic" && action.payload.password === "123456") {
        state.isAuthenticated = true;
        state.username = action.payload.username;
      }
    },
    logout: (state) => {
      state.isAuthenticated = false;
      state.username = "";
    },
  },
});