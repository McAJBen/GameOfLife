package JavaFXPack;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class Block {
	private boolean alive;
	private byte 
		neighbors,
		history;
	private Rectangle rectangle;
	
	public Block(int x, int y, int width, int height) {
		history = 100;
		rectangle = new Rectangle(width, height);
		rectangle.setStrokeType(StrokeType.OUTSIDE);
		rectangle.setStroke(Color.web("gray", 1));
		
		changeAlive(alive);
		
		rectangle.setX(x * width);
		rectangle.setY(y * height);
		
		rectangle.setOnMousePressed(e -> alternateAlive());
	}
	
	private void alternateAlive() {
		changeAlive(!alive);
	}
	
	private void changeAlive(boolean b) {
		alive = b;
		rectangle.setFill(alive ? Color.BLACK : getWhite());
	}
	
	public void setNear(Block[][] bl, int x, int y) {
		if (alive) {
			int maxX = Math.min(x + 2, bl.length);
			int maxY = Math.min(y + 2, bl[0].length);
			int minY = Math.max(0, y - 1);
			
			for (int i = Math.max(0, x - 1); i < maxX; i++) {
				for (int j = minY; j < maxY; j++) {
					bl[i][j].addNeighbor();
				}
			}
		}
	}
	
	private void addNeighbor() {
		neighbors++;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}
	
	public void stepFuture() {
		if (neighbors == 3 ? true : (alive && neighbors == 4)) {
			changeAlive(true);
		}
		else {
			changeAlive(false);
		}
		if (alive) {
			history = 50;
		}
		else if (history < 100) {
			history++;
		}
		neighbors = 0;
	}

	public void changeWidth(int x, int width) {
		rectangle.setX(x);
		rectangle.setWidth(width);
	}
	
	public void changeHeight(int y, int height) {
		rectangle.setY(y);
		rectangle.setHeight(height);
	}
	
	private Color getWhite() {
		double c = (double)history / Byte.MAX_VALUE;
		return new Color(c, c, c, 1);
	}
}
