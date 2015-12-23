package gameOfLifePack;

import java.awt.Dimension;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ScreenJFrame extends JFrame {

	private static final Dimension SCREEN_SIZE = new Dimension(600, 600),
			SCREEN_OFFSET = new Dimension(16, 39);
	private static final String SCREEN_TITLE_BAR = "Game of Life";

	public static void main(String[] args) {
        ScreenJFrame screenJframe = new ScreenJFrame();
        ScreenJPanel screenJPanel = new ScreenJPanel();
        screenJframe.add(screenJPanel);
        screenJframe.setVisible(true);
        screenJPanel.start();
	}
	
	public ScreenJFrame() {
		setTitle(SCREEN_TITLE_BAR);
		setSize(SCREEN_SIZE.width + SCREEN_OFFSET.width, SCREEN_SIZE.height + SCREEN_OFFSET.height);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
	}

}
