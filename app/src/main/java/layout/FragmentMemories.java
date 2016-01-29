package layout;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    private ListView lvMemories;
    ArrayList<Memory> memories;


    public FragmentMemories() {
        // Required empty public constructor
    }

    public static FragmentMemories newInstance() {
        FragmentMemories fragment = new FragmentMemories();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_memories, container, false);

        lvMemories = (ListView) view.findViewById(R.id.lvMemories);
        loadMemories();
        // Inflate the layout for this fragment
        return view;
    }
    public void loadMemories(){


        List<File> files = Collage.getListFiles(new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES) + "/DadTime/Collage-DT"));
        //Memory memory = new Memory("memoria 1", Collage.getSmallBitmap())
        memories = new ArrayList<>();
        for(File file:files){
            memories.add(new Memory("JUEVES, 28 DE ENERO DE 2016", ImageHandler.getSmallBitmap(file.getAbsolutePath(), 1080)));
        }
        lvMemories.setAdapter(new CustomAdapterMemory(getActivity(), memories));


    }

}
