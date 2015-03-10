package financialcalculators.tarun.com.financalculator.helper.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tarun on 3/3/2015.
 */
public class LocalDemoGraphicsItem implements Serializable {

    public int zip;
    public int code;
    public String text;
    public Link links;
    public List<Chart> charts;
    public Region region;
    public ArrayList<Page> pages;

    public LocalDemoGraphicsItem(){
        zip = 0;
        code = 0;
        text = "";
        links = new Link();
        charts = new ArrayList<Chart>();
        region = new Region();
        pages = new ArrayList<Page>();

    }

}
