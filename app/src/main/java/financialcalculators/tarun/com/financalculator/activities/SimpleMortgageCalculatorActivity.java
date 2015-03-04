package financialcalculators.tarun.com.financalculator.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
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
import financialcalculators.tarun.com.financalculator.helper.pojos.MonthlyPaymentItem;
import financialcalculators.tarun.com.financalculator.helper.SAXHandler.MonthlyPaymentSAXHandler;
import financialcalculators.tarun.com.financalculator.helper.URLHelper.URLParserHelper;

/**
 * Created by Tarun on 12/17/2014.
 */
public class SimpleMortgageCalculatorActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simplemortcalc);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_simplemortcalc
                            , new SimpleMortgageCalcFragment())
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

    public static class SimpleMortgageCalcFragment extends Fragment implements
            View.OnClickListener {
        public MonthlyPaymentItem item = null;
        Button calcButton = null;

        public SimpleMortgageCalcFragment() {
            calcButton = null;
            item = new MonthlyPaymentItem();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            return inflater.inflate(R.layout.fragment_simplemortgage, container, false);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceBundle) {
            super.onActivityCreated(savedInstanceBundle);
            calcButton = (Button) getActivity().findViewById(R.id.calculateButton);
            calcButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            TextView getBasePriceTextView = (TextView) getActivity().findViewById(
                    R.id.editTextView);
            TextView getDollarDownTextView = (TextView) getActivity().findViewById(R.id.editTextView2);
            TextView getZipCodeTextView = (TextView) getActivity().findViewById(R.id.editTextView3);
            try{

            switch (v.getId()) {
                case R.id.calculateButton:

                    //Refreshing the Item at each Calculate Button Click
                    item = new MonthlyPaymentItem();

                    if (getBasePriceTextView != null && !getBasePriceTextView.getText().toString().isEmpty()) {
                        String baseMortgagePrice = getBasePriceTextView.getText().toString();
                        item.setPrice(Integer.parseInt(baseMortgagePrice));
                    }
                    if (getDollarDownTextView != null && !getDollarDownTextView.getText().toString().isEmpty()) {
                        String dollarDownPrice = getDollarDownTextView.getText().toString();
                        item.setDownPercentage(Integer.parseInt(dollarDownPrice));
                    }
                    if (getZipCodeTextView != null && !getZipCodeTextView.getText().toString().isEmpty()) {
                        String zipCode = getZipCodeTextView.getText().toString();
                        item.setZipCode(Integer.parseInt(zipCode));
                    }

                    try {
                        if(item.getPrice()>0){
                            new ZillowURLParserHelper().execute(item);
                        }
                        else{
                            Toast.makeText(getActivity(), "Please Enter Base Price to get started",
                                    Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    break;
            }
            }catch(NumberFormatException e){
                if (getBasePriceTextView != null && !getBasePriceTextView.getText().toString().isEmpty()) {
                    getBasePriceTextView.setText("");
                }
                if (getDollarDownTextView != null && !getDollarDownTextView.getText().toString().isEmpty()) {
                    getDollarDownTextView.setText("");
                }
                if (getZipCodeTextView != null && !getZipCodeTextView.getText().toString().isEmpty()) {
                    getZipCodeTextView.setText("");
                }
                Toast.makeText(getActivity(), "Number should be less than 2,147,483,647",
                        Toast.LENGTH_SHORT).show();
            }
        }


        private class ZillowURLParserHelper extends AsyncTask<MonthlyPaymentItem, Void, MonthlyPaymentItem> {


            public String getMonthlyPaymentApi = "http://www.zillow.com/webservice/GetMonthlyPayments.htm?zws-id=X1-ZWz1dyt87mjle3_9fxlx&price={0}";
            public String percentageDownApi = "&down={1}";
            public String zipCodeApi = "&zip=";

            @Override
            protected MonthlyPaymentItem doInBackground(MonthlyPaymentItem... params) {
                try {
                    boolean isZipCodePresent = false;
                    boolean isDollarDownPresent = false;
                    if(params[0].getDownPercentage()!=0 && params[0].getDownPercentage()<=100){
                        isDollarDownPresent = true;
                        getMonthlyPaymentApi+=percentageDownApi;
                    }
                    if(params[0].getZipCode()!=0){
                        isZipCodePresent = true;
                        getMonthlyPaymentApi+=zipCodeApi;
                        if(isDollarDownPresent){
                            getMonthlyPaymentApi+="{2}";
                        } else{
                            getMonthlyPaymentApi+="{1}";
                        }
                    }
                    String url;
                    if(!isDollarDownPresent && !isZipCodePresent){
                        url = MessageFormat.format(getMonthlyPaymentApi, new Object[]{
                                Integer.toString(params[0].getPrice())});
                    }
                    else if(isDollarDownPresent && !isZipCodePresent){
                        url = MessageFormat.format(getMonthlyPaymentApi, new Object[]{
                                Integer.toString(params[0].getPrice()), Integer.toString(params[0].getDownPercentage())});
                    }
                    else if(!isDollarDownPresent && isZipCodePresent){
                        url = MessageFormat.format(getMonthlyPaymentApi, new Object[]{
                                Integer.toString(params[0].getPrice()), Integer.toString(params[0].getZipCode())});
                    }
                    else {
                        url = MessageFormat.format(getMonthlyPaymentApi, new Object[]{
                                Integer.toString(params[0].getPrice()), Integer.toString(params[0].getDownPercentage()), Integer.toString(params[0].getZipCode())});
                    }

                    MonthlyPaymentSAXHandler handler = new MonthlyPaymentSAXHandler();
                    URLParserHelper.parseWithRetry(url, handler, "GetMonthlyPayments");
                    item = handler.getPaymentItem();
                    return item;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }


            @Override
            protected void onPostExecute(MonthlyPaymentItem result) {
                TextView textView = (TextView) getActivity().findViewById(R.id.showResultTextView);
                StringBuilder strBuilder = new StringBuilder();
                if(item.getCode()==0) {
                    strBuilder.append("$ Down Payment: $").append(Integer.toString(item.getDownPayment())).append("\n");
                    strBuilder.append("30 Yr Fixed Rate: ").append(Float.toString(item.getThirtyYearRate())).append("%").append("\n");
                    if (item.getThirtyYearMonthlyPAndI() != 0) {
                        strBuilder.append("30 Yr Principal & Interest: $").append(Integer.toString(item.getThirtyYearMonthlyPAndI())).append("\n");
                    }
                    if (item.getThirtyYearPMI() != 0) {
                        strBuilder.append("30 Yr PMI: $").append(Float.toString(item.getThirtyYearPMI())).append("\n");
                    }
                    strBuilder.append("15 Yr Fixed Rate: ").append(Float.toString(item.getFifteenYearRate())).append("%").append("\n");
                    if (item.getFifteenYearMonthlyPAndI() != 0) {
                        strBuilder.append("15 Yr Principal & Interest: $").append(Integer.toString(item.getFifteenYearMonthlyPAndI())).append("\n");
                    }
                    if (item.getFifteenYearPMI() != 0) {
                        strBuilder.append("15 Yr PMI: $").append(Float.toString(item.getFifteenYearPMI())).append("\n");
                    }
                    strBuilder.append("5 Yr ARM Rate: " ).append(Float.toString(item.getFiveOneArmRate())).append("%").append("\n");
                    if (item.getFiveOneArmMonthlyPAndI() != 0) {
                        strBuilder.append("5/1 Principal & Interest: $").append(Integer.toString(item.getFiveOneArmMonthlyPAndI())).append("\n");
                    }
                    if (item.getFiveOneArmPMI() != 0) {
                        strBuilder.append("5 Yr ARM PMI: $").append(Float.toString(item.getFiveOneArmPMI())).append("\n");
                    }
                    if (item.getMonthlyPropTaxes() != 0) {
                        strBuilder.append("Monthly Taxes: $").append(Integer.toString(item.getMonthlyPropTaxes())).append("\n");
                    }
                    if (item.getMonthlyHazardInsurance() != 0) {
                        strBuilder.append("Monthly Hazard Ins.: $").append( Integer.toString(item.getMonthlyHazardInsurance())).append("\n");
                    }
                } else if (item.getCode() == 1) {
                    strBuilder.append(getResources().getString(R.string.zillow_error_code_one));
                } else if (item.getCode() == 2) {
                    strBuilder.append(getResources().getString(R.string.zillow_error_code_two));
                } else if (item.getCode() == 3) {
                    strBuilder.append(getResources().getString(R.string.zillow_error_code_three));
                } else if (item.getCode() == 4) {
                    strBuilder.append(getResources().getString(R.string.zillow_error_code_four));
                } else if (item.getCode() == 500) {
                    strBuilder.append(getResources().getString(R.string.zillow_error_code_fivehundred));
                } else if (item.getCode() == 501) {
                    strBuilder.append(getResources().getString(R.string.zillow_error_code_fivehundredone));
                } else if (item.getCode() == 502) {
                    strBuilder.append(getResources().getString(R.string.zillow_error_code_fivehundredtwo));
                } else if (item.getCode() == 503) {
                    strBuilder.append(getResources().getString(R.string.zillow_error_code_fivehundredthree));
                } else if (item.getCode() == 505) {
                    strBuilder.append(getResources().getString(R.string.zillow_error_code_fivehundredfive));
                }
                textView.setText(strBuilder);
                super.onPostExecute(result);

            }
        }
    }
}
