package it.polimi.ingsw.client.view.utility;

import it.polimi.ingsw.client.model.ScoreBoardModel;
import it.polimi.ingsw.util.supportclasses.ScoreTrackCoordinates;
import it.polimi.ingsw.util.supportclasses.Token;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.HashMap;
import static it.polimi.ingsw.util.supportclasses.ViewConstants.TOKEN_OFFSET;

/**
 * This class manages the visual representation of the tokens on the score track.
 */
public class ScoreTrackRepresentation {
    private final Pane scoreTrackPane;
    private final HashMap<Token,ImageView> tokenMap;
    private final HashMap<Integer, ArrayList<Token>> placedTokens;

    /**
     * Initializes the score track.
     * @param scoreTrackPane The pane where the score track will be shown.
     * @param blueToken The image of the blue token.
     * @param redToken The image of the red token.
     * @param greenToken The image of the green token.
     * @param yellowToken The image of the yellow token.
     */
    public ScoreTrackRepresentation(Pane scoreTrackPane, ImageView blueToken, ImageView redToken, ImageView greenToken, ImageView yellowToken){
        this.scoreTrackPane = scoreTrackPane;
        tokenMap = new HashMap<>();
        placedTokens = new HashMap<>();
        tokenMap.put(Token.blue, blueToken);
        tokenMap.put(Token.red, redToken);
        tokenMap.put(Token.green, greenToken);
        tokenMap.put(Token.yellow, yellowToken);
        blueToken.setVisible(false);
        redToken.setVisible(false);
        greenToken.setVisible(false);
        yellowToken.setVisible(false);
    }

    /**
     * Updates the position of the tokens on the score track.
     */
    public void updateTokenPosition(){
        ScoreBoardModel scoreBoardModel = ScoreBoardModel.getInstance();

        for (String username : scoreBoardModel.getScore().keySet()){
            Token token = scoreBoardModel.getToken(username);
            setTokenPosition(token, scoreBoardModel.getScore().get(username));
        }

        drawTokens();
    }

    private void setTokenPosition(Token token, int score){
        for(ArrayList<Token> placedTokens : placedTokens.values()){
            placedTokens.removeIf(placedToken -> placedToken.equals(token));
        }

        if(!placedTokens.containsKey(score)){
            placedTokens.put(score, new ArrayList<>());
        }

        placedTokens.get(score).addLast(token);
    }

    private void drawTokens(){
        double offset;
        double x;
        double y;

        for(int score : placedTokens.keySet()){
            ArrayList<Token> tokens = placedTokens.get(score);
            offset = 0;
            for(Token token : tokens){

                ImageView tokenView = tokenMap.get(token);
                x = ScoreTrackCoordinates.getXCoordinate(score);
                y = ScoreTrackCoordinates.getYCoordinate(score);

                tokenView.setLayoutX((x * scoreTrackPane.getWidth()) - (tokenView.getFitWidth()/2));
                tokenView.setLayoutY((y * scoreTrackPane.getHeight()) - (tokenView.getFitHeight()/2) - offset);

                offset += TOKEN_OFFSET;

                tokenView.setVisible(true);
                tokenView.toFront();
            }
        }
    }
}
