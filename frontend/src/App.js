import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import HomePage from "./pages/HomePage";

const PrivateRoute = ({ children }) => {
  return localStorage.getItem("auth") ? children : <Navigate to="/" />;
};

const App = () => (
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<LoginPage />} />
      <Route path="/home" element={<PrivateRoute><HomePage /></PrivateRoute>} />
    </Routes>
  </BrowserRouter>
);

export default App;
