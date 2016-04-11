package com.example.zhivko.stayintouch.fragments;


import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.zhivko.stayintouch.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoNewsToShowFragment extends DialogFragment {

    private Button back;
    private IcomunicatorNoNews comunicator;

    public NoNewsToShowFragment(IcomunicatorNoNews comunicator) {
        this.comunicator = comunicator;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.comunicator = (IcomunicatorNoNews) context;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_no_news_to_show, container, false);
        setCancelable(false);
        back = (Button) root.findViewById(R.id.fragment_button_back_no_news);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comunicator.reloadInfo();
                dismiss();

            }
        });
        getDialog().setTitle("NO NEWS TO SHOW");
        return root;
    }

    /*
      communicator pattern for communication  between this fragment and context that is using this fragment
     */
    public  interface IcomunicatorNoNews{
        void comunicateNoNews();
        void reloadInfo();
    }

}
