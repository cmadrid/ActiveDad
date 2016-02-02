package layout;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import database.DBActivity;
import ec.edu.espol.integradora.dadtime.Collage;
import ec.edu.espol.integradora.dadtime.CustomAdapterMemory;
import ec.edu.espol.integradora.dadtime.Entertainment;
import ec.edu.espol.integradora.dadtime.ImageHandler;
import ec.edu.espol.integradora.dadtime.Memory;
import ec.edu.espol.integradora.dadtime.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMemories extends Fragment {
    private GridView gvMemories;
    ArrayList<Memory> memories;
    ProgressBar progressBar;


    public FragmentMemories() {
        // Required empty public constructor
    }

    public static FragmentMemories newInstance() {
        return new FragmentMemories();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_memories, container, false);

        gvMemories = (GridView) view.findViewById(R.id.gvMemories);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        progressBar.setVisibility(View.VISIBLE);


        LoadMemories loadMemories = new LoadMemories();
        loadMemories.execute();

        // Inflate the layout for this fragment
        return view;
    }
    private class LoadMemories extends AsyncTask<Void, Void, Boolean> {
        CustomAdapterMemory customAdapterMemor = null;
        @Override
        protected Boolean doInBackground(Void... param) {
            List<File> files = Collage.getListFiles(new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES) + "/DadTime/Collage-DT"));
            memories = new ArrayList<>();
            for(File file:files){
                memories.add(new Memory("JUEVES, 28 DE ENERO DE 2016", file.getAbsolutePath()));
            }
            customAdapterMemor = new CustomAdapterMemory(getActivity(), memories);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean response) {
            progressBar.setVisibility(View.GONE);
            gvMemories.setAdapter(customAdapterMemor);
            gvMemories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    Uri imgUri = Uri.parse("file://" + memories.get(position).getPath());
                    intent.setDataAndType(imgUri, "image/*");
                    getActivity().startActivity(intent);
                }
            });

        }
    }

}
