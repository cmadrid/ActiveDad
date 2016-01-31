package layout;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ec.edu.espol.integradora.dadtime.Collage;
import ec.edu.espol.integradora.dadtime.CustomAdapterMemory;
import ec.edu.espol.integradora.dadtime.ImageHandler;
import ec.edu.espol.integradora.dadtime.Memory;
import ec.edu.espol.integradora.dadtime.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMemories extends Fragment {
    private GridView gvMemories;
    ArrayList<Memory> memories;


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
        loadMemories();
        // Inflate the layout for this fragment
        return view;
    }
    public void loadMemories(){


        List<File> files = Collage.getListFiles(new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES) + "/DadTime/Collage-DT"));
        memories = new ArrayList<>();
        for(File file:files){
            memories.add(new Memory("JUEVES, 28 DE ENERO DE 2016", file.getAbsolutePath()));
        }
        gvMemories.setAdapter(new CustomAdapterMemory(getActivity(), memories));
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
