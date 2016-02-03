package layout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import database.DBActivity;
import ec.edu.espol.integradora.dadtime.CustomAdapterEntertainment;
import ec.edu.espol.integradora.dadtime.CustomAdapterFilter;
import ec.edu.espol.integradora.dadtime.Entertainment;
import ec.edu.espol.integradora.dadtime.EntertainmentActivity;
import ec.edu.espol.integradora.dadtime.ImageHandler;
import ec.edu.espol.integradora.dadtime.MainActivity;
import ec.edu.espol.integradora.dadtime.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEntertainments extends Fragment {

    TableLayout tlDays;
    private Button btnMonday;
    private Button btnTuesday;
    private Button btnWednesday;
    private Button btnThursday;
    private Button btnFriday;
    private Button btnSaturday;
    private Button btnSunday;
    TextView tvDate;
    ListView lvEntertainments;
    ArrayList<Entertainment> entertainments;
    ArrayList<Entertainment> entertainmentsSpecificDay;
    public Entertainment selectedEntertaiment;//CM
    public static FragmentEntertainments actual;//CM
    ProgressBar progressBar;
    Calendar calendar;
    FloatingActionButton filter;
    TabLayout.Tab tab;

    ArrayList<String> filters_array;
    boolean filter_my_activities=false;
    private String textFilter = "";

    MenuItem mSearchView;
    SearchView searchView;

    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    public FragmentEntertainments() {
        // Required empty public constructor
    }

    public static FragmentEntertainments newInstance() {
        return new FragmentEntertainments();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        actual=this;//CM
        //container=viewPager
        //container.getParent()=CoordinatorLayout
        preferenceSettings = getActivity().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);//CM
        preferenceEditor = preferenceSettings.edit();//CM
        filter = (FloatingActionButton)((View) container.getParent()).findViewById(R.id.fabFilterActivity);//CM
        tab = ((TabLayout)((View) container.getParent()).findViewById(R.id.tabs)).getTabAt(0);
        System.out.println("padre");
        System.out.println(((View) container.getParent()).getParent());
        setFilter();//CM

        View view = inflater.inflate(R.layout.fragment_entertainments, container, false);
        tlDays = (TableLayout) view.findViewById(R.id.tlDays);
        btnMonday = (Button) view.findViewById(R.id.btnMonday);
        btnTuesday = (Button) view.findViewById(R.id.btnTuesday);
        btnWednesday = (Button) view.findViewById(R.id.btnWednesday);
        btnThursday = (Button) view.findViewById(R.id.btnThursday);
        btnFriday = (Button) view.findViewById(R.id.btnFriday);
        btnSaturday = (Button) view.findViewById(R.id.btnSaturday);
        btnSunday = (Button) view.findViewById(R.id.btnSunday);
        tvDate = (TextView) view.findViewById(R.id.tvDate);
        lvEntertainments = (ListView) view.findViewById(R.id.lvEntertainments);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        calendar = Calendar.getInstance();
        btnMonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMonday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                btnTuesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnWednesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnThursday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnFriday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSaturday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSunday.setBackground(getResources().getDrawable(R.drawable.button_days));
                if (calendar.get(Calendar.DAY_OF_WEEK) != 1)
                {
                    calendar.add(Calendar.DAY_OF_MONTH, (2 - calendar.get(Calendar.DAY_OF_WEEK)));
                }
                else
                {
                    calendar.add(Calendar.DAY_OF_MONTH, -6);
                }
                tvDate.setText("LUNES, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                AdapterEntertainments();
            }
        });
        btnTuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMonday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnTuesday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                btnWednesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnThursday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnFriday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSaturday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSunday.setBackground(getResources().getDrawable(R.drawable.button_days));
                if (calendar.get(Calendar.DAY_OF_WEEK) != 1)
                {
                    calendar.add(Calendar.DAY_OF_MONTH, (3 - calendar.get(Calendar.DAY_OF_WEEK)));
                }
                else
                {
                    calendar.add(Calendar.DAY_OF_MONTH, -5);
                }
                tvDate.setText("MARTES, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                AdapterEntertainments();
            }
        });
        btnWednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMonday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnTuesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnWednesday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                btnThursday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnFriday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSaturday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSunday.setBackground(getResources().getDrawable(R.drawable.button_days));
                if (calendar.get(Calendar.DAY_OF_WEEK) != 1)
                {
                    calendar.add(Calendar.DAY_OF_MONTH, (4 - calendar.get(Calendar.DAY_OF_WEEK)));
                }
                else
                {
                    calendar.add(Calendar.DAY_OF_MONTH, -4);
                }
                tvDate.setText("MIÉRCOLES, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                AdapterEntertainments();
            }
        });
        btnThursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMonday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnTuesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnWednesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnThursday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                btnFriday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSaturday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSunday.setBackground(getResources().getDrawable(R.drawable.button_days));
                if (calendar.get(Calendar.DAY_OF_WEEK) != 1)
                {
                    calendar.add(Calendar.DAY_OF_MONTH, (5 - calendar.get(Calendar.DAY_OF_WEEK)));
                }
                else
                {
                    calendar.add(Calendar.DAY_OF_MONTH, -3);
                }
                tvDate.setText("JUEVES, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                AdapterEntertainments();
            }
        });
        btnFriday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMonday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnTuesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnWednesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnThursday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnFriday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                btnSaturday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSunday.setBackground(getResources().getDrawable(R.drawable.button_days));
                if (calendar.get(Calendar.DAY_OF_WEEK) != 1)
                {
                    calendar.add(Calendar.DAY_OF_MONTH, (6 - calendar.get(Calendar.DAY_OF_WEEK)));
                }
                else
                {
                    calendar.add(Calendar.DAY_OF_MONTH, -2);
                }
                tvDate.setText("VIERNES, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                AdapterEntertainments();
            }
        });
        btnSaturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMonday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnTuesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnWednesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnThursday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnFriday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSaturday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                btnSunday.setBackground(getResources().getDrawable(R.drawable.button_days));
                if (calendar.get(Calendar.DAY_OF_WEEK) != 1)
                {
                    calendar.add(Calendar.DAY_OF_MONTH, (7 - calendar.get(Calendar.DAY_OF_WEEK)));
                }
                else
                {
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                }
                tvDate.setText("SÁBADO, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                AdapterEntertainments();
            }
        });
        btnSunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMonday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnTuesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnWednesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnThursday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnFriday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSaturday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSunday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                if (calendar.get(Calendar.DAY_OF_WEEK) != 1)
                {
                    calendar.add(Calendar.DAY_OF_MONTH, (8 - calendar.get(Calendar.DAY_OF_WEEK)));
                    tvDate.setText("DOMINGO, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                    AdapterEntertainments();
                }
            }
        });
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        progressBar.setVisibility(View.VISIBLE);
        switch (calendar.get(Calendar.DAY_OF_WEEK))
        {
            case 2:
                btnMonday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                tvDate.setText("LUNES, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                break;
            case 3:
                btnTuesday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                tvDate.setText("MARTES, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                break;
            case 4:
                btnWednesday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                tvDate.setText("MIÉRCOLES, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                break;
            case 5:
                btnThursday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                tvDate.setText("JUEVES, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                break;
            case 6:
                btnFriday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                tvDate.setText("VIERNES, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                break;
            case 7:
                btnSaturday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                tvDate.setText("SÁBADO, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                break;
            case 1:
                btnSunday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                tvDate.setText("DOMINGO, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                break;
        }
        LoadActivities loadActivities = new LoadActivities();
        loadActivities.execute();
        return view;
    }

    private String NameMonth(int month)
    {
        switch (month)
        {
            case 0:
                return "ENERO";
            case 1:
                return "FEBRERO";
            case 2:
                return "MARZO";
            case 3:
                return "ABRIL";
            case 4:
                return "MAYO";
            case 5:
                return "JUNIO";
            case 6:
                return "JULIO";
            case 7:
                return "AGOSTO";
            case 8:
                return "SEPTIEMBRE";
            case 9:
                return "OCTUBRE";
            case 10:
                return "NOVIEMBRE";
            default:
                return "DICIEMBRE";
        }
    }
    private class LoadActivities extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... param) {

            DBActivity dbActivity = new DBActivity(getContext());
            try {
                String NAMESPACE = "urn:DadTime";
                String URL = "http://www.corporacionsmartest.com/DadTimeWebServices/wsDadTime.php";
                String SOAP_ACTION = "urn:DadTime#GetActivities";
                String METHOD_NAME = "GetActivities";
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("minimumAge", 1);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransport = new HttpTransportSE(URL);
                httpTransport.call(SOAP_ACTION, envelope);
                SoapObject response = (SoapObject) envelope.bodyIn;
                Vector<?> responseVector = (Vector<?>) response.getProperty(0);
                entertainments = new ArrayList<>();


                Set<String> getSetIdEntertainments = preferenceSettings.getStringSet("idEntertainments", new HashSet<String>());
                Cursor almacenadas = dbActivity.consultar(null);
                if(almacenadas.moveToFirst())
                {
                    do{
                        Entertainment entertainment = new Entertainment();
                        entertainment.setIdActivity(almacenadas.getInt(0));
                        entertainment.setTitle(almacenadas.getString(1));
                        entertainment.setCompany(almacenadas.getString(2));
                        entertainment.setCategory(almacenadas.getString(3));
                        entertainment.setDay(almacenadas.getString(4));
                        entertainment.setSchedule(almacenadas.getString(5));
                        entertainment.setPrice(almacenadas.getString(6));
                        entertainment.setDescription(almacenadas.getString(7));
                        entertainment.setMinimumAge(almacenadas.getInt(8));
                        byte[] blob = almacenadas.getBlob(9);
                        entertainment.setImage(BitmapFactory.decodeByteArray(blob, 0, blob.length));
                        if(getSetIdEntertainments.contains(entertainment.getIdActivity()+""))
                        {
                            entertainment.setChecked(true);
                        }
                        entertainments.add(entertainment);
                        //System.out.println(entertainment);
                    }while(almacenadas.moveToNext());
                }
                //System.out.println("nuevas: ");
                for (int i = 0; i < responseVector.size(); i++)
                {
                    SoapObject respond = (SoapObject) responseVector.get(i);

                    if(!dbActivity.consultar(Integer.parseInt(respond.getProperty("idActivity").toString())).moveToFirst()) {
                        Entertainment entertainment = new Entertainment();
                        entertainment.setIdActivity(Integer.parseInt(respond.getProperty("idActivity").toString()));
                        entertainment.setTitle(respond.getProperty("title").toString());
                        entertainment.setCompany(respond.getProperty("company").toString());
                        entertainment.setCategory(respond.getProperty("category").toString());
                        entertainment.setDay(respond.getProperty("day").toString());
                        entertainment.setSchedule(respond.getProperty("schedule").toString());
                        entertainment.setPrice(respond.getProperty("price").toString());
                        entertainment.setDescription(respond.getProperty("description").toString());
                        entertainment.setMinimumAge(Integer.parseInt(respond.getProperty("minimumAge").toString()));
                        entertainment.setImage(BitmapFactory.decodeStream((InputStream) new URL(respond.getProperty("image").toString()).getContent()));
                        if(getSetIdEntertainments.contains(entertainment.getIdActivity()+""))
                        {
                            entertainment.setChecked(true);
                        }
                        entertainments.add(entertainment);
                        //System.out.println(entertainment);
                        dbActivity.insertaroActualizar(entertainment.getIdActivity(), entertainment.getTitle(), entertainment.getCompany(),
                                entertainment.getCategory(), entertainment.getDay(), entertainment.getSchedule(), entertainment.getPrice(),
                                entertainment.getDescription(), entertainment.getMinimumAge(), entertainment.getImage());
                    }
                }
                return true;
            } catch (Exception e)
            {
                return false;
            } finally {
                dbActivity.close();
            }
        }

        @Override
        protected void onPostExecute(Boolean response) {
            progressBar.setVisibility(View.GONE);
            filter.show();
            mSearchView.setVisible(true);
            if (response)
            {
                tlDays.setVisibility(View.VISIBLE);
                tvDate.setVisibility(View.VISIBLE);
                AdapterEntertainments();
            }
            else
            {
                Toast.makeText(getActivity(), "Ocurrió un error", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void AdapterEntertainments()
    {
        if(entertainments==null)
            return;
        entertainmentsSpecificDay = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < entertainments.size(); i++)
        {
            if (entertainments.get(i).getDay().equalsIgnoreCase(simpleDateFormat.format(calendar.getTime())))
            {
                if((filters_array==null || filters_array.contains(entertainments.get(i).getCategory().toLowerCase()))/*CM*/
                        && (!filter_my_activities || entertainments.get(i).isChecked())/*CM*/
                        && (textFilter.equalsIgnoreCase("")
                            || entertainments.get(i).getCategory().toLowerCase().contains(textFilter)
                            || entertainments.get(i).getTitle().toLowerCase().contains(textFilter))
                        )
                    entertainmentsSpecificDay.add(entertainments.get(i));
            }
        }
        lvEntertainments.setAdapter(new CustomAdapterEntertainment(getActivity(), entertainmentsSpecificDay));
        lvEntertainments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EntertainmentActivity.class);
                intent.putExtra("Entertainment", entertainmentsSpecificDay.get(position));
                selectedEntertaiment = entertainmentsSpecificDay.get(position);
                startActivity(intent);
            }
        });
    }

    public void setFilter(){
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"Todas las actividades", "A realizar", "Filtrar por categoria"};
                new AlertDialog.Builder(getContext()).setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                filters_array = null;
                                filter_my_activities = false;
                                AdapterEntertainments();
                                if (tab != null)
                                    tab.setText("ACTIVIDADES");
                                break;
                            case 1:
                                filters_array = null;
                                filter_my_activities = true;
                                AdapterEntertainments();
                                if (tab != null)
                                    tab.setText("MIS ACTIVIDADES");
                                break;
                            case 2:
                                filter_my_activities = false;
                                categoryPopUp();
                                break;
                        }
                    }
                })
                        .show();
            }
        });
    }

    public void categoryPopUp(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.filter_category, null);
        String[] categories={"SIN ACTIVIDADES!"};
        DBActivity dbActivity=null;
        try {
            dbActivity = new DBActivity(getActivity());
            categories = dbActivity.consultarCategorias();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(dbActivity!=null)
                dbActivity.close();
        }
        GridView gv_filter = (GridView)view.findViewById(R.id.gvCategory);
        gv_filter.setAdapter(new CustomAdapterFilter(getActivity(), categories));

        final AlertDialog ad = new AlertDialog.Builder(getActivity()).setView(view)
                .show();

        gv_filter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String category = ((TextView)view.findViewById(R.id.item_filter)).getText().toString();
                filters_array = new ArrayList<>();
                filters_array.add(category.toLowerCase());
                AdapterEntertainments();
                if (tab != null)
                    tab.setText("CATEGORIA: "+category.toUpperCase());
                ad.dismiss();
            }
        });
    }

    public void setTextFilter(String textFilter) {
        this.textFilter = textFilter;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        inflater.inflate(R.menu.menu_fragment_entertaiments, menu);

        final Menu finalMenu = menu;

        //SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        mSearchView = menu.findItem(R.id.search_menu);
        if(progressBar.getVisibility()==View.VISIBLE)mSearchView.setVisible(false);
        searchView = (SearchView) MenuItemCompat.getActionView(mSearchView);
        searchView.setSubmitButtonEnabled(false);
        //mSearchView.setVisible(false);
        MenuItemCompat.setOnActionExpandListener(mSearchView, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                finalMenu.findItem(R.id.camera_menu).setVisible(false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                finalMenu.findItem(R.id.camera_menu).setVisible(true);
                return true;
            }
        });
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                textFilter=newText;
                AdapterEntertainments();
                return false;
            }
        });






        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.camera_menu:
                // Not implemented here
                return false;
            case R.id.search_menu:
                // Do Fragment menu item stuff here
                return true;
            default:
                break;
        }

        return false;
    }

}