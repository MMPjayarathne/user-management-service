import { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { addUser, updateUser } from "../redux/userSlice";
import { TextField, Button, Box, Typography } from "@mui/material";

const UserForm = ({ editingUser, setEditingUser }) => {
  const dispatch = useDispatch();
  const [user, setUser] = useState({ firstName: "", lastName: "" });

  useEffect(() => {
    if (editingUser) setUser(editingUser);
  }, [editingUser]);

  const handleSubmit = () => {
    if (editingUser) {
      dispatch(updateUser({ id: editingUser.id, user }));
      setEditingUser(null);
    } else {
      dispatch(addUser(user));
    }
    setUser({ firstName: "", lastName: "" });
  };

  return (
    <Box sx={{ mb: 2 }}>
      <Typography variant="h6">{editingUser ? "Edit User" : "Add User"}</Typography>
      <TextField label="First Name" value={user.firstName} onChange={(e) => setUser({ ...user, firstName: e.target.value })} fullWidth margin="normal" />
      <TextField label="Last Name" value={user.lastName} onChange={(e) => setUser({ ...user, lastName: e.target.value })} fullWidth margin="normal" />
      <Button variant="contained" onClick={handleSubmit}>{editingUser ? "Update" : "Add"}</Button>
    </Box>
  );
};

export default UserForm;
