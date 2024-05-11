package it.polimi.ingsw.server.model.GoldCardStrategy;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.util.customexceptions.CannotPlaceCardException;
import it.polimi.ingsw.util.supportclasses.Color;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class GoldCardCoveredCornerStrategyTest {

    private static GameController controller;
    private static Game game;
    private GameField gameField;
    private Player player;

    @BeforeAll
    static void setUpBeforeClass() {
        controller = new GameController(null,4,"test");
        game = controller.getGame();
    }

    @BeforeEach
    void setUp() {
        player = new Player(game);
        gameField = new GameField(player);
        gameField.place(new StarterCard(82),true);
    }
    @AfterEach
    void tearDown() {
        game.reinsertToken(player.getToken());
        gameField = null;
    }

    @AfterAll
    static void tearDownAfterClass() {
        controller = null;
    }

    @Test
    void calculatePoints1() {
        try {
            gameField.place(new ResourceCard(4), true, -1,1);
            gameField.place(new GoldCard(44), true,1, -1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }

        assertEquals(2,gameField.getPlayer().getScore());
    }
    @Test
    void calculatePoints2() {
        try {
            gameField.place(new ResourceCard(4), true, -1,1);
            gameField.place(new ResourceCard(5), false, 1,-1);
            gameField.place(new ResourceCard(6), false, 2,0);
            gameField.place(new GoldCard(44), true,1, 1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(4,gameField.getPlayer().getScore());
    }
}