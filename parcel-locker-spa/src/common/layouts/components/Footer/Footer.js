import React from 'react';
import { Link } from 'react-router-dom';
import css from './Footer.module.scss';
import { useClient } from '../../../../hooks/useUser';

function Footer() {
  const { getSelf, client, clientRole } = useClient();

  window.onload = function () {
    getSelf();
  };
  return (
    <div className={css.footerContainer}>
      <hr />
      <div className={css.navbar}>
        <Link to="/" className={css.navbarLogo}>
          <h4>OpinionCollector🍕</h4>
        </Link>
        <ul className={css.navMenu}>
          <li className={css.navItem}>
            <Link to="/" className={css.navLinks}>
              Home
            </Link>
          </li>
          <li className={css.navItem}>
            <Link to="/products" className={css.navLinks}>
              Products
            </Link>
          </li>
          {clientRole === 'ADMIN' && (
        <li className={css.navItem}>
            <Link to="/all_suggestions" className={css.navLinks}>
              Suggestions
            </Link>
          </li>
        )}
        {clientRole === 'STANDARD' && (
        <li className={css.navItem}>
            <Link to="/my_suggestions" className={css.navLinks}>
              Suggestions
            </Link>
          </li>
        )}
          <li className={css.navItem}>
            <Link to="/about" className={css.navLinks}>
              About
            </Link>
          </li>
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
                  {client.username.username}
                </button>
              </Link>
            </li>
          )}
        </ul>
      </div>
    </div>
  );
}

export default Footer;
