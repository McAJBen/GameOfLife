package gameOfLifePack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ScreenJPanel extends JPanel {
	
	private static final int FPS = 60,
			TICK = 1;
	private static final double FPS_TIME = 1000000000 / (double)FPS,
			TICK_TIME = 1000000 * (double)TICK;
	
	private long lastFrameTime;
	private long lastTickTime;
	private Game game;
	GameOfLifeMouseMotionListener mouseListener;

	public ScreenJPanel() {
		game = new Game(50);
		mouseListener = new GameOfLifeMouseMotionListener();
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);
		lastFrameTime = System.nanoTime();
		lastTickTime = System.nanoTime();
	}
	
	public void start() {
		lastFrameTime = System.nanoTime();
		lastTickTime = System.nanoTime();
		while (true) {
			if (!mouseListener.getMouseDown() && !mouseListener.getPaused()) {
				getFrame();
				getTick();
			}
			else {
				getFrame();
				if (mouseListener.getMouseDown()) {
					game.startChange(mouseListener.getCurrentPoint(), getWindowSize());
				}
				while (mouseListener.getMouseDown()) {
					getFrame();
					while (mouseListener.hasLine()) {
			    		game.change(mouseListener.getOldestLine(), getWindowSize());
			    	}
				}
				game.endChange();
	    		setTick();
			}
			if (mouseListener.getResetGame()) {
				game = new Game((game.getBoardWidth() + 50 <= 300) ? game.getBoardWidth() + 50 : 50);
			}
		}
	}
	
	public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.WHITE);
		g2d.fillRect(0, 0, getWindowSize().width, getWindowSize().height);
		game.paint(g2d, getWindowSize(), mouseListener.getPaused() || mouseListener.getMouseDown());
    }
	private void setTick() {
		lastTickTime = System.nanoTime();
	}

	private boolean getTick() {
		if (System.nanoTime() - TICK_TIME > lastTickTime) {
    		lastTickTime += TICK_TIME;
    		game.move();
    		if (System.nanoTime() - 2 * TICK_TIME > lastTickTime) {
    			System.out.println("Movement is behind! " + (System.nanoTime() - lastTickTime) / 1000000 + "ms");
    		}
    		return true;
    	}
		return false;
	}
	
	private boolean getFrame() {
		if (System.nanoTime() - FPS_TIME > lastFrameTime) {
    		lastFrameTime += FPS_TIME;
    		repaint();
    		if (System.nanoTime() - 2 * FPS_TIME > lastFrameTime) {
    			System.out.println("Drawing is behind! " + (System.nanoTime() - lastFrameTime) / 1000000 + "ms");
        		if (System.nanoTime() - FPS_TIME * 10 > lastFrameTime) {
        			lastFrameTime = System.nanoTime();
        		}
    		}
    		return true;
    	}
		return false;
	}
	
	private Dimension getWindowSize() {
		return new Dimension(getSize().width, getSize().height);
	}
}