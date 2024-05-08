package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.util.customexceptions.CardNotInHandException;
import it.polimi.ingsw.util.customexceptions.FullHandException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private static GameController controller;
    private static Game game;
    private GameField gameField;
    private Player referencePlayer;

    @BeforeAll
    static void setUpBeforeClass() {
        controller = new GameController(null,4,"test");
        game = controller.getGame();
    }

    @BeforeEach
    void setUp() {
        referencePlayer = new Player();
        referencePlayer.getHand().clear();
        gameField = new GameField(referencePlayer);

    }
    @AfterEach
    void tearDown() {
        game.reinsertToken(referencePlayer.getToken());
        gameField = null;
    }

    @AfterAll
    static void tearDownAfterClass() {
        controller = null;
    }

    @Test
    void addToHand_RegularAddCard() throws FullHandException, CardNotInHandException {
        referencePlayer.addToHand(new ResourceCard(1));
        referencePlayer.addToHand(new ResourceCard(2));

        ResourceCard referenceCard = new ResourceCard(3);
        ResourceCard testCard = new ResourceCard(3);
        referencePlayer.addToHand(testCard);

        assertEquals(referenceCard, referencePlayer.removeFromHand(testCard));

    }

    @Test
    void addToHand_CheckException() throws FullHandException {

        referencePlayer.addToHand(new ResourceCard(1));
        referencePlayer.addToHand(new ResourceCard(2));
        referencePlayer.addToHand(new ResourceCard(3));

        assertThrows(FullHandException.class, ()-> referencePlayer.addToHand(new ResourceCard(4)));

    }

    @Test
    void removeFromHand_CardInHandCase() throws CardNotInHandException, FullHandException {

        referencePlayer.addToHand(new ResourceCard(1));
        referencePlayer.addToHand(new ResourceCard(2));
        referencePlayer.addToHand(new ResourceCard(3));

        ResourceCard referenceCard = new ResourceCard(2);
        ResourceCard testCard = new ResourceCard(2);

        assertEquals(referenceCard, referencePlayer.removeFromHand(testCard));


    }

    @Test
    void removeFromHand_CheckException() throws FullHandException {

        referencePlayer.addToHand(new ResourceCard(1));
        referencePlayer.addToHand(new ResourceCard(2));
        referencePlayer.addToHand(new ResourceCard(3));
        ResourceCard testCard = new ResourceCard(4);

        assertThrows(CardNotInHandException.class, ()-> referencePlayer.removeFromHand(testCard));


    }




}