package it.polimi.ingsw.client.view;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class VirtualDeck {

    private int bottomIdRange;
    private int topIdRange;

    public VirtualDeck(int bottomIdRange, int topIdRange) {
        //defines range of id of cards in the deck
        this.bottomIdRange = bottomIdRange;
        this.topIdRange = topIdRange;
    }

    public void showDeck(Pane pane) {

        for (int i = 0; i < 3; i++) {

            int randId = (int) (Math.random() * (topIdRange - bottomIdRange) + bottomIdRange);

            VirtualCard vCard = new VirtualCard(randId, i != 0);
            Rectangle cardNode = vCard.getCard();
            cardNode.setLayoutX(50 + i * 150);
            cardNode.setLayoutY(50);

            Button button = new Button("Button " + (i + 1));
            button.setLayoutX(80 + i * 150);
            button.setLayoutY(120);
            int finalI = i; // For use in lambda expression
            button.setOnAction(event -> {
                // Call a method corresponding to the button
                handleButtonClick(finalI);
            });

            pane.getChildren().addAll(cardNode, button);
        }


    }

    private void handleButtonClick(int buttonId) {
        switch (buttonId) {
            case 0:
                System.out.println("Button 1 clicked");
                break;
            case 1:
                System.out.println("Button 2 clicked");
                break;
            case 2:
                System.out.println("Button 3 clicked");
                break;
            default:
                System.out.println("Invalid button index");
        }
    }


}
