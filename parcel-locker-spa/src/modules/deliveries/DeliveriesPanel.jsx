import { Box } from '@mui/system';

import { FormControl, MenuItem, Select, TextField } from '@mui/material';
import { useState } from 'react';
import { useClient } from '../../hooks/useUser';
import { PageLoad } from '../../pages/PageLoad';
import { useCallback } from 'react';
import { useEffect } from 'react';
import { getDeliveries } from '../../api/productApi';
import { DeliveriesTable } from './DeliveriesTable';
import { get } from '../../api/api';

export function DeliveriesPanel() {
  const [fliter, setFilter] = useState('');
  const [loading, setLoading] = useState(true);
  const [deliveries, setDeliveries] = useState([]);
  const [showActive, setShowActive] = useState(true);
  const { clientRole } = useClient();

    const findDeliveries = useCallback(async () => {
    setLoading(true);
    let response;

    if (clientRole === 'ADMINISTRATOR') {
      response = await getDeliveries();
    } else {
      response = await get('/deliveries/current');
    }

    if (response[1] === 200) {
      setDeliveries(response[0].map(delivery => {return ({
        id: delivery.id,
        archived: delivery.archived,
        status: delivery.status,
        price: delivery.pack.basePrice,
        shipper: delivery.shipper.telNumber,
        receiver: delivery.receiver.telNumber,
      })}));
    }
     setLoading(false);
  }, [clientRole]);

    useEffect(() => {
    findDeliveries();
  }, [findDeliveries])

  if (loading) {
    return <PageLoad />;
  }

  return (
    <Box
      sx={{
        display: 'flex',
        alignItems: 'center',
        flexDirection: 'column',
        width: '100%'
      }}
    >
      <Box
        sx={{
          display: 'flex',
          justifyContent: 'center',
          flexDirection: 'row',
          width: '100%'
        }}
      >
        <TextField
          sx={{ width: '40%', margin: '10px' }}
          placeholder="Search"
          onChange={(e) => setFilter(e.target.value)}
        />
      </Box>

      <DeliveriesTable
        clients={deliveries.filter(
          (delivery) =>
            delivery.shipper.includes(fliter) || delivery.receiver.includes(fliter)
        )}
        showActive={showActive}
      />
    </Box>
  );
}
