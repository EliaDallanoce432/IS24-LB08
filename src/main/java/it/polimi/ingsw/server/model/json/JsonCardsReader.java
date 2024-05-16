package it.polimi.ingsw.server.model.json;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.card.GoldCardStrategy.*;
import it.polimi.ingsw.util.customexceptions.CannotOpenJSONException;
import it.polimi.ingsw.util.customexceptions.InvalidIdException;
import it.polimi.ingsw.util.supportclasses.Resource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * This class handles reading card data from JSON files. It provides methods to extract specific information
 * about card properties based on the JSON structure
 */
public class JsonCardsReader {

    /**
     * extracts the resource type from a JSONObject representing a corner of a card in the JSON data
     * @param item JSONObject representing the card data.
     * @param corn String representing the corner
     * @return `Resource` enum value extracted from the corner data
     */
    private static Resource getCornerResource(JSONObject item, String corn) {
        JSONObject corner = (JSONObject) item.get(corn);
        String resource = corner.get("Resource").toString();
        return Resource.StringToResource(resource);
    }

    /**
     * extracts the attachable flag from a JSONObject representing a corner of a card in the JSON data
     * @param item JSONObject representing the card data
     * @param corn String representing the corner
     * @return True if the "Exist" key in the corner data is true, false otherwise
     */
    private static boolean getCornerAttachable(JSONObject item, String corn) {
        JSONObject corner = (JSONObject) item.get(corn);
        return (Boolean) corner.get("Exist");
    }

    /**
     * loads resource card from json file
     * @param id unique id that identifies the card
     * @param resourceCard  reference to the card itself
     * @throws CannotOpenJSONException If the JSON file cannot be opened or parsed.
     * @throws InvalidIdException If the provided ID is invalid (outside the range of 1-40).
     */
    public static void loadResourceCard(int id, ResourceCard resourceCard) throws CannotOpenJSONException, InvalidIdException {
        if (id < 1 || id > 40) {
            throw new InvalidIdException("invalid id: "+ id);
        }
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("Json/JsonResourceCards.json");
        try (InputStreamReader isr = new InputStreamReader(is)) {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(isr);
            JSONArray dataArray = (JSONArray) jsonObject.get("data");
            for (Object obj : dataArray) {
                JSONObject item = (JSONObject) obj;
                if (id == ((Long) item.get("Id")).intValue()) {
                    loadGenericPlaceableCardInformation(resourceCard, item, id);
                    break;
                }
            }
        } catch (IOException e) {
            throw new CannotOpenJSONException("couldn't load JsonResourceCards file");
        } catch (ParseException e) {
            throw new CannotOpenJSONException("couldn't load resource card " + id);
        }
    }

    /**
     * loads gold card from json file
     * @param id unique id that identifies the card
     * @param goldCard  reference to the card itself
     * @throws CannotOpenJSONException If the JSON file cannot be opened or parsed.
     * @throws InvalidIdException If the provided ID is invalid (outside the range of 41-80).
     */
    public static void loadGoldCard(int id, GoldCard goldCard) throws CannotOpenJSONException, InvalidIdException {
        if (id < 41 || id > 80) {
            throw new InvalidIdException("invalid id: "+ id);
        }
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("Json/JsonGoldCards.json");
        try (InputStreamReader isr = new InputStreamReader(is)) {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(isr);
            JSONArray dataArray = (JSONArray) jsonObject.get("data");
            for (Object obj : dataArray) {
                JSONObject item = (JSONObject) obj;
                JSONArray requirements = (JSONArray) item.get("Requirements");
                if (id == ((Long) item.get("Id")).intValue()) {
                    loadGenericPlaceableCardInformation(goldCard, item, id);
                    loadGoldCardStrategy(goldCard, item);
                    loadGoldCardRequirements(goldCard, requirements);
                    break;
                }
            }

        } catch (IOException e) {
            throw new CannotOpenJSONException("couldn't load JsonGoldCards file");
        } catch (ParseException e) {
            throw new CannotOpenJSONException("couldn't load gold card " + id);
        }
    }

    /**
     * loads generic placeable card information
     * @param placeableCard placeable card to load
     * @param item JSONObject representing the card data
     * @param id unique identifier of the card
     */
    private static void loadGenericPlaceableCardInformation(PlaceableCard placeableCard, JSONObject item, int id) {
        placeableCard.setId(id);
        placeableCard.setPoints(((Long) item.get("Points")).intValue());
        placeableCard.setCardKingdom(Resource.StringToResource(item.get("Kingdom").toString()));
        placeableCard.setFrontTopLeftCorner(new Corner(getCornerResource(item, "TopLeftCorner"), getCornerAttachable(item, "TopLeftCorner"), placeableCard));
        placeableCard.setFrontTopRightCorner(new Corner(getCornerResource(item, "TopRightCorner"), getCornerAttachable(item, "TopRightCorner"), placeableCard));
        placeableCard.setFrontBottomLeftCorner(new Corner(getCornerResource(item, "BottomLeftCorner"), getCornerAttachable(item, "BottomLeftCorner"), placeableCard));
        placeableCard.setFrontBottomRightCorner(new Corner(getCornerResource(item, "BottomRightCorner"), getCornerAttachable(item, "BottomRightCorner"), placeableCard));
        placeableCard.setBackTopLeftCorner(new Corner(Resource.none, true,placeableCard));
        placeableCard.setBackBottomLeftCorner(new Corner(Resource.none, true,placeableCard));
        placeableCard.setBackTopRightCorner(new Corner(Resource.none, true,placeableCard));
        placeableCard.setBackBottomRightCorner(new Corner(Resource.none, true,placeableCard));
        placeableCard.setFacingUp(true);
    }

    /**
     * loads the strategy information for a gold card from a JSONObject
     * @param goldCard gold card to load
     * @param item JSONObject representing the card data
     */
    private static void loadGoldCardStrategy(GoldCard goldCard, JSONObject item)  {
        String strategy = item.get("Strategy").toString();
        switch (strategy) {
            case "coveredcorner" -> goldCard.setContext(new GoldCardContext(new GoldCardCoveredCornerStrategy()));
            case "noaction" -> goldCard.setContext(new GoldCardContext(new GoldCardNoActionStrategy()));
            case "feather" -> goldCard.setContext(new GoldCardContext(new GoldCardFeatherStrategy()));
            case "scroll" -> goldCard.setContext(new GoldCardContext(new GoldCardScrollStrategy()));
            case "inkpot" -> goldCard.setContext(new GoldCardContext(new GoldCardInkPotStrategy()));
        }
    }

    /**
     * loads the resource requirements for a gold card from a JSONArray in the JSON data
     * @param goldCard gold card to load
     * @param requirements JSONArray containing the resource requirements data
     */
    private static void loadGoldCardRequirements(GoldCard goldCard, JSONArray requirements) {
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
    }


    /**
     * loads starter card information from a JSON file based on the provided card ID
     * @param id unique id that identifies the card
     * @param starterCard  reference to the card itself
     * @throws CannotOpenJSONException If the JSON file cannot be opened or parsed.
     * @throws InvalidIdException If the provided ID is invalid (outside the range of 81-86).
     */
    public static void loadStarterCard(int id, StarterCard starterCard) throws CannotOpenJSONException, InvalidIdException {
        if (id < 81 || id > 86) {
            throw new InvalidIdException("invalid id: "+ id);
        }
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("Json/JsonStarterCards.json");
        try (InputStreamReader isr = new InputStreamReader(is)) {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(isr);
            JSONArray dataArray = (JSONArray) jsonObject.get("data");
            for (Object obj : dataArray) {
                JSONObject item = (JSONObject) obj;
                if (id == ((Long) item.get("Id")).intValue()) {
                    loadStarterCardResourcesAndCorners(starterCard, item, id);
                    break;
                }
            }
        } catch (IOException e) {
            throw new CannotOpenJSONException("couldn't load JsonStarterCards file");
        } catch (ParseException e) {
            throw new CannotOpenJSONException("couldn't load starter card " + id);
        }
    }

    /**
     * loads resource and corner information for a starter card from a JSONObject.
     * @param starterCard starter card
     * @param item JSONObject representing the card data
     * @param id unique identifier of the card
     */
    private static void loadStarterCardResourcesAndCorners(StarterCard starterCard, JSONObject item,int id){
        List<Resource> backResources = new ArrayList<>();
        JSONArray resource = (JSONArray) item.get("ResourceBack");
        for(int i=0; i<3; i++) {
            backResources.add(Resource.StringToResource(resource.get(i).toString()));
        }
        starterCard.setId(id);
        starterCard.setFacingUp(true);
        starterCard.setBackCentralResources(new ArrayList<>(backResources));
        starterCard.setFrontTopLeftCorner(new Corner(getCornerResource(item, "FrontTopLeftCorner"), getCornerAttachable(item, "FrontTopLeftCorner"), starterCard));
        starterCard.setFrontTopRightCorner(new Corner(getCornerResource(item, "FrontTopRightCorner"), getCornerAttachable(item, "FrontTopRightCorner"), starterCard));
        starterCard.setFrontBottomLeftCorner(new Corner(getCornerResource(item, "FrontBottomLeftCorner"), getCornerAttachable(item, "FrontBottomLeftCorner"), starterCard));
        starterCard.setFrontBottomRightCorner(new Corner(getCornerResource(item, "FrontBottomRightCorner"), getCornerAttachable(item, "FrontBottomRightCorner"), starterCard));
        starterCard.setBackTopLeftCorner(new Corner(getCornerResource(item, "BackTopLeftCorner"), getCornerAttachable(item, "BackTopLeftCorner"), starterCard));
        starterCard.setBackTopRightCorner(new Corner(getCornerResource(item, "BackTopRightCorner"), getCornerAttachable(item, "BackTopRightCorner"), starterCard));
        starterCard.setBackBottomLeftCorner(new Corner(getCornerResource(item, "BackBottomLeftCorner"), getCornerAttachable(item, "BackBottomLeftCorner"), starterCard));
        starterCard.setBackBottomRightCorner(new Corner(getCornerResource(item, "BackBottomRightCorner"), getCornerAttachable(item, "BackBottomRightCorner"), starterCard));
    }

}


