package gameOfLifePack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Game {
	
	private int width;
	private boolean[][] board;
	private boolean changingTo = false;
	
	ArrayList<Line2D> lin = new ArrayList<Line2D>();
	
	public Game(int w) {
		board = new boolean[w][w];
		width = w;
	}

	public void move() {
		boolean[][] newBoard = new boolean[width][width];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < width; j++) {
				int neighbors = 0;
				int cellXMax = Math.min(i + 2, width);
				int cellYMax = Math.min(j + 2, width);
				for (int cellX = Math.max(i - 1, 0); cellX < cellXMax; cellX++) {
					for (int cellY = Math.max(j - 1, 0); cellY < cellYMax; cellY++) {
						neighbors += board[cellX][cellY] ? 1 : 0;
					}	
				}
				newBoard[i][j] = ((neighbors == 3) ? true : (board[i][j] && neighbors == 4));
			}
		}
		board = newBoard;
	}

	public void paint(Graphics2D g, Dimension screenSize, boolean paused) {
		Dimension boardSize = new Dimension(
				screenSize.width / width,
				screenSize.height / width);
    	for (int x = 0; x < width; x++) {
    		for (int y = 0; y < width; y++) {
        		if (board[x][y]) {
        			g.setColor(Color.BLACK);
        			g.fillRect(
        					x * boardSize.width, y * boardSize.height,
        					boardSize.width, boardSize.height);
        		}
        		else {
        			g.setColor(paused ? Color.BLUE : Color.LIGHT_GRAY);
        			g.drawRect(
        					x * boardSize.width, y * boardSize.height, 
        					boardSize.width, boardSize.height);
        		}
        	}
    	}
    	g.setColor(Color.GREEN);
    	for (Line2D l :lin) {
    		g.draw(l);
    	}
	}

	public int getBoardWidth() {
		return width;
	}
	
	public void endChange() {
		lin.clear();
	}

	public void change(Line2D line2d, Dimension screenSize) {
		if (line2d == null) {
			return;
		}
		
		Dimension boardSize = new Dimension(screenSize.width / width, screenSize.height / width);
		Point start = new Point(), end = new Point();
		
		
		if (line2d.getX1() < line2d.getX2()) {
			start.x = (int)line2d.getX1() / boardSize.width;
			end.x = (int)line2d.getX2() / boardSize.width;
		}
		else {
			start.x = (int)line2d.getX2() / boardSize.width;
			end.x = (int)line2d.getX1() / boardSize.width;
		}
		if (line2d.getY1() < line2d.getY2()) {
			start.y = (int)line2d.getY1() / boardSize.height;
			end.y = (int)line2d.getY2() / boardSize.height;
		}
		else {
			start.y = (int)line2d.getY2() / boardSize.height;
			end.y = (int)line2d.getY1() / boardSize.height;
		}
		changeBoard(start, end, line2d, boardSize);
		lin.add(line2d);
	}
	
	private void changeBoard(Point start, Point end, Line2D line2d, Dimension boardSize) {
		
		checkBounds(start);
		checkBounds(end);
		
		for (int i = start.x; i <= end.x; i++) {
			for (int j = start.y; j <= end.y; j++) {
				if (line2d.intersectsLine(i * boardSize.width, j * boardSize.height, i * boardSize.width, (j+1) * boardSize.height) ||
					line2d.intersectsLine(i * boardSize.width, j * boardSize.height, (i+1) * boardSize.width, j * boardSize.height) ||
					line2d.intersectsLine((i+1) * boardSize.width, (j+1) * boardSize.height, i * boardSize.width, (j+1) * boardSize.height) ||
					line2d.intersectsLine((i+1) * boardSize.width, (j+1) * boardSize.height, (i+1) * boardSize.width, j * boardSize.height)) {
						if (board[i][j] != changingTo) {
							board[i][j] = changingTo;
						}
				}
			}
		}
	}

	private void checkBounds(Point start) {
		if (start.x >= width) {
			start.x = width - 1;
		}
		else if (start.x < 0) {
			start.x = 0;
		}
		if (start.y >= width) {
			start.y = width - 1;
		}
		else if (start.y < 0) {
			start.y = 0;
		}
		
	}

	public void startChange(Point currentPoint, Dimension screenSize) {
		if (currentPoint == null) {
			System.out.println("ERROR: (firstPoint == null)  class:game");
			return;
		}
		
		changingTo = !board[currentPoint.x / (screenSize.width / width)][currentPoint.y / (screenSize.height / width)];
		board[currentPoint.x / (screenSize.width / width)][currentPoint.y / (screenSize.height / width)] = changingTo;
	}
	
	
}
