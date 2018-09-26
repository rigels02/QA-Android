package org.rb.qaandro.tools;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;
import knbinit.KNBSelector;
import org.rb.qaandro.R;

public final class Dialogs {


    private static int newSelectedItem;

    private Dialogs() {
    }

    public enum InfoType{
        Info,
        Error
    }

    public static void infoDlg(Context activity, InfoType type, String message){

        final AlertDialog.Builder dlg = new AlertDialog.Builder(activity);

        switch (type){
            case Info:
                dlg.setIcon(android.R.drawable.ic_dialog_info);
                dlg.setTitle("Info");
                break;
            case Error:
                dlg.setIcon(android.R.drawable.ic_dialog_alert);
                dlg.setTitle("Error");
                break;
        }
        dlg.setMessage(message);
        dlg.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dlg.create().show();
    }

    public interface IChoiceKNBFile{

        void callback(int selectedKnbIdx);
    }

    public static void choiceKNBFile(
            final Context activity,
            CharSequence[] items,
            final int selectedItem,
            final IChoiceKNBFile callBackItf){

        final AlertDialog.Builder dlg = new AlertDialog.Builder(activity);
        dlg.setTitle(R.string.select_knb_title);
        newSelectedItem = selectedItem;
        //dlg.setMessage("Choice KNB file to view")

        dlg.setSingleChoiceItems(items, selectedItem, new DialogInterface.OnClickListener() {


               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   //Toast.makeText(activity, "Selected: " + i, Toast.LENGTH_SHORT).show();
                   newSelectedItem = i;
               }
           })
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(selectedItem!= newSelectedItem) {
                    //Toast.makeText(activity, "OK, i: " + newSelectedItem, Toast.LENGTH_SHORT).show();
                    callBackItf.callback(newSelectedItem);

                }
                else
                    Toast.makeText(activity,"Not Changed...",Toast.LENGTH_SHORT).show();

            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dlg.create().show();
    }
}
