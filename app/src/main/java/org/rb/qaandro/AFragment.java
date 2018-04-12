package org.rb.qaandro;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AFragment extends Fragment {

    private static final String ARG_HTMLSTR = "htmlstr";



    private String mHtmlStr;
    private WebView webview;


    public AFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param htmlstr Parameter 1.
     * @return A new instance of fragment AFragment.
     */

    public static AFragment newInstance(String htmlstr) {
        AFragment fragment = new AFragment();
        Bundle args = new Bundle();
        args.putString(ARG_HTMLSTR, htmlstr);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mHtmlStr = getArguments().getString(ARG_HTMLSTR);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_a, container, false);
        webview = view.findViewById(R.id.aFrgWebView);
       
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        /**
         * To properly show chars must be:
         * mimeType: "text/html; charset=utf-8"
         * encoding: "UTF-8"
         * reference: https://stackoverflow.com/questions/3312643/android-webview-utf-8-not-showing/3313266
         */
        webview.loadData(mHtmlStr,"text/html; charset=utf-8","UTF-8");
    }
}
