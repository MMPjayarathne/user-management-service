import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { TextField, Button, Box, Typography, Alert } from "@mui/material";
import axios from "axios";

const AdminRegister = () => {
  const navigate = useNavigate();
  
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [error, setError] = useState("");
  const BACKEND_URL = process.env.REACT_APP_BACKEND_URL;

  const handleRegister = async () =>  {
    if (!username || !password || !confirmPassword) {
      setError("All fields are required.");
      return;
    }

    if (password !== confirmPassword) {
      setError("Passwords do not match.");
      return;
    }

    setError("");

    try {
        const response = await axios.post(`${BACKEND_URL}/admin/register`, { username: username, password: password });
        // console.log(response.data);
        navigate("/home", { state: { successMessage: "Admin registered successfully!" } });
      } catch (error) {
        if (error.response && error.response.status === 409) {
            setError("Admin already exists");
          } else {
            setError("Invalid username or password");
          }
      }

    
    
  };

  return (
    <Box sx={{ width: 400, margin: "auto", mt: 8, padding: 4, boxShadow: 3, borderRadius: 2 }}>
      <Typography variant="h5" sx={{ mb: 3, textAlign: "center" }}>
        Register Admin
      </Typography>

      {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

      <TextField
        fullWidth
        label="Username"
        variant="outlined"
        sx={{ mb: 2 }}
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />

      <TextField
        fullWidth
        type="password"
        label="Password"
        variant="outlined"
        sx={{ mb: 2 }}
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />

      <TextField
        fullWidth
        type="password"
        label="Re-enter Password"
        variant="outlined"
        sx={{ mb: 2 }}
        value={confirmPassword}
        onChange={(e) => setConfirmPassword(e.target.value)}
      />

      <Button fullWidth variant="contained" color="primary" onClick={handleRegister}>
        Register Admin
      </Button>

      <Button
        fullWidth
        variant="outlined"
        color="secondary"
        sx={{ mt: 2 }}
        onClick={() => navigate("/")}
      >
        Cancel
      </Button>
    </Box>
  );
};

export default AdminRegister;
