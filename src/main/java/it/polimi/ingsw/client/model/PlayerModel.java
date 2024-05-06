package it.polimi.ingsw.client.model;

import it.polimi.ingsw.client.view.VirtualCard;
import it.polimi.ingsw.util.supportclasses.Color;

import java.util.ArrayList;

import static it.polimi.ingsw.util.supportclasses.ViewConstants.HAND_SIZE;

public class PlayerModel extends ObservableModel {

    private static PlayerModel istance;

    public static PlayerModel getIstance(){

        if (istance==null) istance = new PlayerModel();
        return istance;

    }


    private String username;
    private Color color;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }




}
