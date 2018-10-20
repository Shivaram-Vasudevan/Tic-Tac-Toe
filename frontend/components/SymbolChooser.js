import React from 'react';
import symbol from '../constants/Symbol';

export default function SymbolChooser(props) {
    if (props.shouldBeDisplayed) {
        return (
            <div>
                <p class="body-text">Choose your symbol</p>
                <div class="board-area">
                    <input type="button" class="symbolX" onClick={() => props.onClick(symbol.X)}/>
                    <input type="button" class="symbolO" onClick={() => props.onClick(symbol.O)}/>
                </div>
            </div>
        );
    }
    return null;
}