package org.rb.qaandro.tools;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class Dialogs {


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
}
