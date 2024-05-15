package it.polimi.ingsw.network.ping;

import it.polimi.ingsw.network.NetworkInterface;
import org.json.simple.JSONObject;
import java.util.HashMap;
import java.util.Map;
import static it.polimi.ingsw.util.supportclasses.PingerConstants.*;

/**
 * This class has the role to ping the other host to ensure the connection is still alive
 */
public class Pinger implements Runnable, PongObserver {
    private final NetworkInterface networkInterface;
    private volatile boolean running;
    private final JSONObject pingMessage;

    private final int pingTries = PING_TRIES;
    int remainingPings;

    public Pinger(NetworkInterface networkInterface) {
        this.networkInterface = networkInterface;
        Map<String,String> jsonMap = new HashMap<>();
        jsonMap.put("type", "ping");
        pingMessage = new JSONObject(jsonMap);
        running = true;
    }

    @Override
    public void run() {
        remainingPings = pingTries;
        while (running) {
            networkInterface.send(pingMessage);
            try {
                Thread.sleep(PING_INTERVAL);
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

    /**
     * stops the pinger execution
     */
    public void shutdown() {
        running = false;
    }
}
