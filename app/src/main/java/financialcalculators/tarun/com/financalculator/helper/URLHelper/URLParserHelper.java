package financialcalculators.tarun.com.financalculator.helper.URLHelper;

import android.os.AsyncTask;
import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import financialcalculators.tarun.com.financalculator.SimpleMortgageCalculatorActivity;
import financialcalculators.tarun.com.financalculator.helper.MonthlyPaymentItem;
import financialcalculators.tarun.com.financalculator.helper.SAXHandler.MonthlyPaymentSAXHandler;

/**
 * Created by Tarun on 12/17/2014.
 */
public class URLParserHelper {

    private static final int MAX_RETRIES = 3;
    private static final long MAX_TIMEOUT = 2000;

    protected static void parse(InputStream is, final DefaultHandler handler,
                                boolean isRestFormat) throws FactoryConfigurationError {
        if (is == null || handler == null)
            return;
        try {
            if (isRestFormat) {

                int headerSeparator = ('\r' << 24) | ('\n' << 16) | ('\r' << 8)
                        | ('\n');
                int current = 0;
                int dat = 0;
                while ((dat = is.read()) >= 0) {
                    current <<= 8;
                    current |= (dat & 0xFF);
                    if (current == headerSeparator) {
                        break;
                    }
                }
            }
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            xr.setContentHandler(handler);
            xr.parse(new InputSource(is));
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                    is = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void parseWithRetry(String urlString,
                                       DefaultHandler handler, String apiName) throws Exception {
        URL url = new URL(urlString);
        Log.v("Test", "Zillow URL Helper URL: " + urlString);
        InputStream inputStream = null;
        for (int i = 0; i < MAX_RETRIES; i++) {
            try {
                inputStream = url.openConnection().getInputStream();
                if (inputStream != null) {
                    parse(inputStream, handler, false);
                    inputStream.close();
                    inputStream = null;
                    return;
                }

            } catch (IOException ex) {
                // do nothing...
                ex.printStackTrace();
                Log.v("Test", ex.getMessage());
            } finally {
                try {
                    inputStream.close();
                    inputStream = null;
                } catch (Exception e) {
                }
            }
            try {
                Thread.sleep(MAX_TIMEOUT);
            } catch (InterruptedException e) {
            }
        }

        throw new Exception(String.format("No data from Zillow API for %1$s!",
                apiName));
    }



}
