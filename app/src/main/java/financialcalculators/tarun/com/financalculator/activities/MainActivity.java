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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

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
        TextView firstOption, secondOption, thirdOption, fourthOption = null;
        TextView firstOptionSub, secondOptionSub, thirdOptionSub, fourthOptionSub = null;
        ImageView firstImage, secondImage, thirdImage, fourthImage = null;
        public PlaceholderFragment() {
            firstOption = secondOption = thirdOption = fourthOption = null;
            firstOptionSub = secondOptionSub = thirdOptionSub = fourthOptionSub = null;
            firstImage = secondImage = thirdImage = fourthImage = null;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main, container, false);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceBundle) {
            super.onActivityCreated(savedInstanceBundle);

            //Code for adding adds
            AdView mAdView = (AdView) getActivity().findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().addTestDevice("618cd2d5").build();
            mAdView.loadAd(adRequest);

            firstOption = (TextView) getActivity().findViewById(R.id.MainActivityFirstTextView);
            firstOption.setOnClickListener(this);

            firstOptionSub = (TextView)getActivity().findViewById(R.id.MainActivityFirstTextSubView);
            firstOptionSub.setOnClickListener(this);

            firstImage = (ImageView) getActivity().findViewById(R.id.MainActivityFirstImageView);
            //firstImage.setImageResource(R.drawable.ic_launcher);
            firstImage.setOnClickListener(this);

            secondOption = (TextView) getActivity().findViewById(R.id.MainActivitySecondTextView);
            secondOption.setOnClickListener(this);

            secondOptionSub = (TextView) getActivity().findViewById(R.id.MainActivitySecondTextSubView);
            secondOptionSub.setOnClickListener(this);

            secondImage = (ImageView) getActivity().findViewById(R.id.MainActivitySecondImageView);
            //secondImage.setImageResource(R.drawable.ic_launcher);
            secondImage.setOnClickListener(this);

            thirdOption = (TextView) getActivity().findViewById(R.id.MainActivityThirdTextView);
            thirdOption.setOnClickListener(this);

            thirdOptionSub = (TextView) getActivity().findViewById(R.id.MainActivityThirdTextSubView);
            thirdOptionSub.setOnClickListener(this);

            thirdImage = (ImageView) getActivity().findViewById(R.id.MainActivityThirdImageView);
            //thirdImage.setImageResource(R.drawable.ic_launcher);
            thirdImage.setOnClickListener(this);

            fourthOption = (TextView) getActivity().findViewById(R.id.MainActivityFourthTextView);
            fourthOption.setOnClickListener(this);

            fourthOptionSub = (TextView) getActivity().findViewById(R.id.MainActivityFourthTextSubView);
            fourthOptionSub.setOnClickListener(this);

            fourthImage = (ImageView) getActivity().findViewById(R.id.MainActivityFourthImageView);
            //fourthImage.setImageResource(R.drawable.ic_launcher);
            fourthImage.setOnClickListener(this);
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
                    break;
                case R.id.MainActivityFourthTextView:
                case R.id.MainActivityFourthTextSubView:
                case R.id.MainActivityFourthImageView:
                    Intent intent4 = new Intent (getActivity().getApplicationContext(),TipCalculatorActivity.class);
                    startActivity(intent4);
                default:
                    break;
            }
        }
    }
}
