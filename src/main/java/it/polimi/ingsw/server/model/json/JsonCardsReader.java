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

    public static Resource getCornerResource(JSONObject item, String corn) {
        JSONObject corner = (JSONObject) item.get(corn);
        String resource = corner.get("Resource").toString();
        return Resource.StringToResource(resource);
    }

    public static boolean getCornerAttachable(JSONObject item, String corn) {
        JSONObject corner = (JSONObject) item.get(corn);
        return (Boolean) corner.get("Exist");
    }

    public static void readerResourceCard(int id, ResourceCard resc) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("it/polimi/ingsw/server/json/JsonResourceCards.json")) {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            JSONArray dataArray = (JSONArray) jsonObject.get("data");

            for (Object obj : dataArray) {
                JSONObject item = (JSONObject) obj;
                if (id == ((Long) item.get("Id")).intValue()) {
                    resc.setId(id);
                    resc.setPoints(((Long) item.get("Points")).intValue());
                    resc.setCardKingdom(Resource.StringToResource(item.get("Kingdom").toString()));
                    resc.setFrontTopLeftCorner(new Corner(getCornerResource(item, "TopLeftCorner"), getCornerAttachable(item, "TopLeftCorner"), resc));
                    resc.setFrontTopRightCorner(new Corner(getCornerResource(item, "TopRightCorner"), getCornerAttachable(item, "TopRightCorner"), resc));
                    resc.setFrontBottomLeftCorner(new Corner(getCornerResource(item, "BottomLeftCorner"), getCornerAttachable(item, "BottomLeftCorner"), resc));
                    resc.setFrontBottomRightCorner(new Corner(getCornerResource(item, "BottomRightCorner"), getCornerAttachable(item, "BottomRightCorner"), resc));
                    break;
                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }


    public static void readerGoldCard(int id, GoldCard goldCard) {
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

    public static void readerStarterCard(int id, StarterCard starterCard) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("it/polimi/ingsw/server/json/JsonStarterCards.json")) {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            JSONArray dataArray = (JSONArray) jsonObject.get("data");

            for (Object obj : dataArray) {
                JSONObject item = (JSONObject) obj;
                if (id == ((Long) item.get("Id")).intValue()) {
                    starterCard.setId(id);
                    List<Resource> backResources = new ArrayList<Resource>();
                    JSONArray resource = (JSONArray) item.get("ResourceBack");
                    for(int i=0; i<3; i++) {
                        backResources.add(Resource.StringToResource(resource.get(i).toString()));
                    }
                    starterCard.setBackCentralResources(new ArrayList<Resource>(backResources));
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


