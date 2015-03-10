package financialcalculators.tarun.com.financalculator.helper.pojos;

import java.io.Serializable;

/**
 * Created by Tarun on 3/3/2015.
 */
public class Link  implements Serializable {
    private String affordability;
    private String homesandrealestate;
    private String people;
    private String forSale;
    private String forSaleByOwner;
    private String foreclosures;
    private String recentlySold;

    public String getForeclosures(){
        return foreclosures;
    }

    public void setForeclosures(String foreclosures){
        this.foreclosures = foreclosures;
    }

    public String getAffordability() {
        return affordability;
    }

    public void setAffordability(String affordability) {
        this.affordability = affordability;
    }

    public String getHomesandrealestate() {
        return homesandrealestate;
    }

    public void setHomesandrealestate(String homesandrealestate) {
        this.homesandrealestate = homesandrealestate;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getForSale() {
        return forSale;
    }

    public void setForSale(String forSale) {
        this.forSale = forSale;
    }

    public String getForSaleByOwner() {
        return forSaleByOwner;
    }

    public void setForSaleByOwner(String forSaleByOwner) {
        this.forSaleByOwner = forSaleByOwner;
    }

    public String getRecentlySold() {
        return recentlySold;
    }

    public void setRecentlySold(String recentlySold) {
        this.recentlySold = recentlySold;
    }
}
