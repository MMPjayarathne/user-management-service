import { useState, useEffect } from "react";
import { useDispatch } from "react-redux";
import { fetchUsers } from "../redux/userSlice";
import { useNavigate, useLocation } from "react-router-dom";
import UserTable from "../components/UserTable";
import UserForm from "../components/UserForm";
import { Button, Box, Typography } from "@mui/material";
import { jwtDecode } from "jwt-decode";

const HomePage = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const location = useLocation();
  
  const [editingUser, setEditingUser] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");
  const [userName, setUserName] = useState("");

  useEffect(() => {
    const token = sessionStorage.getItem("token");
    if (token) {
      try {
        const decoded = jwtDecode(token);
        setUserName(decoded.sub || "User");
      } catch (error) {
        console.error("Invalid token:", error);
      }
    }
  }, []); 

  useEffect(() => {
    dispatch(fetchUsers());
    if (location.state?.successMessage) {
      setSuccessMessage(location.state.successMessage);
      // Clear success message after a few seconds
      setTimeout(() => setSuccessMessage(""), 3000);
    }
  }, [dispatch, location]);

  const handleCreateUserClick = () => {
    setShowForm(true);
    setEditingUser(null);
  };

  const handleCancelClick = () => {
    setShowForm(false);
    setEditingUser(null);
  };

  const handleRegisterAdminClick = () => {
    navigate("/admin-register");
  };

  const handleLogout = () => {
    sessionStorage.clear();
    navigate("/"); // Redirect to login page
  };

  return (
    <div style={{ marginLeft: 100, marginRight: 100 }}>
      <Typography sx={{marginTop: 5}} variant="h3">Hello {userName}</Typography>
      <Box sx={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginTop: 8 }}>
        
        <Typography variant="h4">Manage Users</Typography>
        <Box>
          <Button variant="contained" color="secondary" onClick={handleRegisterAdminClick} sx={{ mr: 2 }}>
            Register Admin
          </Button>
          <Button variant="contained" color="error" onClick={handleLogout}>
            Logout
          </Button>
        </Box>
      </Box>

      {/* Success Message */}
      {successMessage && (
        <Typography sx={{ color: "green", marginTop: 2 }}>{successMessage}</Typography>
      )}

      <UserTable setEditingUser={setEditingUser} setShowForm={setShowForm} />

      <Box sx={{ paddingTop: 3, mb: 2 }}>
        {!showForm ? (
          <Button variant="contained" onClick={handleCreateUserClick}>
            Create User
          </Button>
        ) : (
          <Button variant="contained" color="error" onClick={handleCancelClick}>
            Cancel
          </Button>
        )}
      </Box>

      {showForm && <UserForm editingUser={editingUser} setEditingUser={setEditingUser} />}
    </div>
  );
};

export default HomePage;
