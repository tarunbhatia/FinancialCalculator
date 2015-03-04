package financialcalculators.tarun.com.financalculator.helper.pojos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tarun on 3/3/2015.
 */
public class Table {

    private String name;
    private List<Attribute> attributeList;

    public Table (){
        name = "";
        attributeList = new ArrayList<>();
    }
    public Table(String name, List<Attribute> attributeList) {
        this.name = name;
        this.attributeList = attributeList;
    }

    class Attribute {
        private String name;
        private int zipValue;
        private int nationValue;
    }

}
