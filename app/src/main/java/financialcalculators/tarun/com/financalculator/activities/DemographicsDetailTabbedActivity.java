package financialcalculators.tarun.com.financalculator.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Locale;

import financialcalculators.tarun.com.financalculator.R;
import financialcalculators.tarun.com.financalculator.helper.pojos.Chart;
import financialcalculators.tarun.com.financalculator.helper.pojos.LocalDemoGraphicsItem;

public class DemographicsDetailTabbedActivity extends ActionBarActivity implements ActionBar.TabListener {

    /**
    * The {@link android.support.v4.view.PagerAdapter} that will provide
    * fragments for each of the sections. We use a
    * {@link FragmentPagerAdapter} derivative, which will keep every
    * loaded fragment in memory. If this becomes too memory intensive, it
    * may be best to switch to a
    * {@link android.support.v4.app.FragmentStatePagerAdapter}.
    */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demographics_detail_tabbed);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_subpages, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        else if (id == R.id.action_home) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        RegionFragment regionFragment = new RegionFragment();
        LinksFragment linksFragment = new LinksFragment();
        ChartFragment chartFragment = new ChartFragment();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position == 0){
                return regionFragment;
            } else if(position == 1) {
                return linksFragment;
            } else {
                return chartFragment;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A fragment for region tab
     */
    public static class RegionFragment extends ListFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            Intent intent = getActivity().getIntent();
            LocalDemoGraphicsItem item = (LocalDemoGraphicsItem) intent.getExtras().getSerializable("LocalDemoGraphicsItem");
            String[] regionList = {"ID - "+ item.region.getRegionId(),
                    "STATE - " + item.region.getState(), "CITY - " + item.region.getCity(),
                    "ZIP - " + item.region.getZipCode(), "LATITUDE - " + item.region.getLatitude(),
                    "LONGITUDE - " + item.region.getLatitude(), "MORE INFO"};
            ListAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, regionList);
            setListAdapter(adapter);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            return inflater.inflate(R.layout.fragment_demo_detail_tabbed_region, container, false);
        }

        @Override
        public void onListItemClick(ListView l, View v, int pos, long id) {
            super.onListItemClick(l, v, pos, id);
            if(pos == 6) {
                Intent intent = getActivity().getIntent();
                LocalDemoGraphicsItem item = (LocalDemoGraphicsItem) intent.getExtras().getSerializable("LocalDemoGraphicsItem");
                if(!item.region.getZmmrateurl().isEmpty()) {
                    Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.region.getZmmrateurl()));
                    startActivity(urlIntent);
                }
            } else{
                Toast.makeText(getActivity(), getString(R.string.zillow_url_not_present), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * A fragment for links tab
     */
    public static class LinksFragment extends ListFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            String[] linksList = {"AFFORDABILITY", "HOMES AND REAL ESTATE", "PEOPLE", "FOR SALE",
                    "FOR SALE BY OWNER", "FORECLOSURES", "RECENTLY SOLD"};
            ListAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, linksList);
            setListAdapter(adapter);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            return inflater.inflate(R.layout.fragment_demo_detail_tabbed_region, container, false);
        }

        @Override
        public void onListItemClick(ListView l, View v, int pos, long id) {
            super.onListItemClick(l, v, pos, id);
            Intent intent = getActivity().getIntent();
            LocalDemoGraphicsItem item = (LocalDemoGraphicsItem) intent.getExtras().getSerializable("LocalDemoGraphicsItem");
            Intent urlIntent = null;
            if(pos == 0 && !item.links.getAffordability().isEmpty()){
                urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.links.getAffordability()));
            } else if (pos == 0 && item.links.getAffordability().isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.zillow_url_not_present), Toast.LENGTH_SHORT).show();
            } else if(pos == 1 && !item.links.getHomesandrealestate().isEmpty()){
                urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.links.getHomesandrealestate()));
            } else if (pos == 1 && item.links.getHomesandrealestate().isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.zillow_url_not_present), Toast.LENGTH_SHORT).show();
            } else if(pos == 2 && !item.links.getPeople().isEmpty()) {
                urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.links.getPeople()));
            } else if (pos == 2 && item.links.getPeople().isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.zillow_url_not_present), Toast.LENGTH_SHORT).show();
            } else if(pos == 3 && !item.links.getForSale().isEmpty()) {
                urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.links.getForSale()));
            } else if (pos == 3 && item.links.getForSale().isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.zillow_url_not_present), Toast.LENGTH_SHORT).show();
            } else if(pos == 4 && !item.links.getForSaleByOwner().isEmpty()) {
                urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.links.getForSaleByOwner()));
            } else if (pos == 4 && item.links.getForSaleByOwner().isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.zillow_url_not_present), Toast.LENGTH_SHORT).show();
            } else if(pos == 5 && !item.links.getForeclosures().isEmpty()) {
                urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.links.getForeclosures()));
            } else if (pos == 5 && item.links.getForeclosures().isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.zillow_url_not_present), Toast.LENGTH_SHORT).show();
            } else if(pos == 6 && !item.links.getRecentlySold().isEmpty()) {
                urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.links.getRecentlySold()));
            } else if (pos == 6 && item.links.getRecentlySold().isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.zillow_url_not_present), Toast.LENGTH_SHORT).show();
            }
            if(urlIntent!= null) {
                startActivity(urlIntent);
            }
        }
    }

    /**
     * A fragment for chart tab
     */
    public static class ChartFragment extends ListFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            Intent intent = getActivity().getIntent();
            LocalDemoGraphicsItem item = (LocalDemoGraphicsItem) intent.getExtras().getSerializable("LocalDemoGraphicsItem");
            String[] chartList = new String[item.charts.size()];
            int i = 0;
            for(Chart chart: item.charts){
                chartList[i] = chart.getName().toUpperCase();
                i++;
            }
            ListAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, chartList);
            setListAdapter(adapter);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            return inflater.inflate(R.layout.fragment_demo_detail_tabbed_region, container, false);
        }

        @Override
        public void onListItemClick(ListView l, View v, int pos, long id) {
            super.onListItemClick(l, v, pos, id);
            Intent intent = getActivity().getIntent();
            LocalDemoGraphicsItem item = (LocalDemoGraphicsItem) intent.getExtras().getSerializable("LocalDemoGraphicsItem");
            Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.charts.get(pos).getUrl()));
            Toast.makeText(getActivity(), "Item " + item.charts.get(pos).getName() + " was clicked", Toast.LENGTH_SHORT).show();
            if(!item.charts.get(pos).getUrl().isEmpty()) {
                startActivity(urlIntent);
            }
        }
    }
}
