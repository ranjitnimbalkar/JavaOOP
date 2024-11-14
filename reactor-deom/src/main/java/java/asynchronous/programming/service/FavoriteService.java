package java.asynchronous.programming.service;

import java.util.HashMap;
import java.util.Map;

public class FavoriteService {

    private  static  HashMap<String, String> favDetails = new HashMap<>();

    static  {
        favDetails.put("Sports", "I love Sports");
        favDetails.put("Tracking", "I love Tracking");
        favDetails.put("Gym", "I love Gym");
        favDetails.put("Swimming", "I love Swimming");
    }

    public static String getDetails(String favId) {
        return favDetails.get(favId);
    };
}
