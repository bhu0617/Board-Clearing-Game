package model;

/**
 * This class represents the logic of a game where a board is updated on each
 * step of the game animation. The board can also be updated by selecting a
 * board cell.
 */

public abstract class Game {
	protected BoardCell[][] board;
	protected int maxRows;
	protected int maxCols;

	/**
	 * Defines a board with BoardCell.EMPTY cells.
	 */
	public Game(int maxRows, int maxCols) {
		this.maxRows = maxRows;
		this.maxCols = maxCols;
		board = new BoardCell[maxRows][maxCols];
		for (int row=0; row<board.length; row++) {
			for (int col=0; col<board[0].length; col++) {
				board[row][col] = BoardCell.EMPTY;
			}
		}
	}

	public int getMaxRows() {
		return maxRows;
	}

	public int getMaxCols() {
		return maxCols;
	}

	public void setBoardCell(int rowIndex, int colIndex, BoardCell cell) {
		board[rowIndex][colIndex] = cell;
	}

	public BoardCell getBoardCell(int rowIndex, int colIndex) {
		return board[rowIndex][colIndex];
	}

	/**
	 * Sets row with the specified color.
	 */
	public void setRowWithColor(int rowIndex, BoardCell cell) {
		for (int col=0; col<board[0].length; col++) {
			board[rowIndex][col] = cell;
		}
	}

	/**
	 * Initializes column with the specified color.
	 */
	public void setColWithColor(int colIndex, BoardCell cell) {
		for (int row=0; row<board.length; row++) {
			board[row][colIndex] = cell;
		}
	}

	/**
	 * Initializes the board with the specified color.
	 */
	public void setBoardWithColor(BoardCell cell) {
		for (int row=0; row<board.length; row++) {
			for (int col=0; col<board[0].length; col++) {
				board[row][col] = cell;
			}
		}
	}

	public abstract boolean isGameOver();

	public abstract int getScore();

	public abstract void nextAnimationStep();

	public abstract void processCell(int rowIndex, int colIndex);
}