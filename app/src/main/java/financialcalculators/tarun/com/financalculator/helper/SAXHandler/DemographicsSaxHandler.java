package financialcalculators.tarun.com.financalculator.helper.SAXHandler;

    import org.xml.sax.Attributes;
    import org.xml.sax.SAXException;
    import org.xml.sax.helpers.DefaultHandler;

    import financialcalculators.tarun.com.financalculator.helper.pojos.Link;
    import financialcalculators.tarun.com.financalculator.helper.pojos.LocalDemoGraphicsItem;
    import financialcalculators.tarun.com.financalculator.helper.pojos.Region;

/**
     * Created by Tarun on 3/3/2015.
     */
    public class DemographicsSaxHandler extends DefaultHandler {
        private StringBuilder characters = new StringBuilder();
        private LocalDemoGraphicsItem localDemoGraphicsItem = new LocalDemoGraphicsItem();
        public LocalDemoGraphicsItem getLocalDemoGraphicsItem(){
            return localDemoGraphicsItem;
        }
        Region region = null;
        Link link = null;
        public void startElement(String uri, String localName, String qName,
                             Attributes attributes) {
            if(localName.equals("region")){
                region = new Region();
            } else if(localName.equals("links")) {
                link = new Link();
            }

        characters.delete(0, characters.length());

    }

    //TODO finish this
    public void endElement(String namespaceURI, String localName, String qName) {
        if (localName.equals("text")) {
            localDemoGraphicsItem.text = getValue();
        } else if (localName.equals("code")) {
            localDemoGraphicsItem.code = Integer.parseInt(getValue());
        } else if(region!=null){
            if(localName.equals("id") ) {
                region.setRegionId(Integer.parseInt(getValue()));
            } else if(localName.equals("state")){
                region.setState(getValue());
            } else if(localName.equals("city")){
                region.setCity(getValue());
            } else if(localName.equals("zip")){
                region.setZipCode(Integer.parseInt(getValue()));
            } else if(localName.equals("latitude")){
                region.setLatitude(Float.parseFloat(getValue()));
            } else if(localName.equals("longitude")){
                region.setLongitude(Float.parseFloat(getValue()));
            } else if(localName.equals("zmmrateurl")){
                //Once you get the last value, push that to localDemoGraphicsItem and nullify it
                localDemoGraphicsItem.region = region;
                region = null;
            }
        } else if(link!=null){
            if(localName.equals("affordability")){
                link.setAffordability(getValue());
            } else if(localName.equals("homesandrealestate")) {
                link.setHomesandrealestate(getValue());
            } else if(localName.equals("people")){
                link.setPeople(getValue());
            } else if(localName.equals("forSale")){
                link.setForSale(getValue());
            } else if(localName.equals("forSaleByOwner")){
                link.setForSaleByOwner(getValue());
            } else if(localName.equals("foreclosures")){
                link.setForeclosures(getValue());
            } else if(localName.equals("recentlySold")){
                link.setRecentlySold(getValue());
                localDemoGraphicsItem.links = link;
                link = null;
            }

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
