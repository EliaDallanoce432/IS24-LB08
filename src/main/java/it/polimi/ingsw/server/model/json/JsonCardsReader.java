package it.polimi.ingsw.server.model.json;

import java.io.FileReader;
import java.io.IOException;

import it.polimi.ingsw.server.model.card.Corner;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.util.supportclasses.Resource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonCardsReader {

    public static Resource getCornerResource(JSONObject item, String corn)
    {
        JSONObject corner = (JSONObject) item.get(corn);
        String resource = corner.get("Resource").toString();
        return Resource.StringToResource(resource);
    }
    public static boolean getCornerAttachable(JSONObject item, String corn)
    {
        JSONObject corner = (JSONObject) item.get(corn);
        return (Boolean)corner.get("Exist");
    }
    public static void readerResourceCard(int id, ResourceCard resc)
    {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("it/polimi/ingsw/server/json/JsonResourceCards.json")) {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            JSONArray dataArray = (JSONArray) jsonObject.get("data");

            for (Object obj : dataArray) {
                JSONObject item = (JSONObject) obj;
                if(id==((Long)item.get("Id")).intValue())
                {
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

    public static boolean getRequirement(JSONObject item, String req)
    {
        JSONObject requirement = (JSONObject) item.get(req);
        return (Boolean)requirement.get("Exist");
    }

    public static void readerGoldCard(int id, ResourceCard resc)
    {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("it/polimi/ingsw/server/json/JsonGoldCards.json")) {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONArray dataArray = (JSONArray) jsonObject.get("data");
            for (Object obj : dataArray) {
                JSONObject item = (JSONObject) obj;
                if(id==((Long)item.get("Id")).intValue())
                {
                    resc.setId(id);
                    resc.setPoints(((Long) item.get("Points")).intValue());
                    resc.setCardKingdom(Resource.StringToResource(item.get("Kingdom").toString()));
                    resc.setFrontTopLeftCorner(new Corner(getCornerResource(item, "TopLeftCorner"), getCornerAttachable(item, "TopLeftCorner"), resc));
                    resc.setFrontTopRightCorner(new Corner(getCornerResource(item, "TopRightCorner"), getCornerAttachable(item, "TopRightCorner"), resc));
                    resc.setFrontBottomLeftCorner(new Corner(getCornerResource(item, "BottomLeftCorner"), getCornerAttachable(item, "BottomLeftCorner"), resc));
                    resc.setFrontBottomRightCorner(new Corner(getCornerResource(item, "BottomRightCorner"), getCornerAttachable(item, "BottomRightCorner"), resc));
                    for (int i = 0; i < 4; i++) {

                    }
                    break;
                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }




    public static void readerGoldCards(int id)
    {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("it/polimi/ingsw/server/json/JsonGoldCards.json")) {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            JSONArray dataArray = (JSONArray) jsonObject.get("data");

            for (Object obj : dataArray) {
                JSONObject item = (JSONObject) obj;

                id = ((Long) item.get("Id")).intValue();
                String kingdomCard = (String) item.get("KingdomCard");
                int points = ((Long) item.get("Points")).intValue();

                JSONArray requirementsArray = (JSONArray) item.get("Requirements");

                for (Object reqObj : requirementsArray) {
                    JSONObject requirement = (JSONObject) reqObj;
                    String resource = (String) requirement.get("Resource");
                    int num = ((Long) requirement.get("Num")).intValue();

                    // Print or process requirement details as needed
                    System.out.println("- Resource: " + resource + ", Num: " + num);
                }

                // Access and process other fields (TopLeftCorner, TopRightCorner, etc.)
                // as shown in the previous example
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
