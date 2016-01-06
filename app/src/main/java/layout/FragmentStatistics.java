package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ec.edu.espol.integradora.dadtime.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentStatistics extends Fragment {


    public FragmentStatistics() {
        // Required empty public constructor
    }

    public static FragmentStatistics newInstance() {
        FragmentStatistics fragment = new FragmentStatistics();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

}
