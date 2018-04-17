package org.rb.qaandro;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.rb.qa.model.KNBase;
import org.rb.qa.storage.StorageFactories;
import org.rb.qa.storage.android.InitKNBase;
import org.rb.qa.storage.android.KNBaseSaver;
import org.rb.qaandro.resfulclient.INotifier;
import org.rb.qaandro.resfulclient.KNBaseService;
import org.rb.qaandro.tools.Dialogs;

import java.io.FileNotFoundException;
import java.io.OutputStream;

import static org.rb.qaandro.tools.Dialogs.infoDlg;

public class RestfulClientActivity extends AppCompatActivity {

    private static final String STATE = "RECEIVING";
    private static final String PROP_URL = "url";
    private boolean receiving;

    private TextView tvReport;
    private Button btnCompare;
    private Button btnGet;
    private ProgressBar progressBar;
    private EditText edUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState !=null)
        {
            this.receiving = savedInstanceState.getBoolean(STATE);
        }else {
            this.receiving = false;
        }

        setContentView(R.layout.activity_restful_client);
        edUrl = findViewById(R.id.edUrl);
        getSavedUrl();
        tvReport = findViewById(R.id.tvReport);
        tvReport.setText("");
        progressBar = findViewById(R.id.progressBar);
        btnGet = findViewById(R.id.btnGet);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                receiveData();

            }
        });
        btnCompare = findViewById(R.id.btnCompare);
        btnCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compareDates();
            }
        });

        setTitle(R.string.get_remote_data);
    }



    private void getSavedUrl() {
        SharedPreferences properties = getPreferences(Context.MODE_PRIVATE);
        String url = properties.getString(PROP_URL, null);
        if(url != null && !url.isEmpty())
            edUrl.setText(url);
    }
    private void saveUrl(){
        String url = edUrl.getText().toString();
        if(url.isEmpty()) return ;
        SharedPreferences properties = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = properties.edit();
        editor.putString(PROP_URL,url);
        editor.commit();
    }

    private boolean preTask(){

        if(edUrl.getText().toString().isEmpty()){
            infoDlg(this,Dialogs.InfoType.Error,"Url must not be empty");
            return false;
        }
        tvReport.setText("");
        receiving = true;
        progressBar.setVisibility(View.VISIBLE);
        saveUrl();
        btnGet.setEnabled(false);
        btnCompare.setEnabled(false);
        return true;
    }

    private void postTask(){
        btnGet.setEnabled(true);
        btnCompare.setEnabled(true);
        progressBar.setVisibility(View.INVISIBLE);
        receiving=false;

    }

    private void compareDates() {
        if( !preTask() ) return;
        new RestfulClientCompareDateTask().execute(edUrl.getText().toString());
    }

    private void receiveData() {

        if( !preTask() ) return;

       new RestfulClientTask().execute(edUrl.getText().toString());
    }

    private class RestfulClientCompareDateTask extends AsyncTask<String,String,Void> implements INotifier
    {


        @Override
        protected Void doInBackground(String... url) {
            try {
                new KNBaseService(url[0],this).compareDates();
            } catch (Exception e) {
               message(e.getMessage());
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... msg) {
            tvReport.append(msg[0]+"\n");
        }

        @Override
        protected void onPostExecute(Void d) {
            postTask();
        }

        @Override
        public void message(String msg) {
                publishProgress(msg);
        }
    }

    private class RestfulClientTask extends AsyncTask<String, String,KNBase> implements INotifier {


        @Override
        protected KNBase doInBackground(String... url) {
            KNBase knb = null;
            try {
               knb = new KNBaseService(url[0],this).getRemoteData();
            } catch (Exception e) {
               message(e.getMessage());
               return null;
            }

           return knb;
        }

        @Override
        protected void onProgressUpdate(String... msg) {

           tvReport.append(msg[0]+"\n");
        }

       
        @Override
        protected void onPostExecute(KNBase knBase) {
            postTask();
            if(knBase==null){
                message("Nothing received to be saved...");
                return;
            }

            try {
                OutputStream os;
                KNBaseSaver
                        .take(knBase,StorageFactories.take().getFactory())
                        .save((os=getOutputStream()));
                os.close();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                message("Error: "+e.getMessage());
                return;
            }
            message("Received KNB stored....\nCompleted!");
            QFragment.dataOnDiskUpdated=true;
        }

        @Override
        public void message(String msg) {
            publishProgress(msg);
        }
    }

    private OutputStream getOutputStream() throws FileNotFoundException {
            return openFileOutput(InitKNBase.knbXML, MODE_PRIVATE);

    }

    @Override
    public void onBackPressed() {
        //if receiving data disable back press handling
        if(receiving)
              return;
        super.onBackPressed();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE,this.receiving);
        super.onSaveInstanceState(outState);
    }


}
