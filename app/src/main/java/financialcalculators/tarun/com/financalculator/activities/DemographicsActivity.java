package financialcalculators.tarun.com.financalculator.activities;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Looper;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;

import financialcalculators.tarun.com.financalculator.R;
import financialcalculators.tarun.com.financalculator.helper.SAXHandler.DemographicsSaxHandler;
import financialcalculators.tarun.com.financalculator.helper.URLHelper.URLParserHelper;
import financialcalculators.tarun.com.financalculator.helper.pojos.LocalDemoGraphicsItem;

import static financialcalculators.tarun.com.financalculator.R.*;


public class DemographicsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_demographics);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(id.container, new DemographicsFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_subpages, menu);
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
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        else if (id == R.id.action_home) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DemographicsFragment extends Fragment implements
            View.OnClickListener {

        Button calculateButton = null;
        Button gpsEnableButton  = null;
        private LocationManager locationManager = null;
        private LocationListener locationListener = null;
        public LocalDemoGraphicsItem item = null;
        private ProgressBar pb =null;
        //Public constructor
        public DemographicsFragment() {
            calculateButton = null;
            gpsEnableButton = null;
            item = new LocalDemoGraphicsItem();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(layout.fragment_demographics, container, false);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceBundle) {
            super.onActivityCreated(savedInstanceBundle);
            gpsEnableButton = (Button) getActivity().findViewById(id.GPSButton);
            gpsEnableButton.setOnClickListener(this);
            calculateButton = (Button) getActivity().findViewById(id.calculateButton);
            calculateButton.setOnClickListener(this);
            locationManager = (LocationManager) getActivity().getApplicationContext().
                    getSystemService(Context.LOCATION_SERVICE);
            pb = (ProgressBar) getActivity().findViewById(R.id.progressBar);
            pb.setVisibility(View.INVISIBLE);
        }

        /*----Method to Check GPS is enable or disable ----- */
        private Boolean getDisplayGpsStatus() {
            ContentResolver contentResolver = getActivity().getBaseContext()
                    .getContentResolver();
            boolean gpsStatus = Settings.Secure
                    .isLocationProviderEnabled(contentResolver,
                            LocationManager.GPS_PROVIDER);
            return gpsStatus;
        }

        @Override
        public void onClick(View v) {
            TextView getZipCodeTextView = (TextView) getActivity().findViewById(id.editTextViewZip);
            Boolean flag;

            try {
                switch (v.getId()) {
                    case id.GPSButton:
                        flag = getDisplayGpsStatus();
                        if(flag){
                            pb.setVisibility(View.VISIBLE);
                            locationListener = new LocationListener() {
                                @Override
                                public void onLocationChanged(Location loc) {

                                    pb.setVisibility(View.INVISIBLE);

                                    /*----------to get ZipCode from coordinates ------------- */
                                    Geocoder gcd = new Geocoder(getActivity().getBaseContext(),
                                            Locale.getDefault());
                                    List<Address> addresses;
                                    try {
                                        addresses = gcd.getFromLocation(loc.getLatitude(), loc
                                                .getLongitude(), 1);
                                        if ((addresses.size() > 0) && (addresses.get(0).getPostalCode() != null)) {
                                            item.zip = Integer.parseInt(addresses.get(0).getPostalCode());
                                            if (item.zip > 0 && item.zip <= 100000) {
                                                pb.setVisibility(View.INVISIBLE);
                                                new DemographicsURLParserHelper().execute(item);

                                            }
                                        }

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onStatusChanged(String provider, int status, Bundle extras) {

                                }

                                @Override
                                public void onProviderEnabled(String provider) {

                                }

                                @Override
                                public void onProviderDisabled(String provider) {

                                }
                            };
                            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, Looper.getMainLooper());
                        }
                        else{
                            alertbox();
                        }

                        break;
                    case id.calculateButton:
                        if (getZipCodeTextView != null && !getZipCodeTextView.getText().toString().isEmpty()) {
                            String zipCode = getZipCodeTextView.getText().toString();
                            item.zip = Integer.parseInt(zipCode);
                        }
                        try {
                            if(item.zip>0 && item.zip <=100000){
                                new DemographicsURLParserHelper().execute(item);
                            }
                            else{
                                Toast.makeText(getActivity(), "Please Enter proper zip code to get started",
                                        Toast.LENGTH_SHORT).show();
                                getZipCodeTextView.setText("");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), "An Error Occurred. Please Try Again.",
                        Toast.LENGTH_SHORT).show();
            }
        }

        /*----------Method to create an AlertBox ------------- */
        protected void alertbox() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Your Device's GPS is Disable")
                    .setCancelable(false)
                    .setTitle("** Gps Status **")
                    .setPositiveButton("Gps On",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // finish the current activity
                                    // AlertBoxAdvance.this.finish();
                                    Intent myIntent = new Intent(
                                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivity(myIntent);
                                    dialog.cancel();
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // cancel the dialog box
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }

        private class DemographicsURLParserHelper extends AsyncTask<LocalDemoGraphicsItem, Void, LocalDemoGraphicsItem> {
            public String getDemographicsApi = "http://www.zillow.com/webservice/GetDemographics.htm?zws-id=X1-ZWz1dyt87mjle3_9fxlx&zip={0}";

            @Override
            protected LocalDemoGraphicsItem doInBackground(LocalDemoGraphicsItem... params) {
                try {
                    String url;
                    if (params[0].zip != 0) {
                        String zip = Integer.toString(params[0].zip);
                        //Lets make sure the zip code is 5 letters to address east code for ex. Newark, NJ 07103
                        if((params[0].zip < 10000) && (params[0].zip > 999)) {
                            zip = "0" + zip;
                        }
                        url = MessageFormat.format(getDemographicsApi, new Object[]{zip});
                        DemographicsSaxHandler handler = new DemographicsSaxHandler();
                        URLParserHelper.parseWithRetry(url, handler, "GetDemographics");
                        item = handler.getLocalDemoGraphicsItem();
                        return item;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(LocalDemoGraphicsItem result) {
                TextView textView = (TextView) getActivity().findViewById(id.showResultTextView);
                StringBuilder strBuilder = new StringBuilder();
                if (item.code == 0) {
                    strBuilder.append("Success");
                    Intent intent = new Intent(getActivity().getApplicationContext(), DemographicsDetailTabbedActivity.class);
                    intent.putExtra("LocalDemoGraphicsItem", item);
                    startActivity(intent);
                } else  {
                    if(item.text!=null)
                        strBuilder.append(item.text);
                    else
                        strBuilder.append(getResources().getString(string.zillow_error_code_one));
                }
                textView.setText(strBuilder);
                super.onPostExecute(result);
            }
        }
    }
}
