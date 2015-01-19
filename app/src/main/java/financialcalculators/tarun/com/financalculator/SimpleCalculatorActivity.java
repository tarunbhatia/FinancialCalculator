package financialcalculators.tarun.com.financalculator;

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
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Tarun on 12/17/2014.
 */
public class SimpleCalculatorActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_calc, new SimpleCalcFragment())
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
    public static class SimpleCalcFragment extends Fragment implements
            View.OnClickListener {
        Button oneButton = null;
        Button twoButton = null;
        Button threeButton = null;
        Button fourButton = null;
        Button fiveButton = null;
        Button sixButton = null;
        Button sevenButton = null;
        Button eightButton = null;
        Button nineButton = null;
        Button zeroButton = null;
        Button plusButton = null;
        Button minusButton = null;
        Button equalButton = null;
        Button clearButton = null;
        Button divButton = null;
        Button removeButton = null;
        Button multiplyButton = null;

        public SimpleCalcFragment() {
            oneButton = twoButton = threeButton = fourButton = fiveButton = sixButton = null;
            sevenButton = eightButton = nineButton = zeroButton = plusButton = minusButton = equalButton = clearButton = null;
            divButton = removeButton = multiplyButton = null;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceBundle) {
            super.onActivityCreated(savedInstanceBundle);
            oneButton = (Button) getActivity().findViewById(R.id.button1);
            oneButton.setOnClickListener(this);

            twoButton = (Button) getActivity().findViewById(R.id.button2);
            twoButton.setOnClickListener(this);

            threeButton = (Button) getActivity().findViewById(R.id.button3);
            threeButton.setOnClickListener(this);

            fourButton = (Button) getActivity().findViewById(R.id.button4);
            fourButton.setOnClickListener(this);

            fiveButton = (Button) getActivity().findViewById(R.id.button5);
            fiveButton.setOnClickListener(this);

            sixButton = (Button) getActivity().findViewById(R.id.button6);
            sixButton.setOnClickListener(this);

            sevenButton = (Button) getActivity().findViewById(R.id.button7);
            sevenButton.setOnClickListener(this);

            eightButton = (Button) getActivity().findViewById(R.id.button8);
            eightButton.setOnClickListener(this);

            nineButton = (Button) getActivity().findViewById(R.id.button9);
            nineButton.setOnClickListener(this);

            zeroButton = (Button) getActivity().findViewById(R.id.button0);
            zeroButton.setOnClickListener(this);

            plusButton = (Button) getActivity().findViewById(R.id.buttonPlus);
            plusButton.setOnClickListener(this);

            minusButton = (Button) getActivity().findViewById(R.id.buttonMinus);
            minusButton.setOnClickListener(this);

            multiplyButton = (Button) getActivity().findViewById(R.id.buttonMulti);
            multiplyButton.setOnClickListener(this);

            divButton = (Button) getActivity().findViewById(R.id.buttonDiv);
            divButton.setOnClickListener(this);

            clearButton = (Button) getActivity().findViewById(R.id.buttonClear);
            clearButton.setOnClickListener(this);

            removeButton = (Button) getActivity().findViewById(R.id.buttonRemove);
            removeButton.setOnClickListener(this);

            equalButton = (Button) getActivity().findViewById(R.id.buttonEqual);
            equalButton.setOnClickListener(this);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_simplecalc, container, false);
        }
        @Override
        public void onClick(View v) {
            Float num1, num2, result = (float) 0;
            TextView firstInput = (TextView) getActivity().findViewById(
                    R.id.firstInput);
            TextView secondInput = (TextView) getActivity().findViewById(
                    R.id.secondInput);
            TextView curOperator = (TextView) getActivity().findViewById(
                    R.id.currentOperatorTextView);
            switch (v.getId()) {

                case R.id.button1:
                    secondInput.setText(secondInput.getText() + "1");
                    break;
                case R.id.button2:
                    secondInput.setText(secondInput.getText() + "2");
                    break;
                case R.id.button3:
                    secondInput.setText(secondInput.getText() + "3");
                    break;
                case R.id.button4:
                    secondInput.setText(secondInput.getText() + "4");
                    break;
                case R.id.button5:
                    secondInput.setText(secondInput.getText() + "5");
                    break;
                case R.id.button6:
                    secondInput.setText(secondInput.getText() + "6");
                    break;
                case R.id.button7:
                    secondInput.setText(secondInput.getText() + "7");
                    break;
                case R.id.button8:
                    secondInput.setText(secondInput.getText() + "8");
                    break;
                case R.id.button9:
                    secondInput.setText(secondInput.getText() + "9");
                    break;
                case R.id.button0:
                    secondInput.setText(secondInput.getText() + "0");
                    break;
                case R.id.buttonRemove:
                    if (secondInput.getText().length() > 0) {
                        if (secondInput.getText().length() == 1) {
                            secondInput.setText("");
                        } else if (secondInput.getText().toString().endsWith(".0")) {
                            secondInput
                                    .setText(secondInput
                                            .getText()
                                            .toString()
                                            .substring(
                                                    0,
                                                    secondInput.getText().toString()
                                                            .length() - 3));
                        } else {

                            secondInput
                                    .setText(secondInput
                                            .getText()
                                            .toString()
                                            .substring(
                                                    0,
                                                    secondInput.getText().toString()
                                                            .length() - 1));
                        }
                    }
                    break;
                case R.id.buttonPlus:
                    if (secondInput.getText().length() == 0) {
                        Toast.makeText(getActivity(), "Need more Information",
                                Toast.LENGTH_SHORT).show();
                        curOperator.setText("+");
                        break;
                    }

                    // If this is the first input, push it to the first Text Window
                    // Clean up the second text window
                    if (firstInput.getText().length() == 0) {
                        firstInput.setText(secondInput.getText());
                        secondInput.setText("");
                    }
                    curOperator.setText("+");
                    break;
                case R.id.buttonMinus:
                    if (secondInput.getText().length() == 0) {
                        Toast.makeText(getActivity(), "Need more Information",
                                Toast.LENGTH_SHORT).show();
                        curOperator.setText("-");
                        break;
                    }

                    // If this is the first input, push it to the first Text Window
                    // Clean up the second text window
                    if (firstInput.getText().length() == 0) {
                        firstInput.setText(secondInput.getText());
                        secondInput.setText("");
                    }
                    curOperator.setText("-");
                    break;
                case R.id.buttonMulti:
                    if (secondInput.getText().length() == 0) {
                        Toast.makeText(getActivity(), "Need more Information",
                                Toast.LENGTH_SHORT).show();
                        curOperator.setText("*");
                        break;
                    }

                    // If this is the first input, push it to the first Text Window
                    // Clean up the second text window
                    if (firstInput.getText().length() == 0) {
                        firstInput.setText(secondInput.getText());
                        secondInput.setText("");
                    }
                    curOperator.setText("*");
                    break;
                case R.id.buttonDiv:
                    if (secondInput.getText().length() == 0) {
                        Toast.makeText(getActivity(), "Need more Information",
                                Toast.LENGTH_SHORT).show();
                        curOperator.setText("/");
                        break;
                    }

                    // If this is the first input, push it to the first Text Window
                    // Clean up the second text window
                    if (firstInput.getText().length() == 0) {
                        firstInput.setText(secondInput.getText());
                        secondInput.setText("");
                    }
                    curOperator.setText("/");
                    break;
                case R.id.buttonClear:
                    secondInput.setText("");
                    firstInput.setText("");
                    curOperator.setText("");
                    break;
                case R.id.buttonEqual:
                    try {
                        if (firstInput.getText().toString().length() != 0
                                && secondInput.getText().toString().length() != 0
                                && curOperator.getText().toString().length() != 0) {

                            num1 = Float.parseFloat(firstInput.getText().toString());
                            num2 = Float.parseFloat(secondInput.getText().toString());
                            if (curOperator.getText().equals("+")) {
                                result = num1 + num2;
                                secondInput.setText(result.toString());
                                firstInput.setText("");
                                curOperator.setText("");
                            } else if (curOperator.getText().equals("-")) {
                                result = num1 - num2;
                                secondInput.setText(result.toString());
                                firstInput.setText("");
                                curOperator.setText("");
                            } else if (curOperator.getText().equals("*")) {
                                result = num1 * num2;
                                secondInput.setText(result.toString());
                                firstInput.setText("");
                                curOperator.setText("");
                            } else if (curOperator.getText().equals("/")) {
                                result = num1 / num2;
                                secondInput.setText(result.toString());
                                firstInput.setText("");
                                curOperator.setText("");
                            }
                        }
                        break;
                    } catch (Exception e) {
                        Toast.makeText(getActivity(),
                                "Improper Use of Simple Calculator", Toast.LENGTH_SHORT)
                                .show();
                        firstInput.setText("");
                        curOperator.setText("");
                        secondInput.setText("");
                    }
                default:
                    break;
            }
        }
    }
}
