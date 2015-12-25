package JavaFXPack;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class Menu extends GridPane {
	
	/*RadioButton
		add,
		remove;*/
	
	TextField
		width,
		height,
		movesPerSecond;
	
	Button 
		applySize,
		playPause,
		step;
	
	boolean playing;
	

	public Menu() {
		setPadding(new Insets(10, 10, 10, 10));
		setHgap(10);
		playing = false;
		/*add =    new RadioButton("Add");
		remove = new RadioButton("Remove");
		ToggleGroup addRemoveGroup = new ToggleGroup();
		
		add.setToggleGroup(addRemoveGroup);
		remove.setToggleGroup(addRemoveGroup);
		add.setSelected(true);
		
		add(add, 0, 0);
		add(remove, 1, 0);*/
		
		
		
		width =  new TextField("25");
		width.setMaxWidth(60);
		width.setMinWidth(40);
		
		height = new TextField("25");
		height.setMaxWidth(60);
		height.setMinWidth(40);
		
		GridPane dimensionPane = new GridPane();
		dimensionPane.setHgap(2);
		dimensionPane.add(width, 0, 0);
		dimensionPane.add(new Label("x"), 1, 0);
		dimensionPane.add(height, 2, 0);
		
		add(dimensionPane, 2, 0);
		
		applySize = new Button("Apply");
		applySize.setMinWidth(50);
		applySize.setOnMouseClicked(new OnApplySize());
		add(applySize, 3, 0);
		
		movesPerSecond = new TextField("10");
		movesPerSecond.setMaxWidth(60);
		movesPerSecond.setMinWidth(40);
		add(movesPerSecond, 4, 0);
		
		playPause = new Button("Play");
		playPause.setMinWidth(60);
		playPause.setOnMouseClicked(new OnPlayPause());
		add(playPause, 5, 0);
		
		step = new Button("Step");
		step.setMinWidth(60);
		step.setOnMouseClicked(new onStep());
		add(step, 6, 0);
		
		
	}
	
	private class onStep implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent arg0) {
			BlockContainer.step();
		}
	}
	
	private class OnApplySize implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			int x = Integer.parseInt(width.getText());
			int y = Integer.parseInt(height.getText());
			
			if (x > 250) {
				x = 250;
				width.setText("250");
			}
			if (y > 250) {
				y = 250;
				height.setText("250");
			}
			BlockContainer.changeDim(x, y);
		}
	}
	
	private class OnPlayPause implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			playing = !playing;
			if (playing) {
				playPause.setText("Pause");
				movesPerSecond.setDisable(true);
				
				BlockContainer.start(movesPerSecond.getText());
			}
			else {
				playPause.setText("Play");
				movesPerSecond.setDisable(false);
				BlockContainer.stop();
			}
		}
	}
}
