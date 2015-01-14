package financialcalculators.tarun.com.financalculator.helper.SAXHandler;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import financialcalculators.tarun.com.financalculator.helper.MonthlyPaymentItem;

/**
 * Created by Tarun on 12/17/2014.
 */
public class MonthlyPaymentSAXHandler extends DefaultHandler {
    private StringBuilder characters = new StringBuilder();
    private MonthlyPaymentItem paymentItem = new MonthlyPaymentItem();
    boolean fifteen, fiveArm = false;
    boolean thirty = true;
    int count = 0;

    public MonthlyPaymentItem getPaymentItem(){
        return paymentItem;
    }

    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) {
        characters.delete(0, characters.length());

    }

    public void endElement(String namespaceURI, String localName, String qName) {
        if (localName.equals("price")) {
            paymentItem.setPrice(Integer.parseInt(getValue()));
        } else if (localName.equals("down")) {
            paymentItem.setDownPercentage(Integer.parseInt(getValue()));
        } else if (localName.equals("zip")) {
            paymentItem.setZipCode(Integer.parseInt(getValue()));
        } else if (localName.equals("code")) {
            paymentItem.setCode(Integer.parseInt(getValue()));
        } else if (localName.equals("rate")) {
            count++;
            //The case where we don't get Monthly Mortgage Insurance (PMI)
            if(count == 2 && thirty){
                thirty = false;
                fifteen = true;
                fiveArm = false;
            } else if(count == 3 && fifteen){
                fifteen = false;
                fiveArm = true;
                thirty = true;
            }
            if (thirty) {
                paymentItem.setThirtyYearRate(Float.parseFloat(getValue()));
            } else if (fifteen) {
                paymentItem.setFifteenYearRate(Float.parseFloat(getValue()));
            } else if (fiveArm) {
                paymentItem.setFiveOneArmRate(Float.parseFloat(getValue()));
            }
        } else if (localName.equals("monthlyPrincipalAndInterest")) {
            if (thirty) {
                paymentItem.setThirtyYearMonthlyPAndI(Integer
                        .parseInt(getValue()));
                Log.v("TEST APP", "30 Year Monthly P&I: "+ paymentItem.getThirtyYearMonthlyPAndI());
            } else if (fifteen) {
                paymentItem.setFifteenYearMonthlyPAndI(Integer
                        .parseInt(getValue()));
            } else if (fiveArm) {
                paymentItem.setFiveOneArmMonthlyPAndI(Integer
                        .parseInt(getValue()));
            }
        } else if (localName.equals("monthlyMortgageInsurance")) {
            if (thirty) {
                paymentItem.setThirtyYearPMI(Integer.parseInt(getValue()));
                fifteen = true;
                thirty = false;
            } else if (fifteen) {
                paymentItem.setFifteenYearPMI(Integer.parseInt(getValue()));
                fifteen = false;
                fiveArm = true;
            } else if (fiveArm) {
                paymentItem.setFiveOneArmPMI(Integer.parseInt(getValue()));
                thirty = true;
                fifteen = false;
                fiveArm = false;
            }
        } else if (localName.equals("monthlyPropertyTaxes")) {
            paymentItem.setMonthlyPropTaxes(Integer.parseInt(getValue()));
        } else if (localName.equals("monthlyHazardInsurance")) {
            paymentItem.setMonthlyHazardInsurance(Integer.parseInt(getValue()));
        } else if(localName.equals("downPayment")){
            paymentItem.setDownPayment(Integer.parseInt(getValue()));
        }
    }

    protected String getValue() {
        return characters.toString().trim();
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        characters.append(ch, start, length);
    }
}
