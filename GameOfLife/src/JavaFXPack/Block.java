package JavaFXPack;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class Block {
	private boolean alive;
	private boolean futureAlive;
	private Rectangle rectangle;
	
	public Block(int x, int y, int width, int height) {
		
		futureAlive = false;
		rectangle = new Rectangle(width, height);
		rectangle.setStrokeType(StrokeType.OUTSIDE);
		rectangle.setStroke(Color.web("gray", 1));
		
		changeAlive(alive);
		
		rectangle.setX(x * width);
		rectangle.setY(y * height);
		
		rectangle.setOnMousePressed(e -> alternateAlive());
	}
	
	public void alternateAlive() {
		changeAlive(!alive);
	}
	
	private void changeAlive(boolean b) {
		alive = b;
		rectangle.setFill(alive ? Color.BLACK : Color.WHITE);
	}
	
	public void setFutureAlive(Block[][] bl, int x, int y) {
		
		byte aliveNear = 0;
		
		int maxX = Math.min(x + 2, bl.length);
		int maxY = Math.min(y + 2, bl[0].length);
		
		for (int i = Math.max(0, x - 1); i < maxX; i++) {
			for (int j = Math.max(0, y - 1); j < maxY; j++) {
				if (bl[i][j].isAlive()) {
					aliveNear++;
				}
			}
		}
		futureAlive = ((aliveNear == 3) ? true : (isAlive() && aliveNear == 4));
	}

	public Rectangle getRectangle() {
		return rectangle;
	}
	
	public void stepFuture() {
		changeAlive(futureAlive);
	}
	
	public boolean isAlive() {
		return alive;
	}

	public void changeWidth(int x, int width) {
		rectangle.setX(x);
		rectangle.setWidth(width);
	}
	
	public void changeHeight(int y, int height) {
		rectangle.setY(y);
		rectangle.setHeight(height);
	}
	
}
