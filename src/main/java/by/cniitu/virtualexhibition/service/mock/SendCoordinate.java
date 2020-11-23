package by.cniitu.virtualexhibition.service.mock;

import by.cniitu.virtualexhibition.service.SocketServerService;
import by.cniitu.virtualexhibition.to.websocket.UserTo;
import by.cniitu.virtualexhibition.util.JsonUtil;

import java.util.LinkedList;
import java.util.List;

public class SendCoordinate extends Thread {

    public static final List<UserTo> users = new LinkedList<UserTo>(){{
        add(new UserTo(1, 2, 0d, 0d, -3.11d));
        add(new UserTo(1, 2, 0.91d, 0d, -2.88d));
        add(new UserTo(1, 2, 1.75d, 0d, -2.525d));
        add(new UserTo(1, 2, 2.543d, 0d, -2.147d));
        add(new UserTo(1, 2, 2.777d, 0d, -1.447d));
        add(new UserTo(1, 2, 2.67d, 0d, -0.6d));
        add(new UserTo(1, 2, 2.531d, 0d, 0.141d));
        add(new UserTo(1, 2, 2.274d, 0d, 0.929d));
        add(new UserTo(1, 2, 1.776d, 0d, 1.446d));
        add(new UserTo(1, 2, 0.878d, 0d, 1.684d));
        add(new UserTo(1, 2, -0.082d, 0d, 1.854d));
        add(new UserTo(1, 2, -0.981d, 0d, 1.8d));
        add(new UserTo(1, 2, -1.783d, 0d, 1.593d));
        add(new UserTo(1, 2, -2.225d, 0d, 1.244d));
        add(new UserTo(1, 2, -2.451, 0d, 0.579d));
        add(new UserTo(1, 2, -2.747d, 0d, -0.051d));
        add(new UserTo(1, 2, -2.954, 0d, -0.849d));
        add(new UserTo(1, 2, -2.806, 0d, -1.669d));
        add(new UserTo(1, 2, -2.613, 0d, -2.384d));
        add(new UserTo(1, 2, -2.23, 0d, -2.937d));
        add(new UserTo(1, 2, -1.598, 0d, -3.305d));
        add(new UserTo(1, 2, -0.781, 0d, -3.195d));
    }};

    SocketServerService socketServer;

    public SendCoordinate(SocketServerService socketServer) {
        this.socketServer = socketServer;
    }

    @Override
    public void run() {
        while (true){
            List<UserTo> users = SendCoordinate.users;

            for(UserTo u : users){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                socketServer.broadcast(JsonUtil.getJsonString(u));
            }

        }
    }

}
