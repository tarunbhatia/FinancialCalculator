package financialcalculators.tarun.com.financalculator.helper.SAXHandler;

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
    boolean fifteen, thirty, fiveArm = false;

    public MonthlyPaymentItem getPaymentItem(){
        return paymentItem;
    }

    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) {
        characters.delete(0, characters.length());
        if (localName.equals("payment loanType")) {
            if (getValue().equals("thirtyYearFixed")) {
                thirty = true;
                fifteen = false;
                fiveArm = false;
            } else if (getValue().equals("fifteenYearFixed")) {
                fifteen = true;
                thirty = false;
                fiveArm = false;
            } else if (getValue().equals("fiveOneARM")) {
                fiveArm = true;
                fifteen = false;
                thirty = false;
            }
        }
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
            if (thirty == true) {
                paymentItem.setThirtyYearRate(Integer.parseInt(getValue()));
            } else if (fifteen == true) {
                paymentItem.setFifteenYearRate(Integer.parseInt(getValue()));
            } else if (fiveArm == true) {
                paymentItem.setFiveOneArmRate(Integer.parseInt(getValue()));
            }
        } else if (localName.equals("monthlyPrincipalAndInterest")) {
            if (thirty == true) {
                paymentItem.setThirtyYearMonthlyPAndI(Integer
                        .parseInt(getValue()));
            } else if (fifteen == true) {
                paymentItem.setFifteenYearMonthlyPAndI(Integer
                        .parseInt(getValue()));
            } else if (fiveArm == true) {
                paymentItem.setFiveOneArmMonthlyPAndI(Integer
                        .parseInt(getValue()));
            }
        } else if (localName.equals("monthlyMortgageInsurance")) {
            if (thirty == true) {
                paymentItem.setThirtyYearPMI(Integer.parseInt(getValue()));
            } else if (fifteen == true) {
                paymentItem.setFifteenYearPMI(Integer.parseInt(getValue()));
            } else if (fiveArm == true) {
                paymentItem.setFiveOneArmPMI(Integer.parseInt(getValue()));
            }
        } else if (localName.equals("monthlyPropertyTaxes")) {
            paymentItem.setMonthlyPropTaxes(Integer.parseInt(getValue()));
        } else if (localName.equals("monthlyHazardInsurance")) {
            paymentItem.setMonthlyHazardInsurance(Integer.parseInt(getValue()));
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
