import React, { useState } from 'react';
import './LogIn.css';
import { useClient } from '../hooks/useUser';
import { Button, TextField } from '@mui/material';
import { Box } from '@mui/system';

function LogIn() {
  const [telNumber, setTelNumber] = useState('');
  const [password, setPassword] = useState('');
  const [isError, setIsError] = useState(false);
  const { logInClient } = useClient();

  async function signInButtonHandle() {
    if (!await logInClient({ telNumber, password })) {
      setIsError(true);
    }
  }

  return (
      <div className="sign-up">
        <div className="form-container">
          <Box sx={{ display: 'flex', flexDirection: 'column', width: '80%' }}>
            <TextField
              error={isError}
              label="Numer telefonu"
              required
              variant="outlined"
              onChange={(e) => setTelNumber(e.target.value)}
            ></TextField>
            <TextField
              sx={{ marginTop: '15px', marginBottom: '15px' }}
              type="password"
              label="Password"
              error={isError}
              required
              variant="outlined"
              onChange={(e) => setPassword(e.target.value)}
            ></TextField>
            <Button
              variant="contained"
              color="primary"
              onClick={signInButtonHandle}
            >
              Sign in
            </Button>
          </Box>
      </div>
    </div>
  );
}

export default LogIn;
