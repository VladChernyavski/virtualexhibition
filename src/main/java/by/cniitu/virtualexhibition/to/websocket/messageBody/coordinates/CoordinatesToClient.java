package by.cniitu.virtualexhibition.to.websocket.messageBody.coordinates;

import by.cniitu.virtualexhibition.to.websocket.messageBody.MessageBody;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
public class CoordinatesToClient extends MessageBody {

    private Integer userId;
    private Double c_x;
    private Double c_z;
    private String playerColor;

    public CoordinatesToClient(Integer userId, Double c_x, Double c_z, String playerColor){
        super("player moved");
        this.userId = userId;
        this.c_x = c_x;
        this.c_z = c_z;
        this.playerColor = playerColor;
    }

    public CoordinatesToClient(CoordinatesToServer coordinatesToServer){
        this(coordinatesToServer.getUserId(), coordinatesToServer.getC_x(), coordinatesToServer.getC_z(),
                coordinatesToServer.getPlayerColor());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoordinatesToClient userTo = (CoordinatesToClient) o;
        return Objects.equals(userId, userTo.userId) &&
                Objects.equals(c_x, userTo.c_x) &&
                Objects.equals(c_z, userTo.c_z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, c_x, c_z);
    }

    @Override
    public String toString() {
        return "{" +
                "\"messageDesc\": \"" + getMessageDesc() + "\"" +
                ", \"userId\": " + userId +
                ", \"c_x\": " + c_x +
                ", \"c_z\": " + c_z +
                ", \"playerColor\": \"" + playerColor + "\"" +
                '}';
    }

}
