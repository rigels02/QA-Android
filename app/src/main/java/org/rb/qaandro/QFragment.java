package org.rb.qaandro;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import org.rb.qa.model.KNBase;
import org.rb.qa.service.DocType;
import org.rb.qa.service.QAGenerator;
import org.rb.qa.storage.StorageFactories;
import org.rb.qa.storage.android.InitKNBase;
import org.rb.qaandro.qfragment.QItem;
import org.rb.qaandro.qfragment.QItemAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView mlv;
    private QAGenerator qaGenerator;
    private int htmlIconId;
    private int markDownIconId;


    public QFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QFragment newInstance(String param1, String param2) {
        QFragment fragment = new QFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_q, container, false);
        mlv = view.findViewById(R.id.qFrgListView);
        htmlIconId = R.mipmap.ic_html;
        markDownIconId = R.mipmap.ic_md;
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<String>());
        QItemAdapter adapter = new QItemAdapter(getActivity(), R.layout.listview_item_row, new ArrayList<QItem>());
        
        mlv.setAdapter(adapter);
        mlv.setOnItemClickListener(getOnItemClickListener());
        return view;
    }

    private AdapterView.OnItemClickListener getOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),"Selected: "+adapterView.getAdapter().getItem(i),Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity())
                        .showFragment(
                                AFragment.newInstance(
                                        qaGenerator.getAnswerforQuestionByIndex(i)
                                                ));
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            updateListView();
        } catch (Exception e) {
            Log.e("Exception",e.getMessage());
            showError("Exception Error",e.getMessage());
        }

    }

    private List<QItem> createQItems(){
        List<QItem> qitems = new ArrayList<>();
        for(int i=0; i< qaGenerator.getAllQuestionsList().size(); i++){
            String qtxt = qaGenerator.getQuestionByIndex(i);
            DocType docType = qaGenerator.getAnswerDocTypeByIndex(i);
            if(docType==DocType.HTML) {
                qitems.add(new QItem(htmlIconId,qtxt));
            }
            if(docType==DocType.Markdown) {
                qitems.add(new QItem(markDownIconId,qtxt));
            }
        }
        return qitems;
    }

    private void updateListView() throws Exception {
        QItemAdapter adapter = (QItemAdapter) mlv.getAdapter();
        if(qaGenerator == null) {
            MainActivity mainActivity = (MainActivity) getActivity();
            KNBase knBase = InitKNBase.go(
                    StorageFactories.take().getFactory(),
                    mainActivity.getInputStream());
            qaGenerator = new QAGenerator(knBase);
        }
            adapter.clear();

            adapter.addAll( createQItems() );

    }

    public  void showError(String title,String msg){
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        //final AlertDialog dlg;
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                     //dlg.dismiss();
                dialogInterface.dismiss();
            }
        });

        alert.create().show();
    }

    //TODO for test remove...
    public QAGenerator getQaGenerator() {
        return qaGenerator;
    }
}
