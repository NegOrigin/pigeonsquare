package modele;

import java.util.ArrayList;

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
	
	private ArrayList<Pigeon> pigeons;
	
	private double distancePigeon;
	
	public Humain(Group root, ArrayList<Pigeon> pigeons) {
		circle = new Circle();
		
		circle.setRadius(100);
		circle.setFill(Color.CRIMSON);
		
		circle.setTranslateX(-200);
		circle.setTranslateY((int)(Math.random()*800));
		
		root.getChildren().add(circle);
		
		this.pigeons = pigeons;
		
		traverser();
		faireFuire();
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
	
	public void faireFuire() {
		//Après 0.5 seconde de pause, évaluation de la peur infligée aux pigeons
		Task<Void> sleeper = new Task<Void>() {
            protected Void call() throws Exception {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            public void handle(WorkerStateEvent event) {
            	for(Pigeon pigeon : pigeons) {
            		//Calcul distance humain/pigeon
        			distancePigeon = Math.sqrt(
        					Math.pow(
        						Math.abs(circle.getTranslateX()
        						- pigeon.getCircle().getTranslateX()), 2)
        					+ Math.pow(
        						Math.abs(circle.getTranslateY()
        						- pigeon.getCircle().getTranslateY()), 2));
        			
        			if(distancePigeon < circle.getRadius()*2)
        				pigeon.fuir();
				}
            	
            	//Après l'exécution, on rappelle la fonction
            	faireFuire();
            }
        });
        new Thread(sleeper).start();
	}
}
