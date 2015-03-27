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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import financialcalculators.tarun.com.financalculator.R;

public class TipCalculatorActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new TipCalculatorFragment())
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
    public static class TipCalculatorFragment extends Fragment implements
            View.OnClickListener{
        Button tipMinusButton, tipPlusButton, splitMinusButton, splitPlusButton = null;
        EditText billAmtTextView, tipAmtTextView, splitAmtTextView = null;

        public TipCalculatorFragment() {
            tipMinusButton = tipPlusButton = splitMinusButton = splitPlusButton = null;
            billAmtTextView = tipAmtTextView = null;
            splitAmtTextView = null;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_tip_calculator, container, false);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceBundle) {
            super.onActivityCreated(savedInstanceBundle);
            tipMinusButton = (Button) getActivity().findViewById(R.id.tipButtonMinus);
            tipMinusButton.setOnClickListener(this);

            tipPlusButton = (Button) getActivity().findViewById(R.id.tipButtonPlus);
            tipPlusButton.setOnClickListener(this);

            splitMinusButton = (Button) getActivity().findViewById(R.id.buttonSplitNegative);
            splitMinusButton.setOnClickListener(this);

            splitPlusButton = (Button) getActivity().findViewById(R.id.buttonSplitPositive);
            splitPlusButton.setOnClickListener(this);

            tipAmtTextView = (EditText) getActivity().findViewById(R.id.tipPercEditTextView);
            billAmtTextView = (EditText) getActivity().findViewById(R.id.billEditTextView);
            billAmtTextView.setOnClickListener(this);

            splitAmtTextView = (EditText) getActivity().findViewById(R.id.splitPercTextView);

        }

        @Override
        public void onClick(View v) {
            String tipAmtString, splitAmtString;
            Integer tipAmtInt, splitAmtInt;
            try {
                switch (v.getId()) {
                    case R.id.tipButtonMinus:
                        tipAmtString = tipAmtTextView.getText().toString();
                        if(!tipAmtString.isEmpty()) {
                            tipAmtInt = Integer.parseInt(tipAmtString);
                            if (tipAmtInt > 0) {
                                tipAmtInt--;
                                tipAmtTextView.setText(tipAmtInt.toString());
                            }
                        }
                        calculateAndUpdateTips();
                        break;
                    case R.id.tipButtonPlus :
                        tipAmtString = tipAmtTextView.getText().toString();
                        if(!tipAmtString.isEmpty()){
                            tipAmtInt = Integer.parseInt(tipAmtString);
                        } else{
                            tipAmtInt = 0;
                        }
                        tipAmtInt++;
                        tipAmtTextView.setText(tipAmtInt.toString());
                        calculateAndUpdateTips();
                        break;
                    case R.id.buttonSplitNegative:
                        splitAmtString = splitAmtTextView.getText().toString();
                        if(!splitAmtString.isEmpty()) {
                            splitAmtInt = Integer.parseInt(splitAmtString);
                            if (splitAmtInt > 0) {
                                splitAmtInt--;
                                splitAmtTextView.setText(splitAmtInt.toString());
                            }
                        }
                        calculateAndUpdateTips();
                        break;
                    case R.id.buttonSplitPositive:
                        splitAmtString = splitAmtTextView.getText().toString();
                        if(!splitAmtString.isEmpty()){
                            splitAmtInt = Integer.parseInt(splitAmtString);
                        }
                        else{
                            splitAmtInt = 0;
                        }
                        splitAmtInt++;
                        splitAmtTextView.setText(splitAmtInt.toString());
                        calculateAndUpdateTips();
                        break;
                    case R.id.billEditTextView:
                        calculateAndUpdateTips();
                        break;
                }

            } catch (Exception e) {
                Toast.makeText(getActivity(), "An Error Occurred. Please Try Again.",
                        Toast.LENGTH_SHORT).show();
            }
        }

        private void calculateAndUpdateTips() {
            //This is where the magic happens
            if(!billAmtTextView.getText().toString().isEmpty()){
                Float totalTipAmt, totalCheck, eachTipAmount = null, eachPay = null, billAmt;
                billAmt = Float.parseFloat(billAmtTextView.getText().toString());

                if(tipAmtTextView.getText().toString().equals("0")){
                    totalTipAmt = Float.valueOf(0);
                    eachTipAmount = Float.valueOf(0);
                }
                else {
                    totalTipAmt = (billAmt * Float.parseFloat(tipAmtTextView.getText().toString())) / 100;
                }
                if(!splitAmtTextView.getText().toString().isEmpty() && !splitAmtTextView.getText().toString().equals("0")) {
                    if(totalTipAmt!=0){
                        eachTipAmount = totalTipAmt / Float.parseFloat(splitAmtTextView.getText().toString());
                    }
                    else{
                        eachTipAmount = Float.valueOf(0);
                    }
                    eachPay = eachTipAmount + (billAmt/Float.parseFloat(splitAmtTextView.getText().toString()));
                }
                totalCheck = totalTipAmt + billAmt;
                if(totalCheck!=0){
                    TextView totalCheckResultTextView = (TextView) getActivity().findViewById(R.id.totalCheckResultTextView);
                    totalCheckResultTextView.setText(totalCheck.toString());
                }
                if(eachPay!=0) {
                    TextView eachPayResultTextView = (TextView) getActivity().findViewById(R.id.eachPayResultTextView);
                    eachPayResultTextView.setText(eachPay.toString());
                }
                TextView totalTipResultTextView = (TextView) getActivity().findViewById(R.id.totalTipResultTextView);
                totalTipResultTextView.setText(totalTipAmt.toString());
                TextView eachTipResultTextView = (TextView) getActivity().findViewById(R.id.eachTipResultTextView);
                eachTipResultTextView.setText(eachTipAmount.toString());

            }
        }
    }
}
