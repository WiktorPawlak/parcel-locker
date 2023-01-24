import React from 'react';
import { Link } from 'react-router-dom';
import css from './Nav.module.scss';
import { useClient } from '../../../../hooks/useUser';
import { Button } from '@mui/material';

function Navbar() {
  const { getSelf, client, clientRole, logOut } = useClient();

  window.onload = function () {
    getSelf();
  };

  async function handleButtonLogOut() {
    logOut()
  }

  return (
    <nav className={css.navbar}>
      <Link to="/" className={css.navbarLogo}>
        <h4>ParcelLocker</h4>
      </Link>
      <ul className={css.navMenu}>
        {client !== undefined && (
          <li className={css.navItem}>
            <Link to="/deliveries" className={css.navLinks}>
              Deliveries
            </Link>
          </li>
        )}

        {client === undefined ? (
          <li className={css.navItem}>
            <a href="/log-in">
              <button className={css.navButton}>Log in</button>
            </a>
          </li>
        ) : (
          <li className={css.navItem}>
            <Link to="/clients/self">
              <button className={css.navButton}>
                {client.telNumber}
              </button>
            </Link>
            <Button
              sx={{ marginLeft: '10px' }}
              variant="contained"
              color="secondary"
              onClick={handleButtonLogOut}
            >
              Log out
            </Button>
          </li>
        )}
      </ul>
    </nav>
  );
}

export default Navbar;
