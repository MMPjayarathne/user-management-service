import { useState, useEffect } from "react";
import { useDispatch } from "react-redux";
import { fetchUsers } from "../redux/userSlice";
import UserTable from "../components/UserTable";
import UserForm from "../components/UserForm";
import { Button, Box, Typography } from "@mui/material";
import { margin, padding } from "@mui/system";

const HomePage = () => {
  const dispatch = useDispatch();
  const [editingUser, setEditingUser] = useState(null);
  const [showForm, setShowForm] = useState(false);

  useEffect(() => {
    dispatch(fetchUsers());
  }, [dispatch]);

  const handleCreateUserClick = () => {
    setShowForm(true);
    setEditingUser(null);
  };

  const handleCancelClick = () => {
    setShowForm(false);
    setEditingUser(null);
  };

  return (
    <div  style={{marginLeft: 100 , marginRight: 100}}>
      <Typography sx={{marginTop: 8}} variant="h4">Manage Users..</Typography>
      <UserTable setEditingUser={setEditingUser} setShowForm={setShowForm} />
      <Box sx={{paddingTop: 3 ,mb: 2 }}>
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
      {showForm && (
        <UserForm editingUser={editingUser} setEditingUser={setEditingUser} />
      )}
      
    </div>
  );
};

export default HomePage;
