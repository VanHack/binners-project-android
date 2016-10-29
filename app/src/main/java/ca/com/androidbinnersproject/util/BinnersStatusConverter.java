package ca.com.androidbinnersproject.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jonathan_campos on 28/10/2016.
 */

public class BinnersStatusConverter {
    private static final Map<String, String> mMapStatus = new HashMap<>(5);

    static {
        mMapStatus.put("on_going", "Ongoing");
        mMapStatus.put("waitingreview", "Waiting Review");
        mMapStatus.put("completed", "Completed");
        mMapStatus.put("canceled", "Canceled");
    }

    public static String getStatusDescription(final String key) {
        return mMapStatus.get(key);
    }

}
