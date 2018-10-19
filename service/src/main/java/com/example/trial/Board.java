package com.example.trial;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonValue;

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
	
	public int getBoardLength() {
		return board.length;
	}
	
	/*
	 * Checks if there are no moves left
	 * @param : None
	 * @return : TRUE if there are no empty spaces, FALSE otherwise
	 */
	public boolean isBoardFull() {
		for (int i = 0; i < getBoardLength(); i++) {
			if (board[i] == PlayerSymbol.EMPTY)
				return false;
		}
		return true;
	}
	
	/*
	 * Checks if there is atleast one empty position
	 * @param: None
	 * @return: TRUE if there is atleast one empty position, FALSE otherwise
	 */
	public boolean isAnyPositionEmpty() {
		return !isBoardFull();
	}
	
	public Set<Integer> getEmptyPositions() {
		Set<Integer> emptyPositions = new HashSet<Integer>();
		for (int i = 0; i < getBoardLength(); i++) {
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
			throw new IllegalArgumentException("Position not within [0," + getBoardLength());
		}
		board[position] = symbol;
	}
	
	public PlayerSymbol getPlayerAtPosition(int position) {
		if (!isIndexWithinRange(position)) {
			throw new IllegalArgumentException("Position not within [0," + getBoardLength());
		}
		return board[position];
	}
	
	/*
	 * Gives the winner of the game
	 * @param None
	 * @return The ENUM of the player who has won the game
	 */
	public PlayerSymbol getWinner() {
		PlayerSymbol winner = PlayerSymbol.EMPTY;
		int i = 0, j = 0;
		
		//search for winner along the rows
		for (i = 0; i < boardSize; i++) {
			for (j = 0; j < boardSize-1; j++) {
				PlayerSymbol currentSymbol = board[getIndex(i, j)];
				PlayerSymbol nextSymbol = board[getIndex(i, j+1)];
				if (currentSymbol != nextSymbol) {
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
					if (currentSymbol != nextSymbol) {
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
				if (currentSymbol != nextSymbol) {
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
				if (currentSymbol != nextSymbol) {
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
		if (index >= 0 && index < getBoardLength())
			return true;
		return false;
	}
	
	private int getIndex(int row, int column) {
		return row*boardSize+column;
	}
	
	public PlayerSymbol[] getBoard() {
		return board.clone();
	}

	@JsonValue
	@Override
	public String toString() {
		String result = "[";
		int boardLength = getBoardLength();
		for (int i = 0; i < boardLength - 1; i++) {
			result += "\"" + board[i].getRepresentation() + "\"" + ",";
		}
		result += "\"" + board[board.length - 1].getRepresentation() + "\"" + "]";
		return result;
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
