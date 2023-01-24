import React, {useState} from 'react';
import css from './OpinionTile.module.scss';
import {useClient} from "../../../hooks/useUser";
import {
    Rating
} from '@mui/material';

const Opinion = ({ clientUsername, opinionContent, opinionPros,
                 opinionCons, creationDate, opinionId, starReview,
                     hidden, handleOpinionHide, handleOpinionEdit, handleOpinionDelete,
                     productId}) => {
    const { clientRole, username } = useClient();
    let stars = starsInt(starReview);
    return (
        <div className={css.opinion}>
            <h2>{clientUsername}</h2>
            <div className={css.parentDiv}>
                <div className={css.childDiv}>
                    <p>Creation Date: {new Date(creationDate).toLocaleString()}</p>
                    <Rating
                        name="simple-controlled"
                        defaultValue={stars}
                        precision={1.0}
                        readOnly
                    />
                    <p>{decodeURIComponent(opinionContent)}</p>
                    <p>Pros: {decodeURIComponent(opinionPros)}</p>
                    <p>Cons: {decodeURIComponent(opinionCons)}</p>
                    {clientRole === 'ADMIN'
                    && <p>Hidden: {hidden.toString()}</p>}
                    {clientRole === 'ADMIN'
                    && <button className={css.HideBtn} onClick={handleOpinionHide}>Hide Opinion</button>}
                    {clientRole === 'STANDARD'
                    && <button className={css.DeleteBtn} onClick={handleOpinionDelete}>Delete Opinion</button>}
                    {clientRole === 'STANDARD'
                    && <button className={css.EditBtn} onClick={handleOpinionEdit}>Edit Opinion</button>}
                    <br />
                </div>
            </div>
        </div>
    );
};

function starsInt(x){
    if(x==='ONE') return 1;
    else if(x==='TWO') return 2;
    else if(x==='THREE') return 3;
    else if(x==='FOUR') return 4;
    else if(x==='FIVE') return 5;
}

export default Opinion;
