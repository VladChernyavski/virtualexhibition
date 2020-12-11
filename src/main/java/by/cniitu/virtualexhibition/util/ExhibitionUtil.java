package by.cniitu.virtualexhibition.util;

import by.cniitu.virtualexhibition.entity.exhibition.ExhibitionObject;
import by.cniitu.virtualexhibition.entity.exhibition.Stand;
import by.cniitu.virtualexhibition.entity.exhibition.StandObject;
import by.cniitu.virtualexhibition.to.ExhibitionObjectTo;
import by.cniitu.virtualexhibition.to.StandObjectTo;
import by.cniitu.virtualexhibition.to.StandTo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ExhibitionUtil {

    public static List<StandTo> getStandTo(List<Stand> stands) {
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
                standObjectTo.setTexture(standObject.getTexture().getPath());
                standObjectTo.setImage(standObject.getImage());
                standObjectTos.add(standObjectTo);
            }

            standTo.setStandObjects(standObjectTos);

            standTos.add(standTo);
        }

        return standTos;
    }

    public static List<ExhibitionObjectTo> getExhibitionObjectTos(List<ExhibitionObject> exhibitionObjects) {
        List<ExhibitionObjectTo> exhibitionObjectTos = new ArrayList<>();

        for (ExhibitionObject object : exhibitionObjects){
            ExhibitionObjectTo objectTo = new ExhibitionObjectTo();
            objectTo.setId(object.getId());
            objectTo.setName(object.getName());
            objectTo.setCoordinates(new LinkedList<Double>() {{
                add(object.getCoordinate_x());
                add(object.getCoordinate_y());
                add(object.getCoordinate_z());
            }});
            objectTo.setRotations(new LinkedList<Double>() {{
                add(object.getRotation_x());
                add(object.getRotation_y());
                add(object.getRotation_z());
            }});
            objectTo.setScale(new LinkedList<Double>() {{
                add(object.getScale_x());
                add(object.getScale_y());
                add(object.getScale_z());
            }});

            objectTo.setObjectModel(object.getObjectModel());
            exhibitionObjectTos.add(objectTo);
        }

        return exhibitionObjectTos;
    }

}
