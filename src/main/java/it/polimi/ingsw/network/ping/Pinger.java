package it.polimi.ingsw.network.ping;

import it.polimi.ingsw.network.NetworkInterface;
import org.json.simple.JSONObject;

public class Pinger implements Runnable, PongObserver {
    private NetworkInterface networkInterface;
    private boolean running;
    private JSONObject pingMessage;

    private final int pingInterval = 2000;
    private final int pingTries = 3;
    int remainingPings;

    public Pinger(NetworkInterface networkInterface) {
        this.networkInterface = networkInterface;
        pingMessage = new JSONObject();
        pingMessage.put("type", "ping");
        running = true;
    }

    @Override
    public void run() {
        remainingPings = pingTries;
        while (running) {
            networkInterface.send(pingMessage);
            System.out.println("pinging");
            try {
                Thread.sleep(pingInterval);
            } catch (InterruptedException ignored) {
            }
            remainingPings -= 1;
            System.out.println("remaining pings: " + remainingPings);
            if (remainingPings == 0) {
                networkInterface.connectionLossNotification();
                running = false;
            }
        }
    }

    public synchronized void notifyPong() {
        remainingPings = pingTries;
        System.out.println("pong received");
    }

}
