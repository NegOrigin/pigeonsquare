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
	
	Graine graineAppetissante = null;
	double distanceMin = 5000;
	
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
		//Repérer la graine la plus appetissante
		for (int i = 0; i < graines.size(); i++) {
			Graine graine = graines.get(i);
			
			if(graine.getStatut() == 0) {
				Circle graineCircle = graine.getCircle();
				double distance;
				
				distance = Math.sqrt(
						Math.pow(
							Math.abs(graineCircle.getTranslateX()
							- circle.getTranslateX()), 2)
						+ Math.pow(
							Math.abs(graineCircle.getTranslateY()
							- circle.getTranslateY()), 2));
						
				if(distance < distanceMin) {
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
        
        transition.setOnFinished(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		graineAppetissante.etreMange();
			}
        });
	}
}
