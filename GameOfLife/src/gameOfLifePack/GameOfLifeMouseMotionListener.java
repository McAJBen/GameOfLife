package gameOfLifePack;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class GameOfLifeMouseMotionListener implements MouseMotionListener, MouseListener {

	
	
	boolean mouseDown = false;
	boolean paused = false;
	boolean resetGame = false;
	Point lastPosition;
	ArrayList<Line2D> lines = new ArrayList<Line2D>();
	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (mouseDown && e.getPoint() != null) {
			lines.add(new Line2D.Double(lastPosition, e.getPoint()));
			lastPosition = e.getPoint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			paused = !paused;
		}
		if (e.getButton() == MouseEvent.BUTTON2) {
			resetGame = true;
		}
		if (e.getButton() == MouseEvent.BUTTON1) {
			mouseDown = true;
			if (e.getPoint() != null) {
				lastPosition = e.getPoint();
			}
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			mouseDown = true;
			if (e.getPoint() != null) {
				lastPosition = e.getPoint();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
		lastPosition = e.getPoint();
	}
	
	public boolean getMouseDown() {
		return mouseDown;
	}

	public Line2D getOldestLine() {
		while (lines.get(0) == null) {
			lines.remove(0);
			if (lines.isEmpty()) {
				return null;
			}
		}
		Line2D l = (Line2D) lines.get(0).clone();
		lines.remove(0);
		return l;
	}

	public boolean hasLine() {
		return !lines.isEmpty();
	}

	public boolean getPaused() {
		return paused;
	}

	public Point getCurrentPoint() {
		if (lastPosition != null) {
			return (Point) lastPosition.clone();
		}
		return null;
	}

	public boolean getResetGame() {
		if (resetGame) {
			resetGame = false;
			return true;
		}
		return false;
	}
}
