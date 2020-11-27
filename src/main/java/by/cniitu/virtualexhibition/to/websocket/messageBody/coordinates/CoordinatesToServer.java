package by.cniitu.virtualexhibition.to.websocket.messageBody.coordinates;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
public class CoordinatesToServer extends CoordinatesToClient {

    private Integer exhibitId;

    public CoordinatesToServer(Integer userId, Double c_x, Double c_y, Double c_z, String playerColor, Integer exhibitId){
        super(userId, c_x, c_z, playerColor);
        this.exhibitId = exhibitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoordinatesToServer userTo = (CoordinatesToServer) o;
        return Objects.equals(getUserId(), userTo.getUserId()) &&
                Objects.equals(getC_x(), userTo.getC_x()) &&
                Objects.equals(getC_z(), userTo.getC_z()) &&
                Objects.equals(exhibitId, userTo.exhibitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getC_x(), getC_z(), exhibitId);
    }

    @Override
    public String toString() {
        return "{" +
                "\"messageDesc\": \"" + getMessageDesc() + "\"" +
                ", \"exhibitId\": " + getExhibitId() +
                ", \"userId\": " + getUserId() +
                ", \"c_x\": " + getC_x() +
                ", \"c_z\": " + getC_z() +
                ", \"playerColor\": \"" + getPlayerColor() + "\"" +
                '}';
    }

}
