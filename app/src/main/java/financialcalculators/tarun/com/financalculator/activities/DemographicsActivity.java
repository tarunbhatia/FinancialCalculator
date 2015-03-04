package financialcalculators.tarun.com.financalculator.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;

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
        public LocalDemoGraphicsItem item = null;
        //Public constructor
        //TODO declare obj
        public DemographicsFragment() {
            calculateButton = null;
            gpsEnableButton = null;
            item = new LocalDemoGraphicsItem();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(layout.fragment_demographics, container, false);
            return rootView;
        }

        //TODO init objects
        @Override
        public void onActivityCreated(Bundle savedInstanceBundle) {
            super.onActivityCreated(savedInstanceBundle);
            gpsEnableButton = (Button) getActivity().findViewById(id.GPSButton);
            gpsEnableButton.setOnClickListener(this);
            calculateButton = (Button) getActivity().findViewById(id.calculateButton);
            calculateButton.setOnClickListener(this);
        }

        //TODO implement obj functionality
        @Override
        public void onClick(View v) {
            TextView getZipCodeTextView = (TextView) getActivity().findViewById(id.editTextViewZip);

            try {
                switch (v.getId()) {
                    case id.calculateButton:
                        if (getZipCodeTextView != null && !getZipCodeTextView.getText().toString().isEmpty()) {
                            String zipCode = getZipCodeTextView.getText().toString();
                            item.zip = Integer.parseInt(zipCode);
                        }
                        try {
                            if(item.zip>0){
                                new DemographicsURLParserHelper().execute(item);
                            }
                            else{
                                Toast.makeText(getActivity(), "Please Enter zip code to get started",
                                        Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), "An Error Occurred. Please Try Again.",
                        Toast.LENGTH_SHORT).show();
            }
        }

        private class DemographicsURLParserHelper extends AsyncTask<LocalDemoGraphicsItem, Void, LocalDemoGraphicsItem> {
            public String getDemographicsApi = "http://www.zillow.com/webservice/GetDemographics.htm?zws-id=X1-ZWz1dyt87mjle3_9fxlx&zip={0}";

            @Override
            protected LocalDemoGraphicsItem doInBackground(LocalDemoGraphicsItem... params) {
                try {
                    String url;
                    if (params[0].zip != 0) {
                        url = MessageFormat.format(getDemographicsApi, new Object[]{
                                Integer.toString(params[0].zip)});
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
                    startActivity(intent);
                } else  {
                    if(item.text!=null)
                        strBuilder.append(item.text);
                    else
                        strBuilder.append(getResources().getString(string.zillow_error_code_one));
                }
                Log.v("TEST", strBuilder.toString() + " Code: "+ item.code + " zip:" + item.zip);
                textView.setText(strBuilder);
                super.onPostExecute(result);
            }
        }
    }
}
