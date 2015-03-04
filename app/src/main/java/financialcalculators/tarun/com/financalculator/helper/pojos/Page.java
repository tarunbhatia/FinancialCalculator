package financialcalculators.tarun.com.financalculator.helper.pojos;

import java.util.List;

/**
 * Created by Tarun on 3/3/2015.
 */
public class Page {
    private String name;
    private List <Table> tables;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }
}
