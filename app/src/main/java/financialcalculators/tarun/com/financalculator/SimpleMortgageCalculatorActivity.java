package financialcalculators.tarun.com.financalculator;

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

import java.text.MessageFormat;

import financialcalculators.tarun.com.financalculator.helper.MonthlyPaymentItem;
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
                            , new PlaceholderFragment())
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment implements
            View.OnClickListener {
        public MonthlyPaymentItem item = null;


        Button calcButton = null;

        public PlaceholderFragment() {
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
            switch (v.getId()) {
                case R.id.calculateButton:
                    TextView getBasePriceTextView = (TextView) getActivity().findViewById(
                            R.id.editTextView);
                    //Refreshing the Item at each Calculate Button Click
                    item = new MonthlyPaymentItem();

                    TextView getDollarDownTextView = (TextView) getActivity().findViewById(R.id.editTextView2);
                    TextView getZipCodeTextView = (TextView) getActivity().findViewById(R.id.editTextView3);

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
                        new ZillowURLParserHelper().execute(item);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    break;
            }
        }


        private class ZillowURLParserHelper extends AsyncTask<MonthlyPaymentItem, Void, MonthlyPaymentItem> {


            public String getMonthlyPaymentApi = "http://www.zillow.com/webservice/GetMonthlyPayments.htm?zws-id=X1-ZWz1dyt87mjle3_9fxlx&price={0}";
            public String percentageDownApi = "&down={1}";
            public String dollardsDownApi = "&dollarsDown{1}";
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
                    String url = "";
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
                strBuilder.append("$ Down Payment: $" + Integer.toString(item.getDownPayment()) + "\n");
                strBuilder.append("30 Yr Fixed Rate: " + Float.toString(item.getThirtyYearRate()) + "%" + "\n");
                if(item.getThirtyYearMonthlyPAndI()!=0){
                    strBuilder.append("30 Yr Principal & Interest: $" + Integer.toString(item.getThirtyYearMonthlyPAndI()) + "\n");
                }
                if(item.getThirtyYearPMI()!=0){
                    strBuilder.append("30 Yr PMI: $" + Float.toString(item.getThirtyYearPMI()) + "\n");
                }
                strBuilder.append("15 Yr Fixed Rate: " + Float.toString(item.getFifteenYearRate()) + "%" + "\n");
                if(item.getFifteenYearMonthlyPAndI()!=0){
                    strBuilder.append("15 Yr Principal & Interest: $" + Integer.toString(item.getFifteenYearMonthlyPAndI()) + "\n");
                }
                if(item.getFifteenYearPMI()!=0){
                    strBuilder.append("15 Yr PMI: $" + Float.toString(item.getFifteenYearPMI()) + "\n");
                }
                strBuilder.append("5 Yr ARM Rate: " + Float.toString(item.getFiveOneArmRate()) + "%" + "\n");
                if(item.getFiveOneArmMonthlyPAndI()!=0){
                    strBuilder.append("5/1 Principal & Interest: $" + Integer.toString(item.getFiveOneArmMonthlyPAndI()) + "\n");
                }
                if(item.getFiveOneArmPMI()!=0){
                    strBuilder.append("5 Yr ARM PMI: $" + Float.toString(item.getFiveOneArmPMI()) + "\n");
                }
                if(item.getMonthlyPropTaxes()!=0){
                    strBuilder.append("Monthly Taxes: $"+Integer.toString(item.getMonthlyPropTaxes()) + "\n");
                }
                if(item.getMonthlyHazardInsurance()!=0){
                    strBuilder.append("Monthly Hazard Ins.: $" + Integer.toString(item.getMonthlyHazardInsurance()) + "\n");
                }
                textView.setText(strBuilder);
                super.onPostExecute(result);

            }
        }
    }
}
