package JavaFXPack;

import java.awt.Dimension;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class JavaFXMain extends Application {
	
	public static void main(String[] args) {
			launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		
		primaryStage.setTitle("Game of Life - McAJBen");
		GridPane mainGrid = new GridPane();
		
		Group root = new Group();
        
        BlockContainer.setup(new Dimension(800, 560));
        
        root.getChildren().add(BlockContainer.getGroup());
        mainGrid.add(root, 0, 0);
        Menu menu = new Menu();
        mainGrid.add(menu, 0, 1);
        
        Scene scene = new Scene(mainGrid, 800, 600);
        
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                BlockContainer.changeWidth(newSceneWidth.intValue());
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                BlockContainer.changeHeight(newSceneHeight.intValue() - 40);
            }
        });
        
        
        primaryStage.setScene(scene);

        primaryStage.show();
	}
	
	
	
	
	
	
	
	
}
