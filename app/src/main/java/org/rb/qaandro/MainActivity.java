package org.rb.qaandro;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import org.rb.qa.storage.IStorageFactory;
import org.rb.qa.storage.StorageFactories;
import org.rb.qa.storage.StorageType;
import org.rb.qa.storage.android.InitKNBase;
import org.rb.qaandro.storage.simple.SimpleFactory;
import org.rb.qaandro.filesdlg.FileDlgActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    //public static boolean dataOnDiskUpdated;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configUsedStorages();
        //getInputStream();
        setContentView(R.layout.activity_main);
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState == null) {
            showFragment(new QFragment());
        }


    }

    public InputStream getInputStream()  {
        AssetManager assetsManager = getResources().getAssets();
        InputStream istream=null;

        try{
            //If private local file is available use it.
            istream = openFileInput(InitKNBase.knbXML);
            Log.d("Use file","Private local file "+InitKNBase.knbXML);
            return istream;
        }catch(FileNotFoundException ex){
            Log.i("Info",InitKNBase.knbXML+" local private file not found");
            //continue from assets...
        }
        try {
            //use file from resource folder 'assets'
            /***
            String[] slist = assetsManager.list(""); //("assets");
            for (String el: slist ) {
                System.out.println("Asset--->:"+el);
            }
             ***/
            InputStream assetsInputStream = assetsManager.open(InitKNBase.knbXML);
            istream = assetsInputStream;
            Log.d("Use Assets:",assetsInputStream.toString());

        } catch (IOException e) {
            Log.d("Assets:",e.getMessage());
            return null;
        }
        return istream;
    }


    private void configUsedStorages() {
        Map<StorageType,IStorageFactory> usedStorages= new HashMap<>();
        usedStorages.put(StorageType.Default, new SimpleFactory());
        usedStorages.put(StorageType.SimpleStorage, new SimpleFactory());
        StorageFactories.configFactories(usedStorages);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.dlg_activity){
            Intent it = new Intent(this, FileDlgActivity.class);
            startActivity(it);
            return true;
        }
        if(id == R.id.rest_client){
            Intent restFulClient = new Intent(this, RestfulClientActivity.class);
            startActivity(restFulClient);
            return true;
        }
        /***
        if(id == R.id.saveKnb){
            Fragment currFrgm = getCurrentFragment();
            if(currFrgm != null && currFrgm.getClass().getSimpleName().equals("QFragment")){

               QFragment qFrgm = (QFragment) currFrgm;
                KNBaseEditor.take(qFrgm.getQaGenerator().getKnBase())
                        .add(new QA("TestQuestion","Test Answer"));
                try {
                    KNBaseSaver.take(qFrgm.getQaGenerator().getKnBase(),StorageFactories.take().getFactory())
                            .save(openFileOutput(InitKNBase.knbXML, Context.MODE_PRIVATE));
                    Toast.makeText(this,"Local KNB saved...",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else{
                Toast.makeText(this,"No QFragment selected...",Toast.LENGTH_SHORT).show();
            }

            return true;
        }
         ***/
        return super.onOptionsItemSelected(item);
    }

    //---------Fragments control methods-----------//

    protected void showFragment(Fragment fragment) {

        String TAG = fragment.getClass().getSimpleName();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //----/
        fragmentTransaction.setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
        );
         //---/
        fragmentTransaction.replace(R.id.fragment, fragment, TAG);
        //fragmentTransaction.add(R.id.fragment,fragment,TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    protected void backstackFragment() {
        Log.d("Stack count", getSupportFragmentManager().getBackStackEntryCount() + "");
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        }
        getSupportFragmentManager().popBackStack();
        removeCurrentFragment();
    }

    protected void removeCurrentFragment() {
        //getSupportFragmentManager().popBackStack();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment currentFrag = getSupportFragmentManager()
                .findFragmentById(R.id.fragment);

        if (currentFrag != null) {
            transaction.remove(currentFrag);
        }
        transaction.commit();
    }

    protected Fragment getCurrentFragment(){
        //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment currentFrag = getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
        return currentFrag;
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

        /*
        Important! to have stm below for when the back button pressed.
        Otherwise, undesired blank frame will come on screen...
         */
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        }
    }
    //----------------------------------//

    protected  void setToolBarTitle(String title){

        toolbar.setTitle(title);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save fields instances if any in outState for next Resume

    }


}
