import React from 'react';
import PlayingModeChooser from '../components/PlayingModeChooser';
import SymbolChooser from '../components/SymbolChooser';
import Board from '../components/Board';
import status from '../constants/Status';
import playerName from '../constants/Player_Name';
import getNextBoard from '../apis/Board';
import { toggleSymbol } from '../constants/Symbol';

function getNextPlayer(currentPlayer, isSinglePlayer) {
    let nextPlayer = currentPlayer == playerName.PLAYER_1 ? playerName.PLAYER_2 : playerName.PLAYER_1;
    if (isSinglePlayer) {
        nextPlayer = currentPlayer == playerName.PLAYER ? playerName.COMPUTER : playerName.PLAYER;
    }
    return nextPlayer;
}

function getBoardStatus(squares) {
    const lines = [
        [0, 1, 2],
        [3, 4, 5],
        [6, 7, 8],
        [0, 3, 6],
        [1, 4, 7],
        [2, 5, 8],
        [0, 4, 8],
        [2, 4, 6],
      ];
      for (let i = 0; i < lines.length; i++) {
        const [a, b, c] = lines[i];
        if (squares[a] && squares[a] === squares[b] && squares[a] === squares[c]) {
          return status.LOST;
        }
      }
      for (let i = 0; i < squares.length; i++) {
          if (!squares[i]) {
              return status.NO_RESULT;
          }
      }
      return status.DRAWN;
}

export default class Game extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
                        isSinglePlayer: null,
                        currentPlayer: null,
                        playerToSymbolMap: {},
                        status: status.NO_RESULT,
                        columns: 3, 
                        board: Array(9).fill(null)};
    }

    handlePlayerClick(isSinglePlayer) {
        this.setState({isSinglePlayer: isSinglePlayer});
    }

    handleSymbolClick(playerSymbol) {
        let newState = Object.assign({}, this.state);
        // TODO: Current player to be assigned randomly
        let currentPlayer = playerName.PLAYER_1;
        let player1 = playerName.PLAYER_1;
        let player2 = playerName.PLAYER_2;
        if (this.state.isSinglePlayer) {
            currentPlayer = playerName.PLAYER;
            player1 = playerName.PLAYER;
            player2 = playerName.COMPUTER;
        }
        newState.currentPlayer = currentPlayer;
        newState.playerToSymbolMap[player1]  = playerSymbol;
        newState.playerToSymbolMap[player2] = toggleSymbol(playerSymbol);
        this.setState(newState);
    }

    handleCellClick(index) {
        const isSinglePlayerMode = this.state.isSinglePlayer;
        const currentPlayerSymbol = this.state.playerToSymbolMap[this.state.currentPlayer]
        let board = this.state.board.slice();
        board[index] = currentPlayerSymbol;
        const nextPlayer = getNextPlayer(this.state.currentPlayer, this.state.isSinglePlayer);
        const boardStatus = getBoardStatus(board);
        this.setState({board: board, currentPlayer: nextPlayer, status: boardStatus});

        if (isSinglePlayerMode && nextPlayer == playerName.COMPUTER && boardStatus == status.NO_RESULT) {
            this.getComputerMove(board, currentPlayerSymbol);
        }
    }

    async getComputerMove(board, playerSymbol) {
        try{
            console.log("Player symbol" + playerSymbol);
            const boardAfterComputerMove = await getNextBoard(board, playerSymbol);
            console.log("Board after computer move " + boardAfterComputerMove);
            const boardStatus = getBoardStatus(boardAfterComputerMove);
            this.setState({board: boardAfterComputerMove, currentPlayer: playerName.PLAYER, status: boardStatus});
        }
        catch(err) {
            console.log(err);
            this.setState({status: status.API_ERROR});
        }
    }

    handleRestartGameClick() {
        let newState = Object.assign({}, this.state);
        newState.currentPlayer = null;
        newState.apiError = null;
        newState.playerToSymbolMap = {};
        newState.columns = 3;
        newState.board = Array(9).fill(null);
        newState.status = status.NO_RESULT;
        this.setState(newState);
    }

    handleNewGameClick() {
        let newState = Object.assign({}, this.state);
        newState.isSinglePlayer = null;
        newState.apiError = null;
        newState.currentPlayer = null;
        newState.playerToSymbolMap = {};
        newState.columns = 3;
        newState.board = Array(9).fill(null);
        newState.status = status.NO_RESULT;
        this.setState(newState);
    }

    render() {
        const shouldPlayingModeChooserBeDisplayed = (this.state.isSinglePlayer == null);
        const shouldSymbolChooserBeDisplayed = (!shouldPlayingModeChooserBeDisplayed && (this.state.currentPlayer == null));
        const shouldBoardBeDisplayed = !shouldSymbolChooserBeDisplayed && !shouldPlayingModeChooserBeDisplayed

        let boardStatus = this.state.status;

        let isBoardDisabled = this.state.isSinglePlayer && (this.state.currentPlayer == playerName.COMPUTER);
        isBoardDisabled = isBoardDisabled || (boardStatus != status.NO_RESULT);

        console.log("Container state");
        console.log(this.state);

        return (
            <React.Fragment>
                <PlayingModeChooser
                    shouldBeDisplayed={shouldPlayingModeChooserBeDisplayed}
                    onClick={this.handlePlayerClick.bind(this)} />
                <SymbolChooser 
                    shouldBeDisplayed={shouldSymbolChooserBeDisplayed}
                    onClick={this.handleSymbolClick.bind(this)} />
                <Board 
                    shouldBeDisplayed = {shouldBoardBeDisplayed} 
                    status = {boardStatus}
                    currentPlayerName = {this.state.currentPlayer}
                    disabled = {isBoardDisabled} 
                    size = {this.state.columns}
                    board = {this.state.board}
                    onCellClick = {this.handleCellClick.bind(this)}
                    onRestartGame = {this.handleRestartGameClick.bind(this)}
                    onNewGame = {this.handleNewGameClick.bind(this)} />
            </React.Fragment>
        );
    }
}