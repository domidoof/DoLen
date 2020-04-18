package com.example.dolen.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.example.dolen.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    //hier wird entschieden bei welchem Tab welches layout geladen wird
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if(getArguments().getInt(ARG_SECTION_NUMBER)==1) {
            View rootView = inflater.inflate(R.layout.fragment_to_do, container, false);
            return rootView;
        }
        else if(getArguments().getInt(ARG_SECTION_NUMBER)==2){
            View rootView = inflater.inflate(R.layout.fragment_chat , container, false);
            return rootView;
        }
        else {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            final TextView textView = rootView.findViewById(R.id.section_label);
            pageViewModel.getText().observe(this, new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    textView.setText(s);
                }
            });
            return rootView;
        }

    }
}