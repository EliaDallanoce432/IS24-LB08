package it.polimi.ingsw.client.view.GUI.viewControllers.utility;

import it.polimi.ingsw.client.model.ScoreBoardModel;
import it.polimi.ingsw.util.supportclasses.Token;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.util.HashMap;

public class ScoreTrackRepresentation {

    private Pane scoreTrackPane;
    private ImageView blueToken;
    private ImageView redToken;
    private ImageView greenToken;
    private ImageView yellowToken;
    private HashMap<Token,ImageView> tokenMap;

    public ScoreTrackRepresentation(Pane scoreTrackPane, ImageView blueToken, ImageView redToken, ImageView greenToken, ImageView yellowToken){
        this.scoreTrackPane = scoreTrackPane;
        this.blueToken = blueToken;
        this.redToken = redToken;
        this.greenToken = greenToken;
        this.yellowToken = yellowToken;
        tokenMap = new HashMap<Token,ImageView>();
        tokenMap.put(Token.blue, blueToken);
        tokenMap.put(Token.red, redToken);
        tokenMap.put(Token.green, greenToken);
        tokenMap.put(Token.yellow, yellowToken);
        blueToken.setVisible(false);
        redToken.setVisible(false);
        greenToken.setVisible(false);
        yellowToken.setVisible(false);
    }

    public void updateTokenPosition(){

        ScoreBoardModel scoreBoardModel = ScoreBoardModel.getInstance();

        for (String username : scoreBoardModel.getScore().keySet()){

            Token token = scoreBoardModel.getToken(username);
            ImageView tokenView = tokenMap.get(token);
            tokenView.setVisible(true);
            setTokenPosition(tokenView, scoreBoardModel.getScore().get(username));
        }
    }

    private void setTokenPosition(ImageView tokenView, int score){

        tokenView.setLayoutX(score*10);
        tokenView.setLayoutY(score*10);

        //TODO implementare
    }
}
