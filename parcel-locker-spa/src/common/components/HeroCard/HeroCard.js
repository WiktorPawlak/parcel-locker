import React from 'react';
import css from './HeroCard.module.scss';

function HeroCard() {
  return (
    <div className={css.heroCardContainer}>
      <div className={css.heroCardBoxParent}>
        <div className={css.childDiv}>24/7 Support</div>
        <div className={css.childDiv}>1000+ of reviews</div>
        <div className={css.childDiv}>And more!</div>
      </div>
    </div>
  );
}

export default HeroCard;
