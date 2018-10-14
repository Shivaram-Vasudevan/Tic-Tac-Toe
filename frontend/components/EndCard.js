import React from 'react';

export default function endCard(props) {
    if (props.shouldBeDisplayed) {
        return (
            <div id="replay-button-div">
                <button class="interactive-button" onClick={() => props.onRestartGame()}>Replay
                    <img class="text-inline-button" src="./images/replay.jpg"/>
                </button>
                                    
                <button class="interactive-button" onClick={() => props.onNewGame()}>New Game
                    <img class="text-inline-button" src="./images/new-game.jpg"/>
                </button>
            </div>
        );
    }
    return null;
}