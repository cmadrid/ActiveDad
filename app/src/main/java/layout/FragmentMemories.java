package layout;


import android.content.Intent;
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
import android.widget.ProgressBar;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ec.edu.espol.integradora.dadtime.Collage;
import ec.edu.espol.integradora.dadtime.CustomAdapterMemory;
import ec.edu.espol.integradora.dadtime.Memory;
import ec.edu.espol.integradora.dadtime.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMemories extends Fragment {
    public GridView gvMemories;
    public ArrayList<Memory> memories;
    ProgressBar progressBar;
    public static FragmentMemories fragmentMemories;


    public FragmentMemories() {
        // Required empty public constructor
    }

    public static FragmentMemories newInstance() {
        return new FragmentMemories();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMemories=this;
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
            Collections.sort(files, new FileDateComparator());
            memories = new ArrayList<>();
            for(File file:files){
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(file.lastModified());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                dateFormat.setTimeZone(cal.getTimeZone());

                memories.add(new Memory(dateFormat.format(cal.getTime()), file.getAbsolutePath()));
            }
            customAdapterMemor = new CustomAdapterMemory(getActivity(), memories);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean response) {
            progressBar.setVisibility(View.GONE);
            setAdapterGvMemories(customAdapterMemor);
        }
    }

    public void setAdapterGvMemories(CustomAdapterMemory customAdapterMemor){
        if(customAdapterMemor==null)
            customAdapterMemor = new CustomAdapterMemory(getActivity(), memories);
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

    public class FileDateComparator implements Comparator<File> {
        public int compare(File f1, File f2) {
            return f1.lastModified()>=f2.lastModified()?-1:1;
        }
    }

    public ArrayList<Memory> getMemories() {
        return memories;
    }

    public void setMemories(ArrayList<Memory> memories) {
        this.memories = memories;
    }

    @Override
    public void onDestroy() {
        fragmentMemories=null;
        super.onDestroy();
    }
}
