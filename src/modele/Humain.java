package modele;

import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Humain {
	
	private Circle circle;
	private TranslateTransition transition;
	
	public Humain(Group root) {
		circle = new Circle();
		
		circle.setRadius(100);
		circle.setFill(Color.CRIMSON);
		
		circle.setTranslateX(-200);
		circle.setTranslateY((int)(Math.random()*800));
		
		root.getChildren().add(circle);
		
		traverser();
	}
	
	public void traverser() {
		//Après 10 secondes de pause, l'humain traverse 
		Task<Void> sleeper = new Task<Void>() {
            protected Void call() throws Exception {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            public void handle(WorkerStateEvent event) {
        		transition = new TranslateTransition();
        		transition.setToX(1000-circle.getTranslateX());
        		transition.setToY((int)(Math.random()*800));
        		transition.setDuration(Duration.seconds(5));
                transition.setNode(circle);
                transition.play();
            	
              //Après la traversé, on rappelle la fonction pour le prochain passage
                transition.setOnFinished(new EventHandler<ActionEvent>() {
                	public void handle(ActionEvent event) {
                		traverser();
        			}
                });
            }
        });
        new Thread(sleeper).start();
	}
}
