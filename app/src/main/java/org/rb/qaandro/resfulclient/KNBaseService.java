package org.rb.qaandro.resfulclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.rb.qa.model.KNBase;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KNBaseService {

    private static final String ID= "50f0f8f0-53ea-413c-b2a3-f3dc387709c0";
    private static final String INFO ="Info" ;
    //The filename of data requested from sever side
    private static final String KNBFILE = "knb.xml";

    private final IKNBaseService service;
    private final INotifier notifier;


    /**
     * init Retrofit service
     * @param url url, ex. http://localhost:9998/knbase/
     */
    public KNBaseService(final String url, INotifier notifier) {

        this.notifier = notifier;
        Gson gson = new GsonBuilder().setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                //.addConverterFactory(JacksonConverterFactory.create())
                .build();

        service = retrofit.create(IKNBaseService.class);

    }

    private void notify(String msg){
        if(notifier==null) return;
        notifier.message(msg);
    }

    public KNBase getRemoteData() throws Exception {
        Call<String> it = service.getIT();

            Response<String> response = it.execute();
            if( !response.isSuccessful()) {
                throw new Exception(response.message());

            }

        if( !response.body().equals(ID) ){

           throw new Exception("Received ID does not match!");
        }
        print(INFO,"ID received: "+ID);
        //ID is OK
        Call<KNBase> knb_r = service.getKNBase(KNBFILE);
        Response<KNBase> knbResponse = knb_r.execute();
        if( !knbResponse.isSuccessful() )
            throw new Exception(knbResponse.message());
        KNBase knb = knbResponse.body();
        print(INFO,"knb received, Records number: "+knb.getQaList().size());
        return knb;
    }

    private void print(String tag,String msg){
        //Log.i(tag,msg);
        System.out.printf("Severity:%s, %s",tag,msg);
        notify(msg);
    }
}
