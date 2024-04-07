package it.polimi.ingsw.server.model.json;

import it.polimi.ingsw.server.model.GoldCardStrategy.GoldCardContext;
import it.polimi.ingsw.server.model.GoldCardStrategy.GoldCardFeatherStrategy;
import it.polimi.ingsw.server.model.card.Corner;
import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.util.customexceptions.CannotOpenJSONException;
import it.polimi.ingsw.util.supportclasses.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JsonCardsReaderTest {

    @Test
    void loadResourceCard() {
        ResourceCard referenceResourceCard = new ResourceCard();
        referenceResourceCard.setId(1);
        referenceResourceCard.setCardKingdom(Resource.fungi);
        referenceResourceCard.setPoints(0);
        referenceResourceCard.setRequiredAnimalResourceAmount(0);
        referenceResourceCard.setRequiredFungiResourceAmount(0);
        referenceResourceCard.setRequiredInsectResourceAmount(0);
        referenceResourceCard.setRequiredPlantResourceAmount(0);
        referenceResourceCard.setBackBottomLeftCorner(new Corner(Resource.none,true,referenceResourceCard));
        referenceResourceCard.setBackBottomRightCorner(new Corner(Resource.none,true,referenceResourceCard));
        referenceResourceCard.setBackTopRightCorner(new Corner(Resource.none,true,referenceResourceCard));
        referenceResourceCard.setBackTopLeftCorner(new Corner(Resource.none,true,referenceResourceCard));
        referenceResourceCard.setFrontBottomLeftCorner(new Corner(Resource.fungi,true,referenceResourceCard));
        referenceResourceCard.setFrontBottomRightCorner(new Corner(Resource.none,false,referenceResourceCard));
        referenceResourceCard.setFrontTopLeftCorner(new Corner(Resource.fungi,true,referenceResourceCard));
        referenceResourceCard.setFrontTopRightCorner(new Corner(Resource.none,true,referenceResourceCard));
        referenceResourceCard.setFacingUp(true);
        ResourceCard testcard = new ResourceCard();
        try {
            JsonCardsReader.loadResourceCard(1,testcard);
        } catch (CannotOpenJSONException e) {
            throw new RuntimeException(e);
        }
        assertTrue(referenceResourceCard.equals(testcard));
    }

    @Test
    void loadGoldCard() {
        GoldCard referenceGoldCard = new GoldCard();
        referenceGoldCard.setId(41);
        referenceGoldCard.setCardKingdom(Resource.fungi);
        referenceGoldCard.setPoints(1);
        referenceGoldCard.setRequiredAnimalResourceAmount(1);
        referenceGoldCard.setRequiredFungiResourceAmount(2);
        referenceGoldCard.setRequiredInsectResourceAmount(0);
        referenceGoldCard.setRequiredPlantResourceAmount(0);
        referenceGoldCard.setBackBottomLeftCorner(new Corner(Resource.none,true,referenceGoldCard));
        referenceGoldCard.setBackBottomRightCorner(new Corner(Resource.none,true,referenceGoldCard));
        referenceGoldCard.setBackTopRightCorner(new Corner(Resource.none,true,referenceGoldCard));
        referenceGoldCard.setBackTopLeftCorner(new Corner(Resource.none,true,referenceGoldCard));
        referenceGoldCard.setFrontBottomLeftCorner(new Corner(Resource.none,true,referenceGoldCard));
        referenceGoldCard.setFrontBottomRightCorner(new Corner(Resource.feather,true,referenceGoldCard));
        referenceGoldCard.setFrontTopLeftCorner(new Corner(Resource.none,false,referenceGoldCard));
        referenceGoldCard.setFrontTopRightCorner(new Corner(Resource.none,true,referenceGoldCard));
        referenceGoldCard.setFacingUp(true);
        referenceGoldCard.setContext(new GoldCardContext(new GoldCardFeatherStrategy()));
        GoldCard testCard = new GoldCard();
        try {
            JsonCardsReader.loadGoldCard(41,testCard);
        } catch (CannotOpenJSONException e) {
            throw new RuntimeException(e);
        }
        assertTrue(referenceGoldCard.equals(testCard));
    }

    @Test
    void loadStarterCard() {
        StarterCard referenceStarterCard = new StarterCard();
        referenceStarterCard.setId(81);
        ArrayList<Resource> resources = new ArrayList<>();
        resources.add(Resource.insect);
        resources.add(Resource.none);
        resources.add(Resource.none);
        referenceStarterCard.setBackCentralResources(resources);
        referenceStarterCard.setBackBottomLeftCorner(new Corner(Resource.insect,true,referenceStarterCard));
        referenceStarterCard.setBackBottomRightCorner(new Corner(Resource.none,true,referenceStarterCard));
        referenceStarterCard.setBackTopRightCorner(new Corner(Resource.plant,true,referenceStarterCard));
        referenceStarterCard.setBackTopLeftCorner(new Corner(Resource.none,true,referenceStarterCard));
        referenceStarterCard.setFrontBottomLeftCorner(new Corner(Resource.insect,true,referenceStarterCard));
        referenceStarterCard.setFrontBottomRightCorner(new Corner(Resource.animal,true,referenceStarterCard));
        referenceStarterCard.setFrontTopLeftCorner(new Corner(Resource.fungi,true,referenceStarterCard));
        referenceStarterCard.setFrontTopRightCorner(new Corner(Resource.plant,true,referenceStarterCard));
        referenceStarterCard.setFacingUp(true);
        StarterCard testCard = new StarterCard();
        try {
            JsonCardsReader.loadStarterCard(81,testCard);
        } catch (CannotOpenJSONException e) {
            throw new RuntimeException(e);
        }
        assertTrue(referenceStarterCard.equals(testCard));

        
    }
}