package by.cniitu.virtualexhibition.service.mock;

import by.cniitu.virtualexhibition.service.SocketServerService;
import by.cniitu.virtualexhibition.to.websocket.SocketTo;
import by.cniitu.virtualexhibition.to.websocket.messageBody.coordinates.CoordinatesToClient;
import by.cniitu.virtualexhibition.util.JsonUtil;

import java.util.LinkedList;
import java.util.List;

public class SendCoordinate extends Thread {

    public static final List<CoordinatesToClient> users = new LinkedList<CoordinatesToClient>(){{
        add(new CoordinatesToClient(2, 0d, -3.11d, "#FFFFFF"));
        add(new CoordinatesToClient( 2, 0.91d, -2.88d, "#F6A24E"));
        add(new CoordinatesToClient( 2, 1.75d, -2.525d, "#F1D7BB"));
        add(new CoordinatesToClient( 2, 2.543d, -2.147d, "#EACDD0"));
        add(new CoordinatesToClient( 2, 2.777d, -1.447d, "#E7BDB3"));
        add(new CoordinatesToClient( 2, 2.67d, -0.6d, "#DDD90A"));
        add(new CoordinatesToClient( 2, 2.531d, 0.141d, "#DA836E"));
        add(new CoordinatesToClient( 2, 2.274d, 0.929d, "#D80000"));
        add(new CoordinatesToClient( 2, 1.776d, 1.446d, "#AE8094"));
        add(new CoordinatesToClient( 2, 0.878d, 1.684d, "#A87550"));
        add(new CoordinatesToClient( 2, -0.082d, 1.854d, "#A4B83C"));
        add(new CoordinatesToClient( 2, -0.981d, 1.8d, "#9BD3CB"));
        add(new CoordinatesToClient( 2, -1.783d, 1.593d, "#A4B83C"));
        add(new CoordinatesToClient( 2, -2.225d, 1.244d, "#A87550"));
        add(new CoordinatesToClient( 2, -2.451, 0.579d, "#AE8094"));
        add(new CoordinatesToClient( 2, -2.747d, -0.051d, "#D80000"));
        add(new CoordinatesToClient( 2, -2.954, -0.849d, "#DA836E"));
        add(new CoordinatesToClient( 2, -2.806, -1.669d, "#DDD90A"));
        add(new CoordinatesToClient( 2, -2.613, -2.384d, "#E7BDB3"));
        add(new CoordinatesToClient( 2, -2.23, -2.937d, "#EACDD0"));
        add(new CoordinatesToClient( 2, -1.598, -3.305d, "#F1D7BB"));
        add(new CoordinatesToClient( 2, -0.781, -3.195d, "#F6A24E"));
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
