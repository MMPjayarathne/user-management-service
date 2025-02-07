import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";

const getAuthHeader = () => {
  const token = sessionStorage.getItem("token");
  return { headers: { Authorization: `Bearer ${token}` } };
};

const BACKEND_URL = process.env.REACT_APP_BACKEND_URL;

export const fetchUsers = createAsyncThunk(
  "users/fetchUsers",
  async () => {
    const response = await axios.get(`${BACKEND_URL}/users`, getAuthHeader());
    return response.data;
  }
);

export const addUser = createAsyncThunk(
  "users/addUser",
  async (user) => {
    const response = await axios.post(`${BACKEND_URL}/users`, user, getAuthHeader());
    return response.data;
  }
);

export const updateUser = createAsyncThunk(
  "users/updateUser",
  async ({ id, user }) => {
    const response = await axios.put(`${BACKEND_URL}/users/${id}`, user, getAuthHeader());
    return response.data;
  }
);

export const deleteUser = createAsyncThunk(
  "users/deleteUser",
  async (id) => {
    await axios.delete(`${BACKEND_URL}/users/${id}`, getAuthHeader());
    return id;
  }
);

// Initial state of the users slice
const initialState = {
  list: [],
  status: "idle",
  error: null
};

// Create slice with reducers and extraReducers for async actions
const userSlice = createSlice({
  name: "users",
  initialState,
  extraReducers: (builder) => {
    builder
      .addCase(fetchUsers.pending, (state) => {
        state.status = "loading";
      })
      .addCase(fetchUsers.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.list = action.payload;
        state.error = null;
      })
      .addCase(fetchUsers.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
      .addCase(addUser.fulfilled, (state, action) => {
        state.list.push(action.payload);
      })
      .addCase(updateUser.fulfilled, (state, action) => {
        const index = state.list.findIndex((user) => user.id === action.payload.id);
        if (index !== -1) {
          state.list[index] = action.payload;
        }
      })
      .addCase(deleteUser.fulfilled, (state, action) => {
        state.list = state.list.filter((user) => user.id !== action.payload);
      });
  },
});

export default userSlice.reducer;
