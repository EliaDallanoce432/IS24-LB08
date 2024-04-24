package it.polimi.ingsw.network.sockets;

import java.net.InetAddress;

public interface SocketInfoInterface {
    /**
     * returns its own Inet address
     *
     * @return InetAddress
     */
    InetAddress getSocketAddress();

    /**
     * returns the port used by the socket
     * @return port number
     */
    int getSocketPort();
}
