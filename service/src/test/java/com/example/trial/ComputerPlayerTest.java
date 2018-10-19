package com.example.trial;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import java.util.Arrays;
import org.junit.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=GameApplicationContext.class,loader=AnnotationConfigContextLoader.class)
public class ComputerPlayerTest {
	
	private PlayerSymbol[] testBoard1 = {PlayerSymbol.COMPUTER, PlayerSymbol.PLAYER, PlayerSymbol.COMPUTER,
			PlayerSymbol.PLAYER, PlayerSymbol.EMPTY, PlayerSymbol.COMPUTER,
			PlayerSymbol.EMPTY, PlayerSymbol.EMPTY, PlayerSymbol.EMPTY};
	
	private PlayerSymbol[] testBoard2 = {PlayerSymbol.COMPUTER, PlayerSymbol.PLAYER, PlayerSymbol.PLAYER,
			PlayerSymbol.COMPUTER, PlayerSymbol.EMPTY, PlayerSymbol.EMPTY,
			PlayerSymbol.EMPTY, PlayerSymbol.EMPTY, PlayerSymbol.EMPTY};
	
	private PlayerSymbol[] defensiveBoard = {PlayerSymbol.COMPUTER, PlayerSymbol.EMPTY, PlayerSymbol.PLAYER,
			PlayerSymbol.EMPTY, PlayerSymbol.PLAYER, PlayerSymbol.EMPTY,
			PlayerSymbol.EMPTY, PlayerSymbol.EMPTY, PlayerSymbol.EMPTY};
	
	private PlayerSymbol[] defensiveBoard1 = {PlayerSymbol.PLAYER, PlayerSymbol.COMPUTER, PlayerSymbol.EMPTY,
			PlayerSymbol.PLAYER, PlayerSymbol.EMPTY, PlayerSymbol.EMPTY,
			PlayerSymbol.EMPTY, PlayerSymbol.PLAYER, PlayerSymbol.COMPUTER};
	
	private PlayerSymbol[] boardWithWinnerInRow = {PlayerSymbol.COMPUTER, PlayerSymbol.EMPTY, PlayerSymbol.COMPUTER,
			PlayerSymbol.PLAYER, PlayerSymbol.PLAYER, PlayerSymbol.PLAYER,
			PlayerSymbol.COMPUTER, PlayerSymbol.COMPUTER, PlayerSymbol.EMPTY};
	
	@Autowired
	private ComputerPlayer computerPlayer;
	
	@Test
	public void testApplicationContextLoading() {
		Assert.assertNotNull(computerPlayer);
	}
	
	@Test
	public void testFindNextBestMoveWhenNotInDanger() {
		Board board1 = new Board(testBoard1);
		Board nextBoard = computerPlayer.findNextBestMove(board1);
		
		PlayerSymbol[] expectedBoardForTestBoard1 = testBoard1.clone();
		expectedBoardForTestBoard1[8] = PlayerSymbol.COMPUTER;
		
		Assert.assertTrue(Arrays.equals(expectedBoardForTestBoard1, nextBoard.getBoard()));
		
		Board board2 = new Board(testBoard2);
		nextBoard = computerPlayer.findNextBestMove(board2);
		
		PlayerSymbol[] expectedBoardForTestBoard2 = testBoard2.clone();
		expectedBoardForTestBoard2[6] = PlayerSymbol.COMPUTER;
		
		Assert.assertTrue(Arrays.equals(expectedBoardForTestBoard2, nextBoard.getBoard()));
	}
	
	@Test
	public void testFindNextBestMoveForEndedGame() {
		Board board1 = new Board(boardWithWinnerInRow);
		Board nextBoard = computerPlayer.findNextBestMove(board1);
		
		Assert.assertNull(nextBoard);
	}
	
	@Test
	public void testFindNextBestMoveWhenInDanger() {
		Board board1 = new Board(defensiveBoard);
		Board nextMove = computerPlayer.findNextBestMove(board1);
		
		PlayerSymbol[] expectedDefensiveBoard = defensiveBoard.clone();
		expectedDefensiveBoard[6] = PlayerSymbol.COMPUTER;
		
		Assert.assertTrue(Arrays.equals(expectedDefensiveBoard, nextMove.getBoard()));
		
		board1 = new Board(defensiveBoard1);
		nextMove = computerPlayer.findNextBestMove(board1);
		
		expectedDefensiveBoard = defensiveBoard1.clone();
		expectedDefensiveBoard[6] = PlayerSymbol.COMPUTER;
		
		Assert.assertTrue(Arrays.equals(expectedDefensiveBoard, nextMove.getBoard()));
	}
}
