import React from 'react';

export default function PlayingModeChooser(props) {
    if (props.shouldBeDisplayed) {
        return (
            <div class="intro-contrainer">
                <button class="player-selection-button body-text" onClick={() => props.onClick(true)}>Single Player</button>
                <button class="player-selection-button body-text" onClick={() => props.onClick(false)}>Double Player</button>
            </div>
        );
    }
    return null;
}