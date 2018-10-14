package com.example.trial;

import java.util.HashSet;
import java.util.Set;

public class ComputerPlayer {
	/*
	 * Function to find the next best move using minimax algorithm
	 * @param currentBoard The board on which the computer should make the move
	 * @return The board after computer made its move
	 */
	public Board findNextBestMove(Board currentBoard) {
		Board nextBestMove = null;
		int nextBestMoveScore = Integer.MIN_VALUE;
		
		PlayerSymbol[] boardArray = currentBoard.getBoard();
		
		if (getWinner(boardArray).equals(PlayerSymbol.EMPTY)) {
			for (int i = 0; i < boardArray.length; i++) {
				if (boardArray[i].equals(PlayerSymbol.EMPTY)) {
					boardArray[i] = PlayerSymbol.COMPUTER;
				
					int boardValue = minimax(boardArray, 0, PlayerSymbol.PLAYER);
				
					if (boardValue > nextBestMoveScore) {
						nextBestMoveScore = boardValue;
						nextBestMove = new Board(boardArray.clone());
					}
			
					boardArray[i] = PlayerSymbol.EMPTY;
				}
			}
		}
		return nextBestMove;
	}
	
	/*
	 * Function to find the value of a board in the terminal state
	 * @param currentBoard The board with no moves to make
	 * @param depth The depth at which this board was reached
	 * @return The value of the board
	 */
	private static int evaluateBoard(PlayerSymbol[] board, int depth) {
		int result;
		PlayerSymbol winner = getWinner(board);
		if (winner.equals(PlayerSymbol.COMPUTER)) {
			result = 10 - depth;
		}
		else if (winner.equals(PlayerSymbol.EMPTY)) {
			result = 0;
		}
		else {
			result = -10 + depth;
		}
		return result;
	}
	
	/*
	 * Function to get the best value for the board
	 */
	private static int minimax(PlayerSymbol[] board, int depth, PlayerSymbol playerToMove) {
		int boardValue = evaluateBoard(board, depth);
		if (boardValue != 0) {
			return boardValue;
		}
		if (isBoardFull(board)) {
			return 0;
		}
		
		if (playerToMove.equals(PlayerSymbol.COMPUTER)){
			boardValue = Integer.MIN_VALUE;
			Set<Integer> emptyPositions = getEmptyPositions(board);
			for (int emptyPositionIndex: emptyPositions) {
				board[emptyPositionIndex] = PlayerSymbol.COMPUTER;
				int nextMoveValue = minimax(board, depth+1, PlayerSymbol.PLAYER);
				boardValue = Math.max(boardValue, nextMoveValue);
				board[emptyPositionIndex] = PlayerSymbol.EMPTY;
			}
		}
		else {
			boardValue = Integer.MAX_VALUE;
			Set<Integer> emptyPositions = getEmptyPositions(board);
			for (int emptyPositionIndex: emptyPositions) {
				board[emptyPositionIndex] = PlayerSymbol.PLAYER;
				int nextMoveValue = minimax(board, depth+1, PlayerSymbol.COMPUTER);
				boardValue = Math.min(boardValue, nextMoveValue);
				board[emptyPositionIndex] = PlayerSymbol.EMPTY;
			}
		}
		return boardValue;
	}
	
	private static PlayerSymbol getWinner(PlayerSymbol[] board) {
		PlayerSymbol winner = PlayerSymbol.EMPTY;
		int boardSize = 3;
		int i = 0, j = 0;
		
		//search for winner along the rows
		for (i = 0; i < boardSize; i++) {
			for (j = 0; j < boardSize-1; j++) {
				PlayerSymbol currentSymbol = board[getIndex(i, j, boardSize)];
				PlayerSymbol nextSymbol = board[getIndex(i, j+1, boardSize)];
				if (currentSymbol != nextSymbol) {
					break;
				}
			}
			if (j == boardSize-1) {
				winner = board[getIndex(i, j, boardSize)];
				break;
			}
		}
		
		//search for winner along the columns
		if (winner == PlayerSymbol.EMPTY) {
			for (i = 0; i < boardSize; i++) {
				for (j = 0; j < boardSize-1; j++) {
					PlayerSymbol currentSymbol = board[getIndex(j, i, boardSize)];
					PlayerSymbol nextSymbol = board[getIndex(j+1, i, boardSize)];
					if (currentSymbol != nextSymbol) {
						break;
					}
				}
				if (j == boardSize-1) {
					winner = board[getIndex(j, i, boardSize)];
					break;
				}
			}
		}
		
		//search for winner along the primary diagonal
		if (winner == PlayerSymbol.EMPTY) {
			for (i = 0; i < boardSize-1; i++) {
				PlayerSymbol currentSymbol = board[getIndex(i, i, boardSize)];
				PlayerSymbol nextSymbol = board[getIndex(i+1, i+1, boardSize)];
				if (currentSymbol != nextSymbol) {
					break;
				}
			}
			if (i == boardSize-1) {
				winner = board[getIndex(i, i, boardSize)];
			}
		}
		
		//search for winner along the secondary diagonal
		if (winner == PlayerSymbol.EMPTY) {
			i = boardSize - 1;
			j = 0;
			while ( i > 0) {
				PlayerSymbol currentSymbol = board[getIndex(i, j, boardSize)];
				PlayerSymbol nextSymbol = board[getIndex(i-1, j+1, boardSize)];
				if (currentSymbol != nextSymbol) {
					break;
				}
				i--;
				j++;
			}
			if (i == 0) {
				winner = board[getIndex(i, j, boardSize)];
			}
		}
		return winner;
	}
	
	private static Set<Integer> getEmptyPositions(PlayerSymbol[] board) {
		Set<Integer> emptyPositions = new HashSet<Integer>();
		for (int i = 0; i < board.length; i++) {
			if (board[i].equals(PlayerSymbol.EMPTY)) {
				emptyPositions.add(i);
			}
		}
		return emptyPositions;
	}
	
	private static boolean isBoardFull(PlayerSymbol[] board) {
		for (int i = 0; i < board.length; i++) {
			if (board[i] == PlayerSymbol.EMPTY)
				return false;
		}
		return true;
	}
	
	private static int getIndex(int row, int column, int boardSize) {
		return row*boardSize+column;
	}
}
