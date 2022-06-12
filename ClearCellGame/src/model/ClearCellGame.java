package model;

import java.util.Random;

/**
 * This class extends GameModel and implements the logic of the clear cell game.
 *An empty cell is defined as BoardCell.EMPTY. An empty row is defined as one
 * where every cell corresponds to BoardCell.EMPTY.
 */

public class ClearCellGame extends Game {
	protected Random random;
	protected int score = 0;
	
	/**
	 * Defines a board with BoardCell.EMPTY cells. 
	 * The random parameter is used for the generation of random cells. 
	 */
	public ClearCellGame(int maxRows, int maxCols, 
						Random random) {
		super(maxRows, maxCols);
		this.random = random;
	}

	/**
	 * The game is over when the last board row (row with index board.length -1) 
	 * is different from an empty row.
	 */
	public boolean isGameOver() {
		boolean flag = false;
		if (!rowIsEmpty(board.length-1)) {
			flag = true;
		}
		return flag;
	}

	public int getScore() {
		return score;
	}

	/**
	 * Inserts a row of random BoardCell objects if the last board row
	 * (row with index board.length -1) corresponds to the empty row;
	 * otherwise no operation will take place.
	 */
	public void nextAnimationStep() {
		BoardCell[][] temp = new BoardCell[board.length][board[0].length];
		for (int row=0; row<board.length; row++) {
			for (int col=0; col<board[0].length; col++) {
				temp[row][col] = BoardCell.EMPTY;
			}
		}
		if (!isGameOver()) {
			for (int col=0; col<board[0].length; col++) {
				temp[0][col] = BoardCell.getNonEmptyRandomBoardCell(random);
			}
			for (int row=0; row<board.length-1; row++) {
				for (int col=0; col<board[0].length; col++) {
					temp[row+1][col] = board[row][col];
				}
			}
		}
		board = temp;
	}

	/**
	 * Turns to BoardCell.EMPTY the cell selected and any adjacent
	 * surrounding cells in the vertical, horizontal, and diagonal directions that
	 * have the same color. The clearing of adjacent cells will continue as long as
	 * cells have a color that corresponds to the selected cell. Clearing a cell adds 
	 * one point to the game's score.
	 * 
	 * If after processing cells, any rows in the board are empty,those rows will
	 * collapse, moving non-empty rows upward. 
	 * 
	 * If the game has ended no action will take place.
	 */
	public void processCell(int rowIndex, int colIndex) {
		if (!isGameOver() && !board[rowIndex][colIndex].equals(BoardCell.EMPTY)) {
			BoardCell copy = board[rowIndex][colIndex]; 
			board[rowIndex][colIndex] = BoardCell.EMPTY; 
			score++; 
			// Clears sequential horizontal cells 
			if (colIndex < board[0].length-1) { 
				for (int col=colIndex+1; col<board[0].length; col++) { 
					if (!board[rowIndex][col].equals(copy)) { 
						break; 
					} 
					board[rowIndex][col] = BoardCell.EMPTY; 
					score++; 
				}        
			} 
			if (colIndex > 0) { 
				for (int col=colIndex-1; col>=0; col--) { 
					if (!board[rowIndex][col].equals(copy)) { 
						break; 
					} 
					board[rowIndex][col] = BoardCell.EMPTY; 
					score++; 
				} 
			}  
			// Clears sequential vertical cells 
			if (rowIndex < board.length-1) { 
				for (int row=rowIndex+1; row<board.length; row++) { 
					if (!board[row][colIndex].equals(copy)) { 
						break; 
					} 
					board[row][colIndex] = BoardCell.EMPTY; 
					score++; 
				} 
			} 
			if (rowIndex > 0) { 
				for (int row=rowIndex-1; row>=0; row--) { 
					if (!board[row][colIndex].equals(copy)) { 
						break; 
					} 
					board[row][colIndex] = BoardCell.EMPTY; 
					score++; 
				} 
			}                     
			// Clears sequential diagonal cells 
			int row1 = rowIndex+1;
			int col1 = colIndex+1;
			while (row1 >=0 && row1 < board.length 
				   && col1 >=0 && col1 < board[0].length) {
				if (!board[row1][col1].equals(copy)) {
					break;
				}
				board[row1][col1] = BoardCell.EMPTY;
				score++;
				row1++;
				col1++;
			}
			int row2 = rowIndex+1;
			int col2 = colIndex-1;
			while (row2 >=0 && row2 < board.length 
				   && col2 >=0 && col2 < board[0].length) {
				if (!board[row2][col2].equals(copy)) {
					break;
				}
				board[row2][col2] = BoardCell.EMPTY;
				score++;
				row2++;
				col2--;
			}
			int row3 = rowIndex-1;
			int col3 = colIndex+1;
			while (row3 >=0 && row3 < board.length 
				   && col3 >=0 && col3 < board[0].length) {
				if (!board[row3][col3].equals(copy)) {
					break;
				}
				board[row3][col3] = BoardCell.EMPTY;
				score++;
				row3--;
				col3++;
			}
			int row4 = rowIndex-1;
			int col4 = colIndex-1;
			while (row4 >=0 && row4 < board.length 
				   && col4 >=0 && col4 < board[0].length) {
				if (!board[row4][col4].equals(copy)) {
					break;
				}
				board[row4][col4] = BoardCell.EMPTY;
				score++;
				row4--;
				col4--;
			}
			
			/* Moves non-empty rows upwards and   
			 * empty rows to the bottom of the board */ 
			for (int checkRow=0; checkRow<board.length; checkRow++) { 
				if (rowIsEmpty(checkRow)) { 
					BoardCell[][] temp  
					= new BoardCell[board.length][board[0].length]; 
					for (int row=0; row<checkRow; row++) { 
						for (int col=0; col<board[0].length; col++) { 
							temp[row][col] = board[row][col]; 
						} 
					} 
					for (int row=checkRow+1; row<board.length; row++) { 
						for (int col=0; col<board[0].length; col++) { 
							temp[row-1][col] = board[row][col]; 
						} 
					} 
					for (int col=0; col<board[0].length; col++) { 
						temp[board.length-1][col] = BoardCell.EMPTY; 
					} 
					board = temp; 
				} 
			}                
		}                  		
	}
	
	/**
	 * Checks if a specified row on the board is empty.
	 * Returns true if all cells in the row are empty and false otherwise 
	 */
	private boolean rowIsEmpty(int row) {
		boolean flag = true;
		for (int col=0; col<board[0].length; col++) {
			if (!board[row][col].equals(BoardCell.EMPTY)) {
				flag = false;
			}
		}
		return flag;
	}
}