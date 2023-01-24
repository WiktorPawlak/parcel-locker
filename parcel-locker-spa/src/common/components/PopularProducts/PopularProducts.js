import React from 'react';
import css from './PopularProducts.module.scss';

function PopularProducts() {
  return (
    <div className={css.popularContainer}>
      <div>
        <h2>Popular products</h2>
        <p>
          Here you can check the most popular products. You can try rating them
          on your own as well!
        </p>
      </div>
      <div>
        <p>
          HERE SOME OF OUR PRODUCTS WILL BE DISPLAYEDHERE SOME OF OUR PRODUCTS
          WILL BE DISPLAYEDHERE SOME OF OUR PRODUCTS WILL BE DISPLAYEDHERE SOME
          OF OUR PRODUCTS WILL BE DISPLAYED
        </p>
      </div>
      <div>
        <h2>See all</h2>
        <p>Check out all products here!</p>
      </div>
    </div>
  );
}

export default PopularProducts;
