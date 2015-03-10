package financialcalculators.tarun.com.financalculator.helper.pojos;

import java.io.Serializable;

/**
 * Created by Tarun on 3/3/2015.
 */
public class Chart implements Serializable {
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
