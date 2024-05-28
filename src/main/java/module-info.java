@SuppressWarnings("all")
module LB08 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires json.simple;
    requires junit;
    requires org.controlsfx.controls;
    requires java.desktop;

    exports it.polimi.ingsw;
    exports it.polimi.ingsw.server.lobby;
    exports it.polimi.ingsw.server.model;
    exports it.polimi.ingsw.server.model.json;
    exports it.polimi.ingsw.server.model.card;
    exports it.polimi.ingsw.server.model.card.GoldCardStrategy;
    exports it.polimi.ingsw.server.model.card.ObjectiveCardStrategy;
    exports it.polimi.ingsw.server.model.deck;
    exports it.polimi.ingsw.server.controller;
    exports it.polimi.ingsw.server.view;
    exports it.polimi.ingsw.client.model;
    exports it.polimi.ingsw.client.controller;
    exports it.polimi.ingsw.client.view;
    exports it.polimi.ingsw.client.view.utility;
    exports it.polimi.ingsw.client.view.observers;
    exports it.polimi.ingsw.client.view.CLI;
    exports it.polimi.ingsw.client.view.GUI;
    exports it.polimi.ingsw.client.view.GUI.viewControllers;
    exports it.polimi.ingsw.network;
    exports it.polimi.ingsw.network.input;
    exports it.polimi.ingsw.network.ping;
    exports it.polimi.ingsw.util.cli;
    exports it.polimi.ingsw.util.customexceptions;
    exports it.polimi.ingsw.util.supportclasses;
}