package modele;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Graine {
	
	private Group root;
	
	private int statut; //0=Bonne, 1=Mangée, 2=Mauvaise
	
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
		
		//Après 5 secondes de pause, si la graine était toujours bonne, elle expire
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
		//Mise à jour du statut de la graine
		this.statut = statut;
		
		//Suppression de l'ancien cercle
		root.getChildren().remove(circle);
		
		//Si la graine a expiré, ajout d'un cercle spécifique
		if(statut == 2) {
			circle.setRadius(5);
			circle.setFill(Color.BLACK);
			root.getChildren().add(circle);
		}
	}
	
	public void etreMange() {
		//Si graine bonne, elle est mangée
		if(statut == 0)
			setStatut(1);
	}
	
	public Circle getCircle() {
		return circle;
	}
}
