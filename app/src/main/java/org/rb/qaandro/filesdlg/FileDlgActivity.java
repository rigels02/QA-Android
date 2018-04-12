package org.rb.qaandro.filesdlg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import org.rb.qaandro.R;

public class FileDlgActivity extends AppCompatActivity implements FileSelectFragment.Callbacks {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_dlg);
        tv= (TextView) findViewById(R.id.textView);

    }


    @Override
    public void onConfirmSelect(String absolutePath, String fileName) {
        Log.d("onConfirmSelect","File selected: "+absolutePath+":"+fileName);
        System.out.println("File selected: "+absolutePath+":"+fileName);
        tv.setText("File selected: "+absolutePath+":"+fileName);
    }

    @Override
    public boolean isValid(String absolutePath, String fileName) {
        Log.d("isValid","File selected: "+absolutePath+":"+fileName);
        System.out.println("File selected: "+absolutePath+":"+fileName);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_file_dlg, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.file_select_dlg) {
            FileSelectFragment fsf = FileSelectFragment.newInstance(
                    FileSelectFragment.Mode.FileSelector,
                    android.R.string.ok,android.R.string.cancel,
                    R.string.select_file_title,
                    /*android.R.drawable.ic_dialog_info,*/
                    R.mipmap.ic_file_dlg,
                    R.mipmap.ic_dir,
                    R.mipmap.ic_file

                    );
            fsf.show(getFragmentManager(),fsf.getClass().getName());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}