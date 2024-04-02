package it.polimi.ingsw.server.model.json;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.server.model.GoldCardStrategy.*;
import it.polimi.ingsw.server.model.card.Corner;
import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.util.supportclasses.Resource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonCardsReader {

    private static Resource getCornerResource(JSONObject item, String corn) {
        JSONObject corner = (JSONObject) item.get(corn);
        String resource = corner.get("Resource").toString();
        return Resource.StringToResource(resource);
    }

    private static boolean getCornerAttachable(JSONObject item, String corn) {
        JSONObject corner = (JSONObject) item.get(corn);
        return (Boolean) corner.get("Exist");
    }

    /**
     * method access the information of a specific card from the json file and sets the attributes in the card according to what is read on the file
     * @param id unique id that identifies the card
     * @param resourceCard  reference to the card itself
     */
    public static void loadResourceCard(int id, ResourceCard resourceCard) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("it/polimi/ingsw/server/json/JsonResourceCards.json")) {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            JSONArray dataArray = (JSONArray) jsonObject.get("data");

            for (Object obj : dataArray) {
                JSONObject item = (JSONObject) obj;
                if (id == ((Long) item.get("Id")).intValue()) {
                    resourceCard.setId(id);
                    resourceCard.setPoints(((Long) item.get("Points")).intValue());
                    resourceCard.setCardKingdom(Resource.StringToResource(item.get("Kingdom").toString()));
                    resourceCard.setFrontTopLeftCorner(new Corner(getCornerResource(item, "TopLeftCorner"), getCornerAttachable(item, "TopLeftCorner"), resourceCard));
                    resourceCard.setFrontTopRightCorner(new Corner(getCornerResource(item, "TopRightCorner"), getCornerAttachable(item, "TopRightCorner"), resourceCard));
                    resourceCard.setFrontBottomLeftCorner(new Corner(getCornerResource(item, "BottomLeftCorner"), getCornerAttachable(item, "BottomLeftCorner"), resourceCard));
                    resourceCard.setFrontBottomRightCorner(new Corner(getCornerResource(item, "BottomRightCorner"), getCornerAttachable(item, "BottomRightCorner"), resourceCard));
                    break;
                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * method access the information of a specific card from the json file and sets the attributes in the card according to what is read on the file
     * @param id unique id that identifies the card
     * @param goldCard  reference to the card itself
     */
    public static void loadGoldCard(int id, GoldCard goldCard) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("it/polimi/ingsw/server/json/JsonGoldCards.json")) {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONArray dataArray = (JSONArray) jsonObject.get("data");
            for (Object obj : dataArray) {
                JSONObject item = (JSONObject) obj;
                JSONArray requirements = (JSONArray) item.get("Requirements");
                if (id == ((Long) item.get("Id")).intValue()) {
                    goldCard.setId(id);
                    goldCard.setPoints(((Long) item.get("Points")).intValue());
                    goldCard.setCardKingdom(Resource.StringToResource(item.get("Kingdom").toString()));
                    goldCard.setFrontTopLeftCorner(new Corner(getCornerResource(item, "TopLeftCorner"), getCornerAttachable(item, "TopLeftCorner"), goldCard));
                    goldCard.setFrontTopRightCorner(new Corner(getCornerResource(item, "TopRightCorner"), getCornerAttachable(item, "TopRightCorner"), goldCard));
                    goldCard.setFrontBottomLeftCorner(new Corner(getCornerResource(item, "BottomLeftCorner"), getCornerAttachable(item, "BottomLeftCorner"), goldCard));
                    goldCard.setFrontBottomRightCorner(new Corner(getCornerResource(item, "BottomRightCorner"), getCornerAttachable(item, "BottomRightCorner"), goldCard));
                    String strategy = item.get("Strategy").toString();
                    switch (strategy) {
                        case "coveredcorner" -> goldCard.setContext(new GoldCardContext(new GoldCardCoveredCornerStrategy()));
                        case "noaction" -> goldCard.setContext(new GoldCardContext(new GoldCardNoActionStrategy()));
                        case "feather" -> goldCard.setContext(new GoldCardContext(new GoldCardFeatherStrategy()));
                        case "scroll" -> goldCard.setContext(new GoldCardContext(new GoldCardScrollStrategy()));
                        case "inkpot" -> goldCard.setContext(new GoldCardContext(new GoldCardInkPotStrategy()));
                    }
                    for (int i = 0; i < 4; i++) {
                        JSONObject currentRequirement = (JSONObject) requirements.get(i);
                        String resource = currentRequirement.get("Resource").toString();
                        switch (Resource.StringToResource(resource)) {
                            case fungi -> goldCard.setRequiredFungiResourceAmount(((Long) currentRequirement.get("Num")).intValue());
                            case animal -> goldCard.setRequiredAnimalResourceAmount(((Long) currentRequirement.get("Num")).intValue());
                            case plant -> goldCard.setRequiredPlantResourceAmount(((Long) currentRequirement.get("Num")).intValue());
                            case insect -> goldCard.setRequiredInsectResourceAmount(((Long) currentRequirement.get("Num")).intValue());
                            case none -> {}
                        }
                    }
                    break;
                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * method access the information of a specific card from the json file and sets the attributes in the card according to what is read on the file
     * @param id unique id that identifies the card
     * @param starterCard  reference to the card itself
     */
    public static void loadStarterCard(int id, StarterCard starterCard) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("it/polimi/ingsw/server/json/JsonStarterCards.json")) {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            JSONArray dataArray = (JSONArray) jsonObject.get("data");

            for (Object obj : dataArray) {
                JSONObject item = (JSONObject) obj;
                if (id == ((Long) item.get("Id")).intValue()) {
                    starterCard.setId(id);
                    List<Resource> backResources = new ArrayList<>();
                    JSONArray resource = (JSONArray) item.get("ResourceBack");
                    for(int i=0; i<3; i++) {
                        backResources.add(Resource.StringToResource(resource.get(i).toString()));
                    }
                    starterCard.setBackCentralResources(new ArrayList<>(backResources));
                    starterCard.setFrontTopLeftCorner(new Corner(getCornerResource(item, "FrontTopLeftCorner"), getCornerAttachable(item, "FrontTopLeftCorner"), starterCard));
                    starterCard.setFrontTopRightCorner(new Corner(getCornerResource(item, "FrontTopRightCorner"), getCornerAttachable(item, "FrontTopRightCorner"), starterCard));
                    starterCard.setFrontBottomLeftCorner(new Corner(getCornerResource(item, "FrontBottomLeftCorner"), getCornerAttachable(item, "FrontBottomLeftCorner"), starterCard));
                    starterCard.setFrontBottomRightCorner(new Corner(getCornerResource(item, "FrontBottomRightCorner"), getCornerAttachable(item, "FrontBottomRightCorner"), starterCard));
                    starterCard.setBackTopLeftCorner(new Corner(getCornerResource(item, "BackTopLeftCorner"), getCornerAttachable(item, "BackTopLeftCorner"), starterCard));
                    starterCard.setBackTopRightCorner(new Corner(getCornerResource(item, "BackTopRightCorner"), getCornerAttachable(item, "BackTopRightCorner"), starterCard));
                    starterCard.setBackBottomLeftCorner(new Corner(getCornerResource(item, "BackBottomLeftCorner"), getCornerAttachable(item, "BackBottomLeftCorner"), starterCard));
                    starterCard.setBackBottomRightCorner(new Corner(getCornerResource(item, "BackBottomRightCorner"), getCornerAttachable(item, "BackBottomRightCorner"), starterCard));
                    break;
                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

}


