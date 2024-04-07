package it.polimi.ingsw.server.model.json;

import it.polimi.ingsw.server.model.card.Corner;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.util.customexceptions.CannotOpenJSONException;
import it.polimi.ingsw.util.supportclasses.Resource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonCardsReaderTest {

    @Test
    void loadResourceCard() {
        ResourceCard referenceResourceCard = new ResourceCard();
        referenceResourceCard.setId(1);
        referenceResourceCard.setCardKingdom(Resource.fungi);
        referenceResourceCard.setPoints(0);
        /*referenceResourceCard.setBackBottomLeftCorner(new Corner(Resource.none,true,referenceResourceCard));
        referenceResourceCard.setBackBottomRightCorner(new Corner(Resource.none,true,referenceResourceCard));
        referenceResourceCard.setBackTopRightCorner(new Corner(Resource.none,true,referenceResourceCard));
        referenceResourceCard.setBackTopLeftCorner(new Corner(Resource.none,true,referenceResourceCard));*/
        referenceResourceCard.setFrontBottomLeftCorner(new Corner(Resource.fungi,true,referenceResourceCard));
        referenceResourceCard.setFrontBottomRightCorner(new Corner(Resource.none,false,referenceResourceCard));
        referenceResourceCard.setFrontTopLeftCorner(new Corner(Resource.fungi,true,referenceResourceCard));
        referenceResourceCard.setFrontTopRightCorner(new Corner(Resource.none,true,referenceResourceCard));
        //referenceResourceCard.setFacingUp(true);
        ResourceCard testcard = new ResourceCard();
        try {
            JsonCardsReader.loadResourceCard(1,testcard);
        } catch (CannotOpenJSONException e) {
            throw new RuntimeException(e);
        }
        assertEquals(referenceResourceCard, testcard);
    }

    @Test
    void loadGoldCard() {
    }

    @Test
    void loadStarterCard() {
    }
}