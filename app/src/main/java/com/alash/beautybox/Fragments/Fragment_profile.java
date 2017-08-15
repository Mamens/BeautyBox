package com.alash.beautybox.Fragments;

import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.alash.beautybox.R;

public class Fragment_profile extends Fragment {
    Fragments fragments;
    Fragment_redact_profile fragment_readact_profile;
    Fragment_history_zakazov fragment_history_zakazov;
    Fragment_contacts fragment_contacts;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_profile, null);
        fragment_readact_profile = new Fragment_redact_profile();
        fragment_history_zakazov = new Fragment_history_zakazov();
        fragment_contacts = new Fragment_contacts();

        RelativeLayout edit_profile_relative_layout = (RelativeLayout) v.findViewById(R.id.edit_profile_relative_layout);
        RelativeLayout edit_profile_history_zakazov = (RelativeLayout) v.findViewById(R.id.edit_profile_history_zakazov);
        RelativeLayout edit_profile_contacts = (RelativeLayout) v.findViewById(R.id.edit_profile_contacts);
        //RelativeLayout edit_profile_relative_layout = (RelativeLayout) v.findViewById(R.id.edit_profile_relative_layout);
        edit_profile_relative_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragments != null)
                fragments.show(fragment_readact_profile);
                else Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
        edit_profile_history_zakazov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragments !=null)
                    fragments.show(fragment_history_zakazov);

            }
        });
        edit_profile_contacts.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(fragments !=null)
                    fragments.show(fragment_contacts);
            }
        });

    return v;
    }

    public void setFragment(Fragments fragments) {
        this.fragments = fragments;
    }
}
