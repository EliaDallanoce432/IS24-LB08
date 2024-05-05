package it.polimi.ingsw.network.ping;

import it.polimi.ingsw.network.NetworkInterface;
import org.json.simple.JSONObject;

public class Pinger implements Runnable, PongObserver {
    private NetworkInterface networkInterface;
    private volatile boolean running;
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
            try {
                Thread.sleep(pingInterval);
            } catch (InterruptedException ignored) {
            }
            remainingPings -= 1;
            if (remainingPings == 0) {
                networkInterface.connectionLossNotification();
                running = false;
            }
        }
    }

    public synchronized void notifyPong() {
        remainingPings = pingTries;
    }

    public void shutdown() {
        running = false;
    }
}
