package application;
	
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import modele.Graine;
import modele.Humain;
import modele.Pigeon;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Group root = new Group();
			Scene scene = new Scene(root, 1000, 800, Color.GREENYELLOW);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Pigeon Square");
	        primaryStage.setResizable(false);
	        
	        int nbPigeons = 4;
	        ArrayList<Pigeon> pigeons = new ArrayList<Pigeon>();
	        
	        ArrayList<Graine> graines = new ArrayList<Graine>();
	        
	        Humain humain = new Humain();
	        
	        for(int i=0; i<nbPigeons; i++) {
				Pigeon pigeon = new Pigeon();
				pigeons.add(pigeon);
		        
		        root.getChildren().add(pigeon);
			}
	        
	        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					Graine graine = new Graine(event.getSceneX(), event.getSceneY());
			        graines.add(graine);
					
					root.getChildren().add(graine);
					
					//test
					for(Pigeon pigeon : pigeons) {
						pigeon.allerManger(graines);
					}
				}
			});
	        
	        root.getChildren().add(humain);
	        
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
