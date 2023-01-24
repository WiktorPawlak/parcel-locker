import React from 'react';
import css from './Product.module.scss';
import { Link } from 'react-router-dom';
import { useClient } from '../../../hooks/useUser';

const Product = ({
  title,
  description,
  image,
  id,
  handleProductHide,
  handleProductEdit
}) => {
  const { clientRole } = useClient();
  return (
    <div className={css.product}>
      <img src={image} alt=""></img>
      <h2>{title}</h2>
      <div className={css.parentDiv}>
        <div className={css.childDiv}>
          <p>Opis: {description}</p>
        </div>
        <div className={css.childDiv}>
          <Link to={`/products/${id}`}>
            <button className={css.btn}>Rate</button>
          </Link>
          {clientRole === 'ADMIN' && (
            <button className={css.btn} onClick={handleProductHide}>
              Hide
            </button>
          )}
          {clientRole === 'ADMIN' && (
            <button className={css.btn} onClick={handleProductEdit}>
              Edit
            </button>
          )}

          {clientRole === 'STANDARD' && (
            <Link to={`/suggestions/add/${id}`}>
              <button className={css.btn}>Suggest changes</button>
            </Link>
          )}
        </div>
      </div>
    </div>
  );
};

export default Product;
