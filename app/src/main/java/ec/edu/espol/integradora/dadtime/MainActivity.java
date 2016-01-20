package ec.edu.espol.integradora.dadtime;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

<<<<<<< HEAD
import layout.FragmentEntertainments;
import layout.FragmentMemories;
=======
import java.io.File;
import java.util.Calendar;

import layout.FragmentActivities;
import layout.FragmentExperiences;
import layout.FragmentStatistics;
>>>>>>> origin/master

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Activity activity;
    private Context context;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.activity = this;
        this.context = getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            new AlertDialog.Builder(activity)
                    .setTitle("DadTime")
                    .setMessage("Una recomendacion de actividad para realizar con el hijo... aqui el padre puede indicar si la acepta o no")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            activity.finish();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                            activity.finish();
                        }
                    })
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return true;
        }
        else if (id == R.id.camera_setting)
        {

            //Si no existe se crea el directorio a guardar
            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/GamingCoach/Photos");
            directory.mkdirs();

            int IMAGE_CAPTURE = 102;
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //Se guarda la fecha para ponerla de nombre en la imagen
            String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
            //se defina guardar en la carpeta destinada con la fecha de nombre
            File mediaFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/GamingCoach/Photos/" + mydate + ".jpg");
            Uri imgUri = Uri.fromFile(mediaFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
            startActivityForResult(intent, IMAGE_CAPTURE);

            return true;
        }
        else if(id == R.id.shortcut_setting){
            //addShortcut();
            openCamera();
        }
        else if (id == R.id.start_service) {
            startService(new Intent(getBaseContext(),ServiceBackground.class));
            //Toast.makeText(this,"inicia",Toast.LENGTH_LONG).show();
            return true;
        }
        else if (id == R.id.stop_service) {

            stopService(new Intent(getBaseContext(),ServiceBackground.class));
            //Toast.makeText(this,"termina",Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void openCamera(){
/*
        //Si no existe se crea el directorio a guardar
        File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/GamingCoach/Photos");
        directory.mkdirs();

        int IMAGE_CAPTURE = 102;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Se guarda la fecha para ponerla de nombre en la imagen
        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        //se defina guardar en la carpeta destinada con la fecha de nombre
        File mediaFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/GamingCoach/Photos/" + mydate + ".jpg");
        Uri imgUri = Uri.fromFile(mediaFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        startActivityForResult(intent, IMAGE_CAPTURE);

*/

        Intent shortcutIntent = new Intent(getApplicationContext(), CameraActivity.class);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Camera DadTime");
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(getApplicationContext(),
                        R.mipmap.ic_launcher));
        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");

        getApplicationContext().sendBroadcast(addIntent);


    }

    private void addShortcut() {
        //Adding shortcut for MainActivity
        //on Home screen
        Intent shortcutIntent = new Intent(getApplicationContext(),
                ProfileFirstActivity.class);

        shortcutIntent.setAction(Intent.ACTION_MAIN);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Camera DadTime");
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(getApplicationContext(),
                R.mipmap.ic_launcher));
        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");

        getApplicationContext().sendBroadcast(addIntent);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NAME = "section_name";
        public PlaceholderFragment() {

        }
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(CharSequence name) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putCharSequence(ARG_SECTION_NAME,name);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getCharSequence(ARG_SECTION_NAME)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment fragment;
            switch (position)
            {
                case 0:
                    fragment = FragmentEntertainments.newInstance();
                    break;
                case 1:
                    fragment = FragmentMemories.newInstance();
                    break;
                default:
                    fragment = PlaceholderFragment.newInstance(getPageTitle(position));
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Actividades";
                case 1:
                    return "Recuerdos";
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
