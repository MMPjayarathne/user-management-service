import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchUsers, deleteUser } from "../redux/userSlice";
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Button, Dialog, DialogActions, DialogContent, DialogTitle } from "@mui/material";

const UserTable = ({ setEditingUser, setShowForm }) => {
  const dispatch = useDispatch();
  const { list: users, status, error } = useSelector((state) => state.users);
  const [open, setOpen] = useState(false); 
  const [userToDelete, setUserToDelete] = useState(null); 

  useEffect(() => {
    if (status === "idle") {
      dispatch(fetchUsers());
    }
  }, [status, dispatch]);

  if (status === "loading") return <p>Loading users...</p>;
  if (status === "failed") return <p>Error: {error}</p>;
  if (!users?.length) return <p>No users found.</p>;

  const handleEditClick = (user) => {
    setEditingUser(user);
    setShowForm(true); 
  };

  const handleDeleteClick = (user) => {
    setUserToDelete(user);
    setOpen(true);
  };

  const handleCloseDialog = () => {
    setOpen(false);
    setUserToDelete(null); 
  };

  const handleConfirmDelete = () => {
    if (userToDelete) {
      dispatch(deleteUser(userToDelete.id)); 
      setOpen(false); 
      setUserToDelete(null); 
    }
  };

  return (
    <div>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>First Name</TableCell>
              <TableCell>Last Name</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {users.map((user) => (
              <TableRow key={user.id}>
                <TableCell>{user.firstName}</TableCell>
                <TableCell>{user.lastName}</TableCell>
                <TableCell>
                  <Button onClick={() => handleEditClick(user)}>Edit</Button>
                  <Button onClick={() => handleDeleteClick(user)} color="error">Delete</Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      {/* Confirmation Dialog */}
      <Dialog open={open} onClose={handleCloseDialog}>
        <DialogTitle>Delete User</DialogTitle>
        <DialogContent>
          <p>Are you sure you want to delete this user?</p>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog} color="primary">Cancel</Button>
          <Button onClick={handleConfirmDelete} color="error">Delete</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export default UserTable;
