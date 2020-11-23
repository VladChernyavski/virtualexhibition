package by.cniitu.virtualexhibition.to.websocket;

import java.util.Objects;

public class UserTo {

    private Integer exhibId;
    private Integer userId;
    private Double c_x;
    private Double c_y;
    private Double c_z;

    public UserTo(){

    }

    public UserTo(Integer exhibId, Integer userId, Double c_x, Double c_y, Double c_z) {
        this.exhibId = exhibId;
        this.userId = userId;
        this.c_x = c_x;
        this.c_y = c_y;
        this.c_z = c_z;
    }

    public Integer getExhibId() {
        return exhibId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Double getC_x() {
        return c_x;
    }

    public Double getC_y() {
        return c_y;
    }

    public Double getC_z() {
        return c_z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTo userTo = (UserTo) o;
        return Objects.equals(exhibId, userTo.exhibId) &&
                Objects.equals(userId, userTo.userId) &&
                Objects.equals(c_x, userTo.c_x) &&
                Objects.equals(c_y, userTo.c_y) &&
                Objects.equals(c_z, userTo.c_z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exhibId, userId, c_x, c_y, c_z);
    }

    @Override
    public String toString() {
        return "User{" +
                "exhibId=" + exhibId +
                ", userId=" + userId +
                ", c_x=" + c_x +
                ", c_y=" + c_y +
                ", c_z=" + c_z +
                '}';
    }

}
