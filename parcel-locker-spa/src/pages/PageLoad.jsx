import { CircularProgress } from '@mui/material';
import { Box } from '@mui/system';

export function PageLoad() {
  return (
    <Box
      sx={{
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        textAlign: 'center',
        minHeight: '100vh'
      }}
    >
      <CircularProgress color="secondary" />
    </Box>
  );
}
