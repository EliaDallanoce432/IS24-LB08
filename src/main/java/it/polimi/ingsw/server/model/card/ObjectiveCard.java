package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.ObjectiveCardStrategy.*;

/**
 * This class represents a Objective card
 */
public class ObjectiveCard extends Card {
    protected int points;
    protected ObjectiveCardContext context;

    public ObjectiveCard(int id) {
        this.setId(id);
        switch (id) {
            case 87 -> {
                points = 2;
                context = new ObjectiveCardContext(new ObjectiveCardDiagonalFungi());
            }case 88 -> {
                points = 2;
                context = new ObjectiveCardContext(new ObjectiveCardDiagonalPlants());
            }
            case 89 -> {
                points = 2;
                context = new ObjectiveCardContext(new ObjectiveCardDiagonalAnimal());
            }
            case 90 -> {
                points = 2;
                context = new ObjectiveCardContext(new ObjectiveCardDiagonalInsect());
            }
            case 91 -> {
                points = 3;
                context = new ObjectiveCardContext(new ObjectiveCardJPatternFFP());
            }
            case 92 -> {
                points = 3;
                context = new ObjectiveCardContext(new ObjectiveCardJPatternPPI());
            }
            case 93 -> {
                points = 3;
                context = new ObjectiveCardContext(new ObjectiveCardJPatternAAF());
            }
            case 94 -> {
                points = 3;
                context = new ObjectiveCardContext(new ObjectiveCardJPatternIIA());
            }
            case 95 -> {
                points = 2;
                context = new ObjectiveCardContext(new ObjectiveCardThreeFungi());
            }
            case 96 -> {
                points = 2;
                context = new ObjectiveCardContext(new ObjectiveCardThreePlant());
            }
            case 97 -> {
                points = 2;
                context = new ObjectiveCardContext(new ObjectiveCardThreeAnimal());
            }
            case 98 -> {
                points = 2;
                context = new ObjectiveCardContext(new ObjectiveCardThreeInsect());
            }
            case 99 -> {
                points = 3;
                context = new ObjectiveCardContext(new ObjectiveCardGoldenTriplet());
            }
            case 100 -> {
                points = 2;
                context = new ObjectiveCardContext(new ObjectiveCardDoubleScroll());
            }
            case 101 -> {
                points = 2;
                context = new ObjectiveCardContext(new ObjectiveCardDoubleInkpot());
            }
            case 102 -> {
                points = 2;
                context = new ObjectiveCardContext(new ObjectiveCardDoubleFeather());
            }
            default -> throw new IllegalStateException("Unexpected value: " + id);
        }
    }
    public int getPoints() {
        return points;
    }

    /**
     * method returns the points earned with this objective card
     * @param gameField game field on which the objective has to be checked
     * @return int
     */
    public int getEarnedPoints(GameField gameField) {
        return context.executePointsCalculation(this.getPoints(),gameField);
    }

}
