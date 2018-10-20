import React from 'react';
import BoardRow from '../components/BoardRow';
import EndCard from '../components/EndCard';
import status from '../constants/Status';

export default class Board extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isFirstPlayerPlaying: true
        };
    }

    shouldComponentUpdate(nextProps, nextState) {
        if (this.props.shouldBeDisplayed == false && nextProps.shouldBeDisplayed == false) {
            return false;
        }
        if (this.props.currentPlayerName == nextProps.currentPlayerName &&
            this.props.status == nextProps.status) {
            return false;
        }
        return true;
    }

    handleCellClick(index) {
        event.stopPropagation();
        this.setState({isFirstPlayerPlaying: !this.state.isFirstPlayerPlaying});
        this.props.onCellClick(index);
    }

    render() {
        if (this.props.shouldBeDisplayed) {
            let gameText = this.props.currentPlayerName;
            const shouldEndCardBeDisplayed = this.props.disabled && this.props.status != status.NO_RESULT;
            if (this.props.status === status.LOST) {
                gameText = gameText + " lost";
            }
            else if (this.props.status === status.DRAWN) {
                gameText = "MATCH DRAWN";
            }
            else if (this.props.status === status.API_ERROR) {
                gameText = status.API_ERROR;
            }
            else {
                gameText = gameText + "'s turn";
            }

            let rows = [];
            for(let i = 0; i < this.props.board.length; i += this.props.size) {
                rows.push(<BoardRow 
                            startIndex={i} 
                            endIndex={i+this.props.size} 
                            disabled={this.props.disabled} 
                            row={this.props.board} 
                            onClick={this.handleCellClick.bind(this)}/>);
            }

            return (
                <div id="game" class={"player" + (this.state.isFirstPlayerPlaying ? "1" : "2")}>
                    <p class="body-text">{gameText}</p>
                    <div class="cell-area">
                        {rows}
                        <EndCard
                            shouldBeDisplayed = {shouldEndCardBeDisplayed}
                            onRestartGame={this.props.onRestartGame}
                            onNewGame = {this.props.onNewGame} />
                    </div>
                </div>
            );
        }
        return null;
    }
}