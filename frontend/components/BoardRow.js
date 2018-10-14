import React from 'react';
import symbol from '../constants/Symbol';

function Square(props) {
    let styleClassName = "no-symbol", disabled = false;
    if (props.value == symbol.X){
        styleClassName = "symbolX";
        disabled = true;
    }
    if (props.value == symbol.O) {
        styleClassName = "symbolO";
        disabled = true;
    }
    return (
        <input type="button" class={styleClassName} disabled={props.disabled || disabled} onClick={props.onClick}/>
    );
}

function BoardRow(props){
    let cells = [];
    for (let i = props.startIndex; i < props.endIndex; i++) {
        let disabled = props.disabled == null ? false: props.disabled;
        cells.push(<Square value={props.row[i]} index={i} disabled={disabled} onClick={() => props.onClick(i)}/>);
    }
    return (
        <div>
            { cells }
        </div>
    );
}

export default BoardRow;