package by.cniitu.virtualexhibition.service.mock;

import by.cniitu.virtualexhibition.service.SocketServerService;
import by.cniitu.virtualexhibition.to.websocket.SocketTo;
import by.cniitu.virtualexhibition.to.websocket.messageBody.coordinates.CoordinatesToClient;
import by.cniitu.virtualexhibition.util.JsonUtil;

import java.util.LinkedList;
import java.util.List;

public class SendCoordinate extends Thread {

    public static final List<CoordinatesToClient> users = new LinkedList<CoordinatesToClient>(){{
        add(new CoordinatesToClient(2, 0d, -3.11d, "#000000"));
        add(new CoordinatesToClient( 2, 0.91d, -2.88d, "#110000"));
        add(new CoordinatesToClient( 2, 1.75d, -2.525d, "#220000"));
        add(new CoordinatesToClient( 2, 2.543d, -2.147d, "#330000"));
        add(new CoordinatesToClient( 2, 2.777d, -1.447d, "#440000"));
        add(new CoordinatesToClient( 2, 2.67d, -0.6d, "#550000"));
        add(new CoordinatesToClient( 2, 2.531d, 0.141d, "#660000"));
        add(new CoordinatesToClient( 2, 2.274d, 0.929d, "#770000"));
        add(new CoordinatesToClient( 2, 1.776d, 1.446d, "#880000"));
        add(new CoordinatesToClient( 2, 0.878d, 1.684d, "#990000"));
        add(new CoordinatesToClient( 2, -0.082d, 1.854d, "#AA0000"));
        add(new CoordinatesToClient( 2, -0.981d, 1.8d, "#BB0000"));
        add(new CoordinatesToClient( 2, -1.783d, 1.593d, "#AA0000"));
        add(new CoordinatesToClient( 2, -2.225d, 1.244d, "#990000"));
        add(new CoordinatesToClient( 2, -2.451, 0.579d, "#880000"));
        add(new CoordinatesToClient( 2, -2.747d, -0.051d, "#770000"));
        add(new CoordinatesToClient( 2, -2.954, -0.849d, "#660000"));
        add(new CoordinatesToClient( 2, -2.806, -1.669d, "#550000"));
        add(new CoordinatesToClient( 2, -2.613, -2.384d, "#440000"));
        add(new CoordinatesToClient( 2, -2.23, -2.937d, "#330000"));
        add(new CoordinatesToClient( 2, -1.598, -3.305d, "#220000"));
        add(new CoordinatesToClient( 2, -0.781, -3.195d, "#110000"));
    }};

    SocketServerService socketServer;

    public SendCoordinate(SocketServerService socketServer) {
        this.socketServer = socketServer;
    }

    @Override
    public void run() {
        while (true){
            List<CoordinatesToClient> users = SendCoordinate.users;

            for(CoordinatesToClient u : users){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    socketServer.broadcast(JsonUtil.getJsonString(new SocketTo(u)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
