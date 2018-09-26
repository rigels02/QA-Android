package knbinit;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import org.rb.qa.storage.KNBSelectorConst;

import java.util.Arrays;
import java.util.List;

public class KNBSelector {


    private static final String CURRENT_KNB = "Current_KNB_PREFS";

    private final SharedPreferences prefs;

    public KNBSelector(SharedPreferences sharedPreferences) {

        //prefs = main_activity.getPreferences(Context.MODE_PRIVATE);
        prefs = sharedPreferences;
    }


    public int getSelectedKnb(){

        int idx = prefs.getInt(CURRENT_KNB, 0);
        return idx;

    }
    public void setSelectedKnb(int idx){

        prefs.edit().putInt(CURRENT_KNB,idx).commit();
   }

    public List<String> getTitles(){
        return Arrays.asList(KNBSelectorConst.TITLES);
    }

    public List<String> getFiles(){
        return Arrays.asList(KNBSelectorConst.FILES);
    }

    public String getSelectedTitle(){
        return KNBSelectorConst.TITLES[getSelectedKnb()];
    }

    public String getSelectedFile(){
        return KNBSelectorConst.FILES[getSelectedKnb()];
    }
}
