import React from 'react';
import css from './HeroSection.module.scss';

function HeroSection() {
  return (
    <div className={css.heroContainer}>
      <div className={css.heroText}>
        <h1>
          Become a part of the community.
          <span> Rate</span> a product on your own!
        </h1>
      </div>
    </div>
  );
}

export default HeroSection;
