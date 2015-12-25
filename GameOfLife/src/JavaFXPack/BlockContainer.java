package JavaFXPack;

import java.awt.Dimension;

import javafx.scene.Group;

public class BlockContainer implements Runnable {
	private static Thread thread;
	
	private static Dimension blockDim = new Dimension(25, 25);
	private static Dimension blockSize;
	private static Dimension lastWindowSize;
	private static Block[][] blocks;
	private static Group group = new Group();
	private static boolean running;
	private static long msDelay;
	
	public static void setup(Dimension windowSize) {
		lastWindowSize = windowSize;
		group.getChildren().clear();
		blocks = new Block[blockDim.width][blockDim.height];
		blockSize = new Dimension(
			lastWindowSize.width  / blockDim.width,
			lastWindowSize.height / blockDim.height);
		
		for (int i = 0; i < blockDim.width; i++) {
        	for (int j = 0; j < blockDim.height; j++) {
        		blocks[i][j] = new Block(i, j, blockSize.width, blockSize.height);
		        group.getChildren().add(blocks[i][j].getRectangle());
        	}
        }
	}

	public static void changeWidth(int newSceneWidth) {
		lastWindowSize.width = newSceneWidth;
		blockSize.width = newSceneWidth / blockDim.width;
		for (int i = 0; i < blockDim.width; i++) {
			int w = blockSize.width * i;
        	for (int j = 0; j < blockDim.height; j++) {
        		blocks[i][j].changeWidth(w, blockSize.width);
        	}
        }
	}
	
	public static void changeHeight(int newSceneHeight) {
		lastWindowSize.height = newSceneHeight;
		blockSize.height = newSceneHeight / blockDim.height;
		for (int j = 0; j < blockDim.height; j++) {
			int h = blockSize.height * j;
			for (int i = 0; i < blockDim.width; i++) {
        		blocks[i][j].changeHeight(h, blockSize.height);
        	}
        }
	}
	
	public static void step() {
		for (int i = 0; i < blockDim.width; i++) {
        	for (int j = 0; j < blockDim.height; j++) {
        		blocks[i][j].setNear(blocks, i, j);
        	}
        }
		for (int i = 0; i < blockDim.width; i++) {
        	for (int j = 0; j < blockDim.height; j++) {
        		blocks[i][j].stepFuture();
        	}
        }
	}

	public static void changeDim(int width, int height) {
		blockDim = new Dimension(width, height);
		setup(lastWindowSize);
	}

	public static Group getGroup() {
		return group;
	}

	@Override
	public void run() {
		while (true) {
			long startTime = System.currentTimeMillis();
			BlockContainer.step();
			while (startTime + msDelay > System.currentTimeMillis()) {} // wait
		}
	}

	public static void start(String string) {
		
		double mps = Integer.parseInt(string);
		msDelay = (long) (1_000 / mps);
		
		if (thread != null) {
			thread.resume();
		}
		else {
			BlockContainer step = new BlockContainer();
			thread = new Thread(step);
			thread.start();
		}
		
		// TODO fix fast thread errors
		// TODO fix threads staying after they are 'stopped'
	}
	
	public static void stop() {
		running = false;
		thread.suspend();
	}
	
	

	
}
