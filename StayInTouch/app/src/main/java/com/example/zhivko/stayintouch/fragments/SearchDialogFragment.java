package com.example.zhivko.stayintouch.fragments;


import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.panche.rssreader.R;


public class SearchDialogFragment extends DialogFragment {

    private IComunicator comunicator;
    private Button backFragment;
    private ImageButton searchFragment;
    private EditText phraseFragment;
    private String phrase = "";

    public SearchDialogFragment(IComunicator comunicator) {
        this.comunicator = comunicator;
    }

    @Override
    public void onAttach(Context context) {
        this.comunicator = (IComunicator) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_dialog, container, false);
        searchFragment = (ImageButton) root.findViewById(R.id.fragment_button_search);
        searchFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                getting word for search news method
                 */
                phrase = phraseFragment.getText().toString();
                comunicator.comunicate(phrase);
                phraseFragment.setText("");
                dismiss();
            }
        });
        backFragment = (Button) root.findViewById(R.id.fragment_button_back);
        backFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phraseFragment.setText("");
                dismiss();
            }
        });
        phraseFragment = (EditText) root.findViewById(R.id.fragment_edittext);
        setCancelable(false);
        getDialog().setTitle("NEWS SEARCH BY WORD");
        return root;
    }

     /*
      communicator pattern for communication  between this fragment and context that is using this fragment
     */

    public interface IComunicator{
        void comunicate(String phrase);
    }

}
