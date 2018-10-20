package com.example.trial;

import java.util.Set;

import org.assertj.core.util.Arrays;
import org.junit.Assert;
import org.junit.Test;


public class BoardTest {
	
	private final int boardSize = 3;
	
	private PlayerSymbol[] testBoardEmpty = {PlayerSymbol.EMPTY, PlayerSymbol.EMPTY,PlayerSymbol.EMPTY, 
										PlayerSymbol.EMPTY, PlayerSymbol.EMPTY, PlayerSymbol.EMPTY, 
										PlayerSymbol.EMPTY, PlayerSymbol.EMPTY, PlayerSymbol.EMPTY};
	
	private PlayerSymbol[] testBoardFilled = {PlayerSymbol.COMPUTER, PlayerSymbol.COMPUTER, PlayerSymbol.COMPUTER,
			PlayerSymbol.COMPUTER, PlayerSymbol.PLAYER, PlayerSymbol.PLAYER,
			PlayerSymbol.PLAYER, PlayerSymbol.PLAYER, PlayerSymbol.PLAYER};
	
	private PlayerSymbol[] testBoard1 = {PlayerSymbol.COMPUTER, PlayerSymbol.PLAYER, PlayerSymbol.COMPUTER,
			PlayerSymbol.PLAYER, PlayerSymbol.PLAYER, PlayerSymbol.COMPUTER,
			PlayerSymbol.EMPTY, PlayerSymbol.EMPTY, PlayerSymbol.EMPTY};

	
	private PlayerSymbol[] boardWithWinnerInRow = {PlayerSymbol.COMPUTER, PlayerSymbol.EMPTY, PlayerSymbol.COMPUTER,
			PlayerSymbol.PLAYER, PlayerSymbol.PLAYER, PlayerSymbol.PLAYER,
			PlayerSymbol.COMPUTER, PlayerSymbol.COMPUTER, PlayerSymbol.EMPTY};
	
	private PlayerSymbol[] boardWithWinnerInColumn = {PlayerSymbol.COMPUTER, PlayerSymbol.EMPTY, PlayerSymbol.PLAYER,
			PlayerSymbol.EMPTY, PlayerSymbol.COMPUTER, PlayerSymbol.PLAYER,
			PlayerSymbol.COMPUTER, PlayerSymbol.EMPTY, PlayerSymbol.PLAYER};
	
	private PlayerSymbol[] boardWithWinnerInPrimaryDiagonal = {PlayerSymbol.PLAYER, PlayerSymbol.COMPUTER, PlayerSymbol.COMPUTER,
			PlayerSymbol.EMPTY, PlayerSymbol.PLAYER, PlayerSymbol.PLAYER,
			PlayerSymbol.COMPUTER, PlayerSymbol.EMPTY, PlayerSymbol.PLAYER};
	
	private PlayerSymbol[] boardWithWinnerInSecondaryDiagonal = {PlayerSymbol.COMPUTER, PlayerSymbol.COMPUTER, PlayerSymbol.PLAYER,
			PlayerSymbol.EMPTY, PlayerSymbol.PLAYER, PlayerSymbol.COMPUTER,
			PlayerSymbol.PLAYER, PlayerSymbol.COMPUTER, PlayerSymbol.EMPTY};
	
	/*
	 * Test to check if the board is getting created
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testBoardCreation() {
		Board board = new Board(testBoardEmpty, boardSize);
		Assert.assertEquals(testBoardEmpty, board.getBoard());
	}
	
	/*
	 * Test to check if the board is empty
	 */
	@Test
	public void testBoardEmptyPositions() {
		Board boardFilled = new Board(testBoardFilled, boardSize);
		Assert.assertTrue(boardFilled.emptyPositions().isEmpty());
		
		Board boardEmpty = new Board(testBoardEmpty, boardSize);
		Set<Integer> emptyPositionSet = boardEmpty.emptyPositions();
		int[] positionArray = {0,1,2,3,4,5,6,7,8};
		Assert.assertTrue(emptyPositionSet.containsAll(Arrays.asList(positionArray)));
		
		Board partiallyFilledBoard = new Board(testBoard1, boardSize);
		emptyPositionSet = partiallyFilledBoard.emptyPositions();
		int[] expectedPositionArray = {6,7,8};
		Assert.assertTrue(emptyPositionSet.containsAll(Arrays.asList(expectedPositionArray)));
	}
	
	/*
	 * Test to check if the player can be moved in an empty board
	 */
	@Test
	public void testPlayerMoveEmpty() {
		Board board = new Board(testBoardEmpty, boardSize);
		board.setPlayerAtPosition(PlayerSymbol.COMPUTER, 0);
		
		PlayerSymbol[] expectedBoardNextMove = testBoardEmpty.clone();
		expectedBoardNextMove[0] = PlayerSymbol.COMPUTER;
		Assert.assertArrayEquals(expectedBoardNextMove, board.getBoard());
	}
	
	/*
	 * Test to check if player can move in a partially filled board
	 */
	@Test
	public void testPlayerMovePartiallyEmpty() {
		Board board = new Board(testBoard1, boardSize);
		board.setPlayerAtPosition(PlayerSymbol.COMPUTER, 6);
		
		PlayerSymbol[] expectedBoardNextMove = testBoard1.clone();
		expectedBoardNextMove[6] = PlayerSymbol.COMPUTER;
		Assert.assertArrayEquals(expectedBoardNextMove, board.getBoard());
	}
	
	/*
	 * Test to determine if the winner function is working correctly
	 */
	@Test
	public void testWinner() {
		//Test 1: test with empty board
		Board emptyBoard = new Board(testBoardEmpty, boardSize);
		Assert.assertEquals(PlayerSymbol.EMPTY, emptyBoard.winner());
		
		//Test 2: test with partially filled board with no winner
		Board partiallyFilledBoardWithNoWinner = new Board(testBoard1, boardSize);
		Assert.assertEquals(PlayerSymbol.EMPTY, partiallyFilledBoardWithNoWinner.winner());
		
		//Test 3: test with winner in the row
		Board partiallyFilledBoardWithRowWinner = new Board(boardWithWinnerInRow, boardSize);
		Assert.assertEquals(PlayerSymbol.PLAYER, partiallyFilledBoardWithRowWinner.winner());
		
		//Test 4: test with winner in the column
		Board partiallyFilledBoardWithColumnWinner = new Board(boardWithWinnerInColumn, boardSize);
		Assert.assertEquals(PlayerSymbol.PLAYER, partiallyFilledBoardWithColumnWinner.winner());
		
		//Test 5: test with winner along the primary diagonal
		Board partiallyFilledBoardWithDiagonalWinner = new Board(boardWithWinnerInPrimaryDiagonal, boardSize);
		Assert.assertEquals(PlayerSymbol.PLAYER, partiallyFilledBoardWithDiagonalWinner.winner());
		
		//Test 6: test with winner along the secondary diagonal
		Board partiallyFilledBoardWithSecondaryDiagonalWinner = new Board(boardWithWinnerInSecondaryDiagonal, boardSize);
		Assert.assertEquals(PlayerSymbol.PLAYER, partiallyFilledBoardWithSecondaryDiagonalWinner.winner());
	}
}
