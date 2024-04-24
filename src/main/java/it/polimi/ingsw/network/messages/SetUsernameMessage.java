package it.polimi.ingsw.network.messages;

public class SetUsernameMessage {


    String command;
    String username;

    public SetUsernameMessage(String username) {
        this.command="setUsername";
        this.username = username;
    }
}
