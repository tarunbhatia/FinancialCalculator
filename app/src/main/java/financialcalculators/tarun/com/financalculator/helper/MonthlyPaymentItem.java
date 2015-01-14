package financialcalculators.tarun.com.financalculator.helper;

/**
 * Created by Tarun on 12/17/2014.
 */
public class MonthlyPaymentItem {
    private int price;
    private int downPayment;
    private int downPercentage;
    private int zipCode;
    private int code;
    private float thirtyYearRate;
    private int thirtyYearMonthlyPAndI;
    private int thirtyYearPMI;
    private float fifteenYearRate;
    private int fifteenYearMonthlyPAndI;
    private int fifteenYearPMI;
    private float fiveOneArmRate;
    private int fiveOneArmMonthlyPAndI;
    private int fiveOneArmPMI;
    private int monthlyPropTaxes;
    private int monthlyHazardInsurance;

    public MonthlyPaymentItem() {
        this.setPrice(0);
        this.setDownPercentage(0);
        this.setDownPayment(0);
        this.setZipCode(0);
    }

    // Change to Int
    public void getMonthlyPaymentFromDownPercentageAndZip(int price,
                                                          int downPercentage, int zipCode) {
        this.setPrice(price);
        this.setDownPercentage(downPercentage);
        this.setZipCode(zipCode);
    }

    // Change to Int
    public void getMonthlyPaymentItemFromDownDollarsAndZip(int price,
                                                           int downPayment, int zipCode) {
        this.setPrice(price);
        this.setDownPayment(downPayment);
        this.setZipCode(zipCode);
    }

    // Change to Int
    public void getMonthlyPaymentItemFromDownDollarsAndNoZip(int price,
                                                             int downPayment) {
        this.setPrice(price);
        this.setDownPayment(downPayment);
    }

    // Change to Int
    public void getMonthlyPaymentItemFromDownPercentageAndNoZip(int price,
                                                                int downPayment) {
        this.setPrice(price);
        this.setDownPayment(downPayment);
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDownPercentage() {
        return downPercentage;
    }

    public void setDownPercentage(int downPercentage) {
        this.downPercentage = downPercentage;
    }

    public int getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(int downPayment) {
        this.downPayment = downPayment;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public float getThirtyYearRate() {
        return thirtyYearRate;
    }

    public void setThirtyYearRate(float thirtyYearRate) {
        this.thirtyYearRate = thirtyYearRate;
    }

    public int getThirtyYearMonthlyPAndI() {
        return thirtyYearMonthlyPAndI;
    }

    public void setThirtyYearMonthlyPAndI(int thirtyYearMonthlyPAndI) {
        this.thirtyYearMonthlyPAndI = thirtyYearMonthlyPAndI;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getThirtyYearPMI() {
        return thirtyYearPMI;
    }

    public void setThirtyYearPMI(int thirtyYearPMI) {
        this.thirtyYearPMI = thirtyYearPMI;
    }

    public float getFifteenYearRate() {
        return fifteenYearRate;
    }

    public void setFifteenYearRate(float fifteenYearRate) {
        this.fifteenYearRate = fifteenYearRate;
    }

    public int getFifteenYearMonthlyPAndI() {
        return fifteenYearMonthlyPAndI;
    }

    public void setFifteenYearMonthlyPAndI(int fifteenYearMonthlyPAndI) {
        this.fifteenYearMonthlyPAndI = fifteenYearMonthlyPAndI;
    }

    public int getFifteenYearPMI() {
        return fifteenYearPMI;
    }

    public void setFifteenYearPMI(int fifteenYearPMI) {
        this.fifteenYearPMI = fifteenYearPMI;
    }

    public float getFiveOneArmRate() {
        return fiveOneArmRate;
    }

    public void setFiveOneArmRate(float fiveOneArmRate) {
        this.fiveOneArmRate = fiveOneArmRate;
    }

    public int getFiveOneArmMonthlyPAndI() {
        return fiveOneArmMonthlyPAndI;
    }

    public void setFiveOneArmMonthlyPAndI(int fiveOneArmMonthlyPAndI) {
        this.fiveOneArmMonthlyPAndI = fiveOneArmMonthlyPAndI;
    }

    public int getFiveOneArmPMI() {
        return fiveOneArmPMI;
    }

    public void setFiveOneArmPMI(int fiveOneArmPMI) {
        this.fiveOneArmPMI = fiveOneArmPMI;
    }

    public int getMonthlyPropTaxes() {
        return monthlyPropTaxes;
    }

    public void setMonthlyPropTaxes(int monthlyPropTaxes) {
        this.monthlyPropTaxes = monthlyPropTaxes;
    }

    public int getMonthlyHazardInsurance() {
        return monthlyHazardInsurance;
    }

    public void setMonthlyHazardInsurance(int monthlyHazardInsurance) {
        this.monthlyHazardInsurance = monthlyHazardInsurance;
    }
}
