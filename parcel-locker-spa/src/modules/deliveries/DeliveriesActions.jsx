import { DeleteOutline } from '@mui/icons-material';
import { Button, Modal, TextField } from '@mui/material';
import { Box } from '@mui/system';
import { useState } from 'react';
import { deleteApi, put, putForDeliveries } from '../../api/api';
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast } from 'react-toastify';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  pt: 2,
  px: 4,
  pb: 3,
  backgroundColor: 'darkGrey'
};

export function DeliveriesActions({ username: deliveryId, status }) {
  const [isPutInModalOpen, setIsPutInModalOpen] = useState(false);
  const [isTakeOutModalOpen, setIsTaskeOutModalOpen] = useState(false);
  const [accessCode, setAccessCode] = useState('');
  const [takeOutAccessCode, setTakeOutAccessCode] = useState('');
  const [takeOutTelNumber, setTakeOutTelNumber] = useState('');

  async function handleDeleteDelivery() {
    await deleteApi(`/deliveries/${deliveryId}`);
    window.location.reload(true);
  }

  async function handlePutIn() {
    if (accessCode !== '') {
      const response = await putForDeliveries(`/deliveries/${deliveryId}/put-in`, {
        lockerId: 'LODZ_01',
        accessCode: accessCode
      });
      if (response === 200) {
        toast('Delivery placed in locker');
        setIsPutInModalOpen(false);
        window.location.reload(true);
      }
      
    }
  }

    async function handleTakeOut() {
    if (takeOutAccessCode !== '') {
      const response = await putForDeliveries(`/deliveries/${deliveryId}/take-out`, {
        lockerId: 'LODZ_01',
        accessCode: takeOutAccessCode,
        telNumber: takeOutTelNumber
      });

      if (response === 200) {
        toast('Delivery took out from locker');
        setIsPutInModalOpen(false);
        window.location.reload(true);
      }
    }
  }

  return (
    <Box sx={{ display: 'inline' }}>
      {status === 'READY_TO_SHIP' &&
      <Button
        sx={{ marginRight: '7px' }}
        onClick={s => setIsPutInModalOpen(true)}
        variant="outlined"
      >
        Put in
      </Button>
      }
      {(status === 'RECEIVED' || status === 'READY_TO_SHIP') &&
      <Button
        onClick={handleDeleteDelivery}
        variant="outlined"
        startIcon={<DeleteOutline />}
      >
        Delete
      </Button>
      }
      {status === 'READY_TO_PICKUP' && 
       <Button
        onClick={s => setIsTaskeOutModalOpen(true)}
        variant="outlined"
      >
        Take out
      </Button>
      }
      <Modal
        open={isPutInModalOpen}
        onClose={s => setIsPutInModalOpen(false)}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Box sx={{ display: 'flex', flexDirection: 'column' }}>
            <TextField
              label="Access code"
              required
              variant="outlined"
              onChange={(e) => setAccessCode(e.target.value)}
            ></TextField>
            <Button color="secondary" variant="contained" onClick={handlePutIn}>
              Put in
            </Button>
          </Box>
        </Box>
      </Modal>
      <Modal
        open={isTakeOutModalOpen}
        onClose={s => setIsTaskeOutModalOpen(false)}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Box sx={{ display: 'flex', flexDirection: 'column' }}>
            <TextField
              label="Access code"
              required
              variant="outlined"
              onChange={(e) => setTakeOutAccessCode(e.target.value)}
            ></TextField>
            <TextField
              label="Telephone number"
              required
              variant="outlined"
              onChange={(e) => setTakeOutTelNumber(e.target.value)}
            ></TextField>
            <Button color="secondary" variant="contained" onClick={handleTakeOut}>
              Take out
            </Button>
          </Box>
        </Box>
      </Modal>
    </Box>
  );
}
