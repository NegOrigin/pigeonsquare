package modele;

import java.util.ArrayList;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Pigeon {
	
	private Circle circle;
	private TranslateTransition transition;
	
	Graine graineAppetissante;
	double distanceMin;
	
	int fuiteX;
	int fuiteY;
	double distanceFuite;
	boolean enFuite = false;
	
	public Pigeon(Group root) {
		circle = new Circle();
		transition = new TranslateTransition();
		
		circle.setRadius(38);
		circle.setFill(Color.FUCHSIA);

		circle.setTranslateX((int)(Math.random()*1000));
		circle.setTranslateY((int)(Math.random()*800));
		
		root.getChildren().add(circle);
	}
	
	public void allerManger(ArrayList<Graine> graines) {
		//Si pigeon non en fuite
		if(!enFuite) {
			//Réinitialisation recherche
			graineAppetissante = null;
			distanceMin = 5000;
			
			//Repérer la graine la plus appetissante
			for (int i = 0; i < graines.size(); i++) {
				Graine graine = graines.get(i);
				
				//Test graine bonne
				if(graine.getStatut() == 0) {
					Circle graineCircle = graine.getCircle();
					double distance;
					
					//Calcul distance
					distance = Math.sqrt(
							Math.pow(
								Math.abs(graineCircle.getTranslateX()
								- circle.getTranslateX()), 2)
							+ Math.pow(
								Math.abs(graineCircle.getTranslateY()
								- circle.getTranslateY()), 2));
					
					//Test distance plus courte
					if(distance < distanceMin) {
						//Mise à jour graine cible
						graineAppetissante = graine;
						distanceMin = distance;
					}
				}
			}
			
			//Aller vers la graine appetissante
			transition.stop();
			transition.setToX(graineAppetissante.getCircle().getTranslateX());
			transition.setToY(graineAppetissante.getCircle().getTranslateY());
			transition.setDuration(Duration.seconds(distanceMin*0.01));
	        transition.setNode(circle);
	        transition.play();
	        
	        //Manger graine
	        transition.setOnFinished(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent event) {
	        		graineAppetissante.etreMange();
	        		//fuir();
				}
	        });
		}
	}
	
	public void fuir() {
		//Si pigeon non en fuite
		if(!enFuite) {
			//Choix point de fuite
			fuiteX = (int)(Math.random()*1000);
			fuiteY = (int)(Math.random()*800);
			
			//Calcul distance de fuite
			distanceFuite = Math.sqrt(
					Math.pow(
						Math.abs(fuiteX
						- circle.getTranslateX()), 2)
					+ Math.pow(
						Math.abs(fuiteY
						- circle.getTranslateY()), 2));
			
			//Prendre la fuite
			transition.stop();
			transition.setToX(fuiteX);
			transition.setToY(fuiteY);
			transition.setDuration(Duration.seconds(distanceFuite*0.005));
	        transition.setNode(circle);
	        transition.play();
	        
	        //Gestion état en fuite
	        enFuite = true;
	        transition.setOnFinished(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent event) {
	        		enFuite = false;
				}
	        });
		}
	}
	
	public Circle getCircle() {
		return circle;
	}
}
