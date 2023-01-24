import React, { useState } from 'react';
import { useClient } from '../../hooks/useUser';
import 'react-toastify/dist/ReactToastify.css';
import { Box, Button, TextField, Typography } from '@mui/material';
import { validatePassword } from '../../validators/client/clientValidators';
import { PageLoad } from '../../pages/PageLoad';

export function ClientPanel() {
  const [password, setPassword] = useState('');
  const [repeatedPassword, setRepeatedPassword] = useState('');
  const [isPasswordValid, setIsPasswordValid] = useState(true);

  const { client, clientRole, changePassword } = useClient();

  async function changePasswordButtonHandle() {
    if (validatePassword(password, repeatedPassword)) {
      setIsPasswordValid(true);
      await changePassword({
        firstName: client.firstName,
        lastName: client.lastName,
        password: password
      });
    } else {
      setIsPasswordValid(false);
    }
  }

  if (!client) {
    return <PageLoad />;
  }

  return (
    <Box sx={{ margin: '50px' }}>
      <Typography variant="h6">
        <b>Telephone number:</b> {client.telNumber}
      </Typography>
      <Typography variant="h6">
        <b>Role:</b> {clientRole}
      </Typography>

      <Typography variant="h6">
        <b>FirstName:</b> {client.firstName}
      </Typography>

      <Typography variant="h6">
        <b>LastName:</b> {client.lastName}
      </Typography>

      <Typography variant="h6">
        <b>Password:</b>
      </Typography>
      <Box sx={{ display: 'flex', flexDirection: 'column', width: '20%' }}>
        <TextField
          error={!isPasswordValid}
          type="password"
          label="New password"
          required
          variant="outlined"
          onChange={(e) => setPassword(e.target.value)}
        ></TextField>
        <TextField
          sx={{ marginTop: '15px', marginBottom: '15px' }}
          type="password"
          error={!isPasswordValid}
          helperText={!isPasswordValid ? 'Password is not valid' : ' '}
          label="Repeat password"
          required
          variant="outlined"
          onChange={(e) => setRepeatedPassword(e.target.value)}
        ></TextField>
        <Button
          variant="contained"
          color="primary"
          onClick={changePasswordButtonHandle}
        >
          Change password
        </Button>
      </Box>
    </Box>
  );
}
