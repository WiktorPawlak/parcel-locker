import {useCallback, useEffect, useState} from 'react';
import {apiChangePassword, apiGetSelf, apiLogOut, postLogin} from '../api/authApi';
import {useNavigate} from 'react-router-dom';

export function useClient() {
  const [client, setClient] = useState();
  const [clientRole, setClientRole] = useState();
  const navigate = useNavigate();

  const logInClient = useCallback(
    async (credentials) => {
      if (credentials) {
        const response = await postLogin(credentials);
        if (response === 202) {
          navigate('/deliveries');
          window.location.reload(true);
          return true;
        } else {
          return false;
        }
      }
    },
    [navigate]
  );

  const getSelf = useCallback(async () => {
    const response = await apiGetSelf();

    if (response[1] === 200) {
      setClient(response[0]);
    } else {
      // navigate('/log-in');
      // window.location.reload(true);
    }
  }, []);

  const logOut = useCallback(async () => {
    await apiLogOut();
    navigate('/log-in');
    window.location.reload(true);
  }, [navigate]);


  const changePassword = useCallback(async (password) => {
    const response = await apiChangePassword(password);

    return response[1] === 200;
  }, []);

  useEffect(() => {
    getSelf();
  }, [getSelf]);

  useEffect(() => {
    if (client !== undefined) {
      setClientRole(client.role);
    }
  }, [client]);

  return {
    client,
    logInClient,
    getSelf,
    clientRole,
    changePassword,
    logOut
  };
}
