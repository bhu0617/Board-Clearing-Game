package tests;

import static org.junit.Assert.*;


import java.util.Random;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import model.BoardCell;
import model.ClearCellGame;
import model.Game;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class JUnitTests {
	@Test
	public void testConstructorsAndSizeGetters() {
		Game game1 = new ClearCellGame(5,5,new Random(1L));
		Game game2 = new ClearCellGame(6,8,new Random(1L));
		boolean allEmpty1 = true;
		boolean allEmpty2 = true;
		for (int row=0; row<game1.getMaxRows(); row++) {
			for (int col=0; col<game1.getMaxCols(); col++) {
				if (game1.getBoardCell(row, col) != BoardCell.EMPTY) {
					allEmpty1 = false;
				}
			}
		}
		for (int row=0; row<game2.getMaxRows(); row++) {
			for (int col=0; col<game2.getMaxCols(); col++) {
				if (game2.getBoardCell(row, col) != BoardCell.EMPTY) {
					allEmpty2 = false;
				}
			}
		}
		assertTrue(allEmpty1);
		assertTrue(allEmpty2);
		assertTrue(game1.getMaxRows() == 5);
		assertTrue(game1.getMaxCols() == 5);
		assertTrue(game2.getMaxRows() == 6);
		assertTrue(game2.getMaxCols() == 8);
	}
	
	@Test
	public void testSetGetBoardCell() {
		Game game = new ClearCellGame(6,8,new Random(1L));
		game.setBoardCell(1,2,BoardCell.RED);
		game.setBoardCell(0,7,BoardCell.BLUE);
		game.setBoardCell(0,7,BoardCell.EMPTY);
		BoardCell cell1 = game.getBoardCell(1, 2);
		BoardCell cell2 = game.getBoardCell(0, 7);
		assertTrue(cell1.equals(BoardCell.RED));
		assertTrue(cell2.equals(BoardCell.EMPTY));
	}
	
	@Test
	public void testSetRowWithColor() {
		Game game = new ClearCellGame(6,8,new Random(1L));
		game.setBoardCell(1,2,BoardCell.RED);
		game.setRowWithColor(1,BoardCell.BLUE);
		game.setRowWithColor(3,BoardCell.YELLOW);
		assertTrue(game.getBoardCell(1,2).equals(BoardCell.BLUE));
		assertFalse(game.getBoardCell(3,1).equals(BoardCell.EMPTY));
	}
	
	@Test
	public void testSetCowWithColor() {
		Game game = new ClearCellGame(6,8,new Random(1L));
		game.setBoardCell(1,2,BoardCell.RED);
		game.setColWithColor(2,BoardCell.BLUE);
		game.setColWithColor(3,BoardCell.YELLOW);
		assertTrue(game.getBoardCell(1,2).equals(BoardCell.BLUE));
		assertFalse(game.getBoardCell(1,3).equals(BoardCell.EMPTY));
	}
	
	@Test
	public void testSetBoardWithColor() {
		Game game = new ClearCellGame(6,8,new Random(1L));
		game.setBoardCell(1,2,BoardCell.RED);
		game.setBoardWithColor(BoardCell.BLUE);
		assertTrue(game.getBoardCell(1,2).equals(BoardCell.BLUE));
		assertFalse(game.getBoardCell(5,3).equals(BoardCell.EMPTY));
	}
	
	/* Tests the isGameOver method */
	@Test
	public void testIsGameOver() {
		Game game1 = new ClearCellGame(6,8,new Random(1L));
		game1.setBoardCell(5,7,BoardCell.BLUE);
		assertTrue(game1.isGameOver());
		Game game2 = new ClearCellGame(1,2,new Random(1L));
		game2.setBoardCell(0,0,BoardCell.EMPTY);
		assertFalse(game2.isGameOver());
		game2.setBoardCell(0,0,BoardCell.GREEN);
		assertTrue(game2.isGameOver());
	}
	
	@Test
	public void testGetScore() {
		Game game = new ClearCellGame(6,8,new Random(1L));
		game.setRowWithColor(0,BoardCell.BLUE);
		game.setBoardCell(0,7,BoardCell.GREEN);		
		game.processCell(0,1);
		game.processCell(0,7);		
		game.processCell(2,3);
		assertTrue(game.getScore() == 8);
	}
	
	@Test
	public void testNextAnimationStep() {
		Game game = new ClearCellGame(6,8,new Random(1L));
		game.setRowWithColor(0,BoardCell.BLUE);
		game.nextAnimationStep();
		assertTrue(!game.getBoardCell(0,5).equals(BoardCell.EMPTY));
		assertTrue(game.getBoardCell(1,2).equals(BoardCell.BLUE));
		game.setRowWithColor(5, BoardCell.RED);
		game.nextAnimationStep();
		assertTrue(game.getBoardCell(2,3).equals(BoardCell.EMPTY));
	}
	
	@Test
	public void testProcessCell() {
		Game game = new ClearCellGame(8,10,new Random(1L));
		game.setRowWithColor(2, BoardCell.GREEN);
		game.processCell(2, 1);
		assertTrue(game.getBoardCell(2,5).equals(BoardCell.EMPTY));
		game.setRowWithColor(0, BoardCell.BLUE);
		game.setBoardCell(1, 3, BoardCell.BLUE);
		game.setBoardCell(2, 3, BoardCell.BLUE);
		game.processCell(1,3);
		assertTrue(game.getBoardCell(2,3).equals(BoardCell.EMPTY));
		game.setBoardCell(3, 4, BoardCell.YELLOW);
		game.setBoardCell(4, 5, BoardCell.YELLOW);
		game.setBoardCell(5, 6, BoardCell.RED);
		game.processCell(4,5);
		assertTrue(game.getBoardCell(3,4).equals(BoardCell.EMPTY));
		assertFalse(game.getBoardCell(5,6).equals(BoardCell.RED));
		assertTrue(game.getBoardCell(3,6).equals(BoardCell.RED));
	}
}
