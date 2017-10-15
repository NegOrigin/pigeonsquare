package modele;

import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Humain extends Parent {
	
	private Circle circle;
	private TranslateTransition transition;
	
	public Humain() {
		circle = new Circle();
		
		circle.setRadius(100);
		circle.setFill(Color.CRIMSON);
		
		circle.setTranslateX(-200);
		circle.setTranslateY((int)(Math.random()*800));
		
		getChildren().add(circle);
		
		traverser();
	}
	
	public void traverser() {
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
            	
                traverser();
            }
        });
        new Thread(sleeper).start();
	}
}
