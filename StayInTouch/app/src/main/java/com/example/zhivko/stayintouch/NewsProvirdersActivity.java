package com.example.zhivko.stayintouch;

import android.app.FragmentManager;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.zhivko.stayintouch.controlers.MyAdapter;
import com.example.zhivko.stayintouch.controlers.RSSReaderTask;
import com.example.zhivko.stayintouch.controlers.VerticalSpace;
import com.example.zhivko.stayintouch.fragments.NoNewsToShowFragment;
import com.example.zhivko.stayintouch.fragments.SearchDialogFragment;
import com.example.zhivko.stayintouch.model.News;
import com.example.zhivko.stayintouch.sqlLite.DBNewsDAO;

import java.util.ArrayList;

public class NewsProvirdersActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchDialogFragment.IComunicator ,
        NoNewsToShowFragment.IcomunicatorNoNews,MyAdapter.IComunicatorAdapter {
    public static final String MY_FAVORITE_NEWS = "My Favorite News";
    private RecyclerView recyclerView;
    private RSSReaderTask rssReaderTask;
    private String phrase = "";
    private String adress;
    private MyAdapter myAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_providers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        /*
        setting space for items from the recycler view
         */
        recyclerView.addItemDecoration(new VerticalSpace(30));
        /*
        disable screen rotation
         */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        if(savedInstanceState == null || savedInstanceState.isEmpty()) {
            phrase = "";
            rssReaderTask = new RSSReaderTask(this, recyclerView, getIntent().getExtras().getString(ChooseProvirderActivity.PROVIDER),phrase);
            adress = rssReaderTask.getAdress();
            rssReaderTask.execute();
        }else{
            /*
            in progress for screen rotation
             */
            if(savedInstanceState.get("title").equals(MY_FAVORITE_NEWS)){
                /*
                fill adapter from database is screen rotation is on my favorite news category
                 */
                myAdapter = new MyAdapter((ArrayList<News>) DBNewsDAO.getInstance(this).getNews(), this, R.layout.recycler_row_layout_sky_news);
                myAdapter.removeMyFavoriteButton();
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                getSupportActionBar().setTitle(savedInstanceState.getString("title"));
                recyclerView.setAdapter(myAdapter);

            }else {
                phrase = savedInstanceState.getString("phrase");
                getSupportActionBar().setTitle(savedInstanceState.getString("title"));
                rssReaderTask = new RSSReaderTask(this, recyclerView, savedInstanceState.getString("adress"), phrase);
                adress = rssReaderTask.getAdress();
                rssReaderTask.execute();
            }
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.news_provirders, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        phrase = "";
        switch (id){
            case R.id.nav_back:
                finish();
                break;
            case R.id.nav_bbc_top_stories:
                getSupportActionBar().setTitle("BBC TOP STORIES");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_FEEDS_BBC_CO_UK_NEWS_RSS_XML, phrase);
                adress = rssReaderTask.getAdress();
                rssReaderTask.execute();
                break;
            case R.id.nav_bbc_world:
                getSupportActionBar().setTitle("BBC WORLD NEWS");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_FEEDS_BBC_CO_UK_NEWS_WORLD_RSS_XML,phrase);
                adress = rssReaderTask.getAdress();
                rssReaderTask.execute();
                break;
            case R.id.nav_bbc_politics:
                getSupportActionBar().setTitle("BBC POLITICS NEWS");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_FEEDS_BBC_CO_UK_NEWS_POLITICS_RSS_XML,phrase);
                adress = rssReaderTask.getAdress();
                rssReaderTask.execute();
                break;
            case R.id.nav_bbc_techology:
                getSupportActionBar().setTitle("BBC TECHOLOGY NEWS");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_FEEDS_BBC_CO_UK_NEWS_TECHNOLOGY_RSS_XML, phrase );
                adress = rssReaderTask.getAdress();
                rssReaderTask.execute();
                break;
            case R.id.nav_bbc_business:
                getSupportActionBar().setTitle("BBC BUSINESS NEWS");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_FEEDS_BBC_CO_UK_NEWS_BUSINESS_RSS_XML, phrase );
                adress = rssReaderTask.getAdress();
                rssReaderTask.execute();
                break;
            case R.id.nav_cbs_world:
                getSupportActionBar().setTitle("CBS WORLD NEWS");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_CBSNEWS_COM_LATEST_RSS_WORLD, phrase );
                adress = rssReaderTask.getAdress();
                rssReaderTask.execute();
                break;
            case R.id.nav_cbs_politics:
                getSupportActionBar().setTitle("CBS POLITICS NEWS");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_CBSNEWS_COM_LATEST_RSS_POLITICS, phrase );
                adress = rssReaderTask.getAdress();
                rssReaderTask.execute();
                break;
            case R.id.nav_cbs_techology:
                getSupportActionBar().setTitle("CBS TECHOLOGY NEWS");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_CBSNEWS_COM_LATEST_RSS_TECHNOLOGY, phrase );
                adress = rssReaderTask.getAdress();
                rssReaderTask.execute();
                break;
            case R.id.nav_cbs_top_stories:
                getSupportActionBar().setTitle("CBS TOP STORIES");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_CBSNEWS_COM_LATEST_RSS_MAIN, phrase );
                adress = rssReaderTask.getAdress();
                rssReaderTask.execute();
                break;
            case R.id.nav_cbs_business:
                getSupportActionBar().setTitle("CBS BUSINESS NEWS");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_CBSNEWS_COM_LATEST_RSS_BUSINESS, phrase );
                adress = rssReaderTask.getAdress();
                rssReaderTask.execute();
                break;
            case R.id.nav_fox_top_stories:
                getSupportActionBar().setTitle("FOX NEWS TOP STORIES");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_FOXNEWS_COM_ABOUT_RSS_FEEDBURNER_FOXNEWS_LATEST, phrase );
                adress = rssReaderTask.getAdress();
                rssReaderTask.execute();
                break;
            case R.id.nav_science_top_stories:
                getSupportActionBar().setTitle("SCIENCE AAAS TOP STORIES");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_SCIENCEMAG_ORG_RSS_NEWS_XML, phrase);
                adress = rssReaderTask.getAdress();
                rssReaderTask.execute();
                break;
            case R.id.nav_sky_top_stories:
                getSupportActionBar().setTitle("SKY NEWS TOP STORIES");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_SKY_NEWS_HOME_XML, phrase);
                adress = rssReaderTask.getAdress();
                rssReaderTask.execute();
                break;
            case R.id.nav_sky_world:
                getSupportActionBar().setTitle("SKY NEWS WORLD ");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_SKY_NEWS_WORLD_XML,phrase);
                adress = rssReaderTask.getAdress();
                rssReaderTask.execute();
                break;
            case R.id.nav_sky_politics:
                getSupportActionBar().setTitle("SKY NEWS POLITICS");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_SKY_NEWS_POLITICS_XML,phrase);
                adress = rssReaderTask.getAdress();
                rssReaderTask.execute();
                break;
            case R.id.nav_sky_techology:
                getSupportActionBar().setTitle("SKY NEWS TECHOLOGY");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_SKY_NEWS_TECHNOLOGY_XML,phrase);
                adress = rssReaderTask.getAdress();
                rssReaderTask.execute();
                break;
            case R.id.nav_sky_business:
                getSupportActionBar().setTitle("SKY NEWS BUSINESS");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_SKY_NEWS_BUSINESS_XML,phrase);
                adress = rssReaderTask.getAdress();
                rssReaderTask.execute();
                break;
            case R.id.nav_computer_weekly_enterprise_software_news:
                getSupportActionBar().setTitle("COMPUTER WEEKLY SOFTWARE");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_COMPUTERWEEKLY_COM_RSS_ENTERPRISE_SOFTWARE_NEWS, phrase);
                rssReaderTask.execute();
                break;
            case R.id.nav_computer_weekly_financial_services_it_news:
                getSupportActionBar().setTitle("COMPUTER WEEKLY FINANCIAL");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_COMPUTERWEEKLY_COM_RSS_FINANCIAL_SERVICES_IT_NEWS, phrase);
                rssReaderTask.execute();
                break;
            case R.id.nav_computer_weekly_internet_technology:
                getSupportActionBar().setTitle("COMPUTER WEEKLY INTERNET");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_COMPUTERWEEKLY_COM_RSS_INTERNET_TECHOLOGY, phrase);
                rssReaderTask.execute();
                break;
            case R.id.nav_computer_weekly_it_careers_and_it_skills:
                getSupportActionBar().setTitle("COMPUTER WEEKLY CAREERS");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_COMPUTERWEEKLY_COM_RSS_IT_CAREERS_AND_IT_SKILLS, phrase);
                rssReaderTask.execute();
                break;
            case R.id.nav_computer_weekly_it_hardware_news:
                getSupportActionBar().setTitle("COMPUTER WEEKLY HARDWARE");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_COMPUTERWEEKLY_COM_RSS_HARDWARE_NEWS, phrase);
                rssReaderTask.execute();
                break;
            case R.id.nav_computer_weekly_it_management:
                getSupportActionBar().setTitle("COMPUTER WEEKLY MENAGEMENT");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_COMPUTERWEEKLY_COM_RSS_IT_MENAGEMENT, phrase);
                rssReaderTask.execute();
                break;
            case R.id.nav_computer_weekly_it_networking_and_communication:
                getSupportActionBar().setTitle("COMPUTER WEEKLY NETWORKING");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_COMPUTERWEEKLY_COM_RSS_NETWORKING_AND_COMMUNICATION, phrase);
                rssReaderTask.execute();
                break;
            case R.id.nav_computer_weekly_it_security:
                getSupportActionBar().setTitle("COMPUTER WEEKLY SECURITY");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_COMPUTERWEEKLY_COM_RSS_SECURITY, phrase);
                rssReaderTask.execute();
                break;
            case R.id.nav_computer_weekly_mobile_technology:
                getSupportActionBar().setTitle("COMPUTER WEEKLY MOBILE");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_COMPUTERWEEKLY_COM_RSS_MOBILE_TECHOLOGY, phrase);
                rssReaderTask.execute();
                break;
            case R.id.nav_computer_weekly_it_services_and_outsourcing:
                getSupportActionBar().setTitle("COMPUTER WEEKLY SERVICES");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_COMPUTERWEEKLY_COM_RSS_SERVICES_NEWS, phrase);
                rssReaderTask.execute();
                break;
            case R.id.nav_computer_weekly_top_stories:
                getSupportActionBar().setTitle("COMPUTER WEEKLY TOP STORIES");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_COMPUTERWEEKLY_COM_RSS_LATEST_NEWS, phrase);
                rssReaderTask.execute();
                break;
            case R.id.nav_computer_weekly_public_sector_it_news:
                getSupportActionBar().setTitle("COMPUTER WEEKLY PUBLIC IT");
                rssReaderTask = new RSSReaderTask(this, recyclerView, RSSReaderTask.WWW_COMPUTERWEEKLY_COM_RSS_PUBLIC_SECTOR_IT_NEWS, phrase);
                rssReaderTask.execute();
                break;
            case R.id.nav_search :
                /*
                disable search in my favorite news category
                 */
                if(getSupportActionBar().getTitle().toString().equals(MY_FAVORITE_NEWS)) {
                    Toast.makeText(NewsProvirdersActivity.this, "YOU ARE NOT ABLE TO SEARCH IN THIS CATEGORY", Toast.LENGTH_SHORT).show();
                    break;
                }else {
                    /*
                    show search fragment
                     */
                    FragmentManager manager = getFragmentManager();
                    SearchDialogFragment search = new SearchDialogFragment(this);
                    search.show(manager, "search");
                    break;
                }
            case R.id.nav_my_favorite:
                /*
                fill adapter from database, my favorite news category
                 */
                getSupportActionBar().setTitle(MY_FAVORITE_NEWS);
                MyAdapter myAdapter = new MyAdapter((ArrayList<News>) DBNewsDAO.getInstance(this).getNews(), this, R.layout.recycler_row_layout_sky_news);
                myAdapter.removeMyFavoriteButton();
                recyclerView.setAdapter(myAdapter);

            default:
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
      in progress for screen rotation
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("phrase", phrase);
        outState.putString("adress", rssReaderTask.getAdress());
        outState.putString("title", getSupportActionBar().getTitle().toString());
    }

    /*
    from search fragment
    getting phrase from search fragment
     */
    @Override
    public void comunicate(String phrase) {
        rssReaderTask = new RSSReaderTask(this, recyclerView,adress, phrase);
        rssReaderTask.execute();
        this.phrase = phrase;
    }

    /*
    from noNews fragment
    showing noNews fragment
     */

    @Override
    public void comunicateNoNews() {
        FragmentManager manager = getFragmentManager();
        NoNewsToShowFragment search = new NoNewsToShowFragment(this);
        search.show(manager, "noNews");
    }
    /*
    from noNews fragment
    dismissing noNews fragment
     */

    @Override
    public void reloadInfo() {
        this.phrase = "";
        rssReaderTask = new RSSReaderTask(this,recyclerView,adress,phrase);
        rssReaderTask.execute();
    }

    /*
    from adapter
    fill adapter from database
     */

    @Override
    public void notifyContext() {
        myAdapter = new MyAdapter((ArrayList<News>) DBNewsDAO.getInstance(this).getNews(), this, R.layout.recycler_row_layout_sky_news);
        myAdapter.removeMyFavoriteButton();
        recyclerView.setAdapter(myAdapter);
    }
    /*
    setting screen rotation on
     */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

    }
}
