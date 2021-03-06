package hearthstone.client.network;

import hearthstone.client.data.ClientData;
import hearthstone.models.Packet;

import java.io.PrintStream;

public class Sender extends Thread{
    private PrintStream printStream;

    public Sender(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void sendPacket(Packet packet) {
        try {
            String objectString;

            objectString = ClientData.getNetworkMapper().writeValueAsString(packet);

            printStream.println(objectString);
            printStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
