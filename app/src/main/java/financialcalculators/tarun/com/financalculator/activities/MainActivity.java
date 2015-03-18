package financialcalculators.tarun.com.financalculator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import financialcalculators.tarun.com.financalculator.R;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
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
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements
            View.OnClickListener {
        TextView firstOption, secondOption, thirdOption = null;
        TextView firstOptionSub, secondOptionSub, thirdOptionSub = null;
        public PlaceholderFragment() {
            firstOption = secondOption = thirdOption= null;
            firstOptionSub = secondOptionSub = thirdOptionSub = null;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            return inflater.inflate(R.layout.fragment_main, container, false);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceBundle) {
            super.onActivityCreated(savedInstanceBundle);
            firstOption = (TextView) getActivity().findViewById(R.id.MainActivityFirstTextView);
            firstOption.setOnClickListener(this);

            firstOptionSub = (TextView)getActivity().findViewById(R.id.MainActivityFirstTextSubView);
            firstOptionSub.setOnClickListener(this);

            secondOption = (TextView) getActivity().findViewById(R.id.MainActivitySecondTextView);
            secondOption.setOnClickListener(this);

            secondOptionSub = (TextView) getActivity().findViewById(R.id.MainActivitySecondTextSubView);
            secondOptionSub.setOnClickListener(this);

            thirdOption = (TextView) getActivity().findViewById(R.id.MainActivityThirdTextView);
            thirdOption.setOnClickListener(this);

            thirdOptionSub = (TextView) getActivity().findViewById(R.id.MainActivityThirdTextSubView);
            thirdOptionSub.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.MainActivityFirstTextView:
                case R.id.MainActivityFirstTextSubView:
                case R.id.MainActivityFirstImageView:

                    Intent intent = new Intent(getActivity()
                            .getApplicationContext(), SimpleCalculatorActivity.class);
                    startActivity(intent);
                    break;

                case R.id.MainActivitySecondImageView:
                case R.id.MainActivitySecondTextSubView:
                case R.id.MainActivitySecondTextView:

                    Intent intent2 = new Intent(getActivity().getApplicationContext(),SimpleMortgageCalculatorActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.MainActivityThirdImageView:
                case R.id.MainActivityThirdTextView:
                case R.id.MainActivityThirdTextSubView:
                    Intent intent3 = new Intent (getActivity().getApplicationContext(),DemographicsActivity.class);
                    startActivity(intent3);
                default:
                    break;
            }
        }
    }
}
