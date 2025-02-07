import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { Container, TextField, Button, Typography } from "@mui/material";

const LoginPage = () => {
  const [credentials, setCredentials] = useState({ username: "", password: "" });
  const [error, setError] = useState("");
  const navigate = useNavigate();
  const BACKEND_URL = process.env.REACT_APP_BACKEND_URL;
  const handleLogin = async () => {
    try {
      const response = await axios.post(`${BACKEND_URL}/admin/login`, credentials);
      // localStorage.setItem("auth", response.data.token);
      sessionStorage.setItem("token", response.data.token);
      console.log("Token",response.data.token);
      navigate("/home");
    } catch {
      setError("Invalid username or password");
    }
  };

  return (
    <Container maxWidth="sm" sx={{ paddingTop: 8 }}>
      <Typography variant="h3">Welcome to the User Management Service..</Typography>
      <Typography sx={{paddingTop:3}} variant="h5">Login</Typography>
      {error && <Typography color="error">{error}</Typography>}
      <TextField label="Username" fullWidth margin="normal" onChange={(e) => setCredentials({ ...credentials, username: e.target.value })} />
      <TextField label="Password" type="password" fullWidth margin="normal" onChange={(e) => setCredentials({ ...credentials, password: e.target.value })} />
      <Button variant="contained" onClick={handleLogin}>Login</Button>
    </Container>
  );
};

export default LoginPage;
