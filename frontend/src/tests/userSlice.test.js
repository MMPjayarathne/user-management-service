import { configureStore } from "@reduxjs/toolkit";
import axios from "axios";
import userReducer, { addUser, updateUser } from "../redux/userSlice";

// mock axios
jest.mock("axios");

describe("userSlice async thunks", () => {
  let store;

  beforeEach(() => {
    store = configureStore({
      reducer: {
        users: userReducer,
      },
    });

    // Mock sessionStorage
    global.sessionStorage = {
      getItem: jest.fn(() => "fake-token"),
      setItem: jest.fn(),
      removeItem: jest.fn(),
    };
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  it("should handle addUser fulfilled", async () => {
    const mockUser = { id: 1, name: "John Doe" };
    axios.post.mockResolvedValue({ data: mockUser });

    await store.dispatch(addUser(mockUser));

    const state = store.getState().users;

    expect(state.list).toContainEqual(mockUser);
  });

  it("should handle updateUser fulfilled", async () => {
    const initialUser = { id: 1, firstName: "John", lastName: "Doe" };
    const updatedUser = { id: 1, firstName: "Jane", lastName: "Doe" };

    store = configureStore({
      reducer: {
        users: userReducer,
      },
      preloadedState: {
        users: {
          list: [initialUser],
          status: "idle",
          error: null,
        },
      },
    });

    axios.put.mockResolvedValue({ data: updatedUser });

    await store.dispatch(updateUser({ id: 1, user: updatedUser }));

    // Get the updated state
    const state = store.getState().users;

    // Check if the updatedUser was added to the list and initialUser was removed
    expect(state.list).toContainEqual(updatedUser);
    expect(state.list).not.toContainEqual(initialUser);
  });
});