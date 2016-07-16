package ca.com.androidbinnersproject.apis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ca.com.androidbinnersproject.models.Pickup;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by Pedro Henrique on 05/07/2016.
 */
public class PickupService extends BaseAPI {
    private Retrofit retroInstance;
    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private PickupApi api;

    public PickupService() {
        retroInstance = getRetroInstance();
        api = retroInstance.create(PickupApi.class);
    }

    public List<Pickup> getPickups(String token){
        final Call<List<Pickup>> callPickups = api.getPickups(token);

        try {
            List<Pickup> pickups = callPickups.execute().body();

            return pickups;
        }
        catch (IOException ie){
            ie.printStackTrace();
        }

        return null;
    }

    public ArrayList<Pickup> getPickupsDataTest() {
        ArrayList<Pickup> pickups = new ArrayList<Pickup>();
        Pickup pickup1 = new Pickup();
        pickup1.setDateTime(Calendar.getInstance());
        pickup1.setInstructions("Pickup em Salvador-BA.");
        pickup1.getAddress().getLocation().setCoordinates(new double[]{-12.9243275, -38.4863661});
//        pickup1.setUserID(user.getId());
        pickups.add(pickup1);
        
        Pickup pickup2 = new Pickup();
        pickup2.setDateTime(Calendar.getInstance());
        pickup2.setInstructions("Pickup em Barreiras-BA.");
        pickup2.getAddress().getLocation().setCoordinates(new double[]{-12.14966578, -44.99842627});
//        pickup2.setUserID(user.getId());
        pickups.add(pickup2);

        return pickups;
    }
}
