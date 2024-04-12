package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.json.JsonCardsReader;
import it.polimi.ingsw.util.customexceptions.CannotOpenJSONException;
import it.polimi.ingsw.util.customexceptions.CardNotInHandException;
import it.polimi.ingsw.util.customexceptions.FullHandException;
import it.polimi.ingsw.util.supportclasses.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void addToHand_RegularAddCard() throws FullHandException, CardNotInHandException {

        Player testPlayer = new Player("reference", Color.yellow);

        testPlayer.addToHand(new ResourceCard(1));
        testPlayer.addToHand(new ResourceCard(2));

        ResourceCard referenceCard = new ResourceCard(3);
        ResourceCard testCard = new ResourceCard(3);
        testPlayer.addToHand(testCard);

        assertEquals(referenceCard, testPlayer.removeFromHand(testCard));

    }

    @Test
    void addToHand_CheckException() throws FullHandException {

        Player referencePlayer = new Player("reference", Color.yellow);

        referencePlayer.addToHand(new ResourceCard(1));
        referencePlayer.addToHand(new ResourceCard(2));
        referencePlayer.addToHand(new ResourceCard(3));

        assertThrows(FullHandException.class, ()-> referencePlayer.addToHand(new ResourceCard(4)));

    }

    @Test
    void removeFromHand_CardInHandCase() throws CardNotInHandException, FullHandException {

        Player referencePlayer = new Player("reference", Color.yellow);

        referencePlayer.addToHand(new ResourceCard(1));
        referencePlayer.addToHand(new ResourceCard(2));
        referencePlayer.addToHand(new ResourceCard(3));

        ResourceCard referenceCard = new ResourceCard(2);
        ResourceCard testCard = new ResourceCard(2);

        assertEquals(referenceCard, referencePlayer.removeFromHand(testCard));


    }

    @Test
    void removeFromHand_CheckException() throws FullHandException {

        Player referencePlayer = new Player("reference", Color.yellow);

        referencePlayer.addToHand(new ResourceCard(1));
        referencePlayer.addToHand(new ResourceCard(2));
        referencePlayer.addToHand(new ResourceCard(3));
        ResourceCard testCard = new ResourceCard(4);

        assertThrows(CardNotInHandException.class, ()-> referencePlayer.removeFromHand(testCard));


    }




}