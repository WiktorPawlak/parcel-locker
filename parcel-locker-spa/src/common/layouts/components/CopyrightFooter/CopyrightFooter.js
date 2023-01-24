import React from 'react';
import css from './CopyrightFooter.module.scss';

function CopyrightFooter() {
  return (
    <div className={css.copyrightFooterContainer}>
      © 2022 OpinionCollector. All rights reserved.
    </div>
  );
}

export default CopyrightFooter;
