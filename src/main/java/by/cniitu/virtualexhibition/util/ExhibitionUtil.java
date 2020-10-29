package by.cniitu.virtualexhibition.util;

import by.cniitu.virtualexhibition.entity.exhibition.Exhibition;
import by.cniitu.virtualexhibition.entity.exhibition.Stand;
import by.cniitu.virtualexhibition.entity.exhibition.StandObject;
import by.cniitu.virtualexhibition.to.StandObjectTo;
import by.cniitu.virtualexhibition.to.StandTo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ExhibitionUtil {

    public static List<StandTo> getStandTo(Exhibition exhibition){
        List<Stand> stands = exhibition.getStands();
        List<StandTo> standTos = new ArrayList<>();

        for (Stand stand : stands) {
            StandTo standTo = new StandTo();
            standTo.setId(stand.getId());
            standTo.setName(stand.getName());
            standTo.setCoordinates(new LinkedList<Double>() {{
                add(stand.getCoordinate_x());
                add(stand.getCoordinate_y());
                add(stand.getCoordinate_z());
            }});
            standTo.setRotations(new LinkedList<Double>() {{
                add(stand.getRotation_x());
                add(stand.getRotation_y());
                add(stand.getRotation_z());
            }});
            standTo.setScale(new LinkedList<Double>() {{
                add(stand.getScale_x());
                add(stand.getScale_y());
                add(stand.getScale_z());
            }});
            standTo.setStandModel(stand.getStandModel());
            standTo.setUser(stand.getUser());

            List<StandObjectTo> standObjectTos = new ArrayList<>();
            for (StandObject standObject : stand.getStandObjects()) {
                StandObjectTo standObjectTo = new StandObjectTo();
                standObjectTo.setId(standObject.getId());
                standObjectTo.setName(standObject.getName());
                standObjectTo.setCoordinates(new LinkedList<Double>() {{
                    add(standObject.getCoordinate_x());
                    add(standObject.getCoordinate_y());
                    add(standObject.getCoordinate_z());
                }});
                standObjectTo.setRotations(new LinkedList<Double>() {{
                    add(standObject.getRotation_x());
                    add(standObject.getRotation_y());
                    add(standObject.getRotation_z());
                }});
                standObjectTo.setScale(new LinkedList<Double>() {{
                    add(standObject.getScale_x());
                    add(standObject.getScale_y());
                    add(standObject.getScale_z());
                }});
                standObjectTo.setObjectModel(standObject.getObjectModel());

                standObjectTos.add(standObjectTo);
            }

            standTo.setStandObjects(standObjectTos);

            standTos.add(standTo);
        }

        return standTos;
    }

}
