package by.cniitu.virtualexhibition.util;

import java.util.HashMap;
import java.util.Map;

public class ActionTypeUtil {

    public static final Map<String, Integer> actionType = new HashMap<String, Integer>(){{
       put("downloaded", 1);
       put("seen", 2);
       put("saved", 3);
       put("deleted", 4);
       put("uploaded", 5);
    }};

}
