package modele;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Graine {
	
	private Group root;
	
	private int statut;
	
	private Circle circle;
	
	public Graine(Group root, double x, double y) {
		this.root = root;
		
		statut = 0;
		
		circle = new Circle();
		
		circle.setRadius(10);
		circle.setFill(Color.BURLYWOOD);

		circle.setTranslateX(x);
		circle.setTranslateY(y);
		
		root.getChildren().add(circle);
		
		Task<Void> sleeper = new Task<Void>() {
            protected Void call() throws Exception {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            public void handle(WorkerStateEvent event) {
                if(statut == 0)
            		setStatut(2);
            }
        });
        new Thread(sleeper).start();
	}

	public int getStatut() {
		return statut;
	}

	public void setStatut(int statut) {
		this.statut = statut;
		
		root.getChildren().remove(circle);
		
		if(statut == 2) {
			circle.setRadius(5);
			circle.setFill(Color.BLACK);
			root.getChildren().add(circle);
		}
	}
	
	public void etreMange() {
		setStatut(1);
	}
	
	public Circle getCircle() {
		return circle;
	}
}
