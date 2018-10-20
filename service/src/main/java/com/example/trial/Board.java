package com.example.trial;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Board {
	private PlayerSymbol board[];
	private int boardSize;
	
	/*
	 * Default constructor is required for Jackson to convert the JSON string to a Board object
	 */
	public Board() {
		super();
	}

	public Board(PlayerSymbol[] board) {
		this.board = board;
		boardSize = (int)Math.sqrt(board.length);
	}
	
	@JsonCreator
	public Board(
			@JsonProperty("board") PlayerSymbol[] board,
			@JsonProperty("boardSize") int boardSize) {
		this.board = board;
		this.boardSize = boardSize;
	}
	
	public Set<Integer> emptyPositions() {
		Set<Integer> emptyPositions = new HashSet<Integer>();
		for (int i = 0; i < board.length; i++) {
			if (board[i] == PlayerSymbol.EMPTY) {
				emptyPositions.add(i);
			}
		}
		return emptyPositions;
	}
	
	/*
	 * Places a player at a position in the board
	 * @param symbol The ENUM of the player who is making the move
	 * @param position The index where the move is to be made
	 */
	public void setPlayerAtPosition(PlayerSymbol symbol, int position) throws IllegalArgumentException{
		if (!isIndexWithinRange(position)) {
			throw new IllegalArgumentException("Position not within [0," + board.length);
		}
		board[position] = symbol;
	}
	
	public PlayerSymbol getPlayerAtPosition(int position) {
		if (!isIndexWithinRange(position)) {
			throw new IllegalArgumentException("Position not within [0," + board.length);
		}
		return board[position];
	}
	
	/*
	 * Gives the winner of the game
	 * @param None
	 * @return The ENUM of the player who has won the game
	 */
	public PlayerSymbol winner() {
		PlayerSymbol winner = PlayerSymbol.EMPTY;
		int i = 0, j = 0;
		
		//search for winner along the rows
		for (i = 0; i < boardSize; i++) {
			for (j = 0; j < boardSize-1; j++) {
				PlayerSymbol currentSymbol = board[getIndex(i, j)];
				PlayerSymbol nextSymbol = board[getIndex(i, j+1)];
				if (currentSymbol != nextSymbol || currentSymbol == PlayerSymbol.EMPTY) {
					break;
				}
			}
			if (j == boardSize-1) {
				winner = board[getIndex(i, j)];
			}
		}
		
		//search for winner along the columns
		if (winner == PlayerSymbol.EMPTY) {
			for (i = 0; i < boardSize; i++) {
				for (j = 0; j < boardSize-1; j++) {
					PlayerSymbol currentSymbol = board[getIndex(j, i)];
					PlayerSymbol nextSymbol = board[getIndex(j+1, i)];
					if (currentSymbol != nextSymbol || currentSymbol == PlayerSymbol.EMPTY) {
						break;
					}
				}
				if (j == boardSize-1) {
					winner = board[getIndex(j, i)];
				}
			}
		}
		
		//search for winner along the primary diagonal
		if (winner == PlayerSymbol.EMPTY) {
			for (i = 0; i < boardSize-1; i++) {
				PlayerSymbol currentSymbol = board[getIndex(i, i)];
				PlayerSymbol nextSymbol = board[getIndex(i+1, i+1)];
				if (currentSymbol != nextSymbol || currentSymbol == PlayerSymbol.EMPTY) {
					break;
				}
			}
			if (i == boardSize-1) {
				winner = board[getIndex(i, i)];
			}
		}
		
		//search for winner along the secondary diagonal
		if (winner == PlayerSymbol.EMPTY) {
			i = boardSize - 1;
			j = 0;
			while ( i > 0) {
				PlayerSymbol currentSymbol = board[getIndex(i, j)];
				PlayerSymbol nextSymbol = board[getIndex(i-1, j+1)];
				if (currentSymbol != nextSymbol || currentSymbol == PlayerSymbol.EMPTY) {
					break;
				}
				i--;
				j++;
			}
			if (i == 0) {
				winner = board[getIndex(i, j)];
			}
		}
		return winner;
	}
	
	private boolean isIndexWithinRange(int index){
		if (index >= 0 && index < board.length)
			return true;
		return false;
	}
	
	private int getIndex(int row, int column) {
		return row*boardSize+column;
	}
	
	public void setBoard(PlayerSymbol[] board) {
		this.board = board;
	}
	
	public PlayerSymbol[] getBoard() {
		return board.clone();
	}
	
	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(board);
		result = prime * result + boardSize;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		if (!Arrays.equals(board, other.board))
			return false;
		if (boardSize != other.boardSize)
			return false;
		return true;
	}
}
