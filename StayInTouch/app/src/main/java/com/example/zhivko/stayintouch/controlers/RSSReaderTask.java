package com.example.zhivko.stayintouch.controlers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.zhivko.stayintouch.NewsProvirdersActivity;
import com.example.zhivko.stayintouch.fragments.NoNewsToShowFragment;
import com.example.zhivko.stayintouch.model.News;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


/**
 * Created by Panche on 4/3/2016.
 */
public class RSSReaderTask extends AsyncTask<Void,Void,Void> {

/*
only top news
 */
    public static final String WWW_SCIENCEMAG_ORG_RSS_NEWS_XML = "https://www.sciencemag.org/rss/news_current.xml";


    public static final String WWW_SKY_NEWS_HOME_XML = "http://feeds.skynews.com/feeds/rss/home.xml";
    public static final String WWW_SKY_NEWS_WORLD_XML = "http://feeds.skynews.com/feeds/rss/world.xml";
    public static final String WWW_SKY_NEWS_POLITICS_XML = "http://feeds.skynews.com/feeds/rss/politics.xml";
    public static final String WWW_SKY_NEWS_TECHNOLOGY_XML = "http://feeds.skynews.com/feeds/rss/technology.xml";
    public static final String WWW_SKY_NEWS_BUSINESS_XML = "http://feeds.skynews.com/feeds/rss/business.xml";


    public static final String WWW_FEEDS_BBC_CO_UK_NEWS_RSS_XML = "http://feeds.bbci.co.uk/news/rss.xml";
    public static final String WWW_FEEDS_BBC_CO_UK_NEWS_WORLD_RSS_XML = "http://feeds.bbci.co.uk/news/world/rss.xml";
    public static final String WWW_FEEDS_BBC_CO_UK_NEWS_POLITICS_RSS_XML = "http://feeds.bbci.co.uk/news/business/rss.xml";
    public static final String WWW_FEEDS_BBC_CO_UK_NEWS_TECHNOLOGY_RSS_XML = "http://feeds.bbci.co.uk/news/technology/rss.xml";
    public static final String WWW_FEEDS_BBC_CO_UK_NEWS_BUSINESS_RSS_XML = "http://feeds.bbci.co.uk/news/business/rss.xml";


    public static final String WWW_CBSNEWS_COM_LATEST_RSS_MAIN = "http://www.cbsnews.com/latest/rss/main";
    public static final String WWW_CBSNEWS_COM_LATEST_RSS_WORLD = "http://www.cbsnews.com/latest/rss/world";
    public static final String WWW_CBSNEWS_COM_LATEST_RSS_POLITICS = "http://www.cbsnews.com/latest/rss/politics";
    public static final String WWW_CBSNEWS_COM_LATEST_RSS_TECHNOLOGY = "http://www.cbsnews.com/latest/rss/tech";
    public static final String WWW_CBSNEWS_COM_LATEST_RSS_BUSINESS = "http://www.cbsnews.com/latest/rss/moneywatch";


    public static final String WWW_COMPUTERWEEKLY_COM_RSS_LATEST_NEWS= "http://www.computerweekly.com/rss/Latest-IT-news.xml";
    public static final String WWW_COMPUTERWEEKLY_COM_RSS_HARDWARE_NEWS = "http://www.computerweekly.com/rss/IT-hardware.xml";
    public static final String WWW_COMPUTERWEEKLY_COM_RSS_ENTERPRISE_SOFTWARE_NEWS = "http://www.computerweekly.com/rss/Enterprise-software.xml";
    public static final String WWW_COMPUTERWEEKLY_COM_RSS_SERVICES_NEWS = "http://www.computerweekly.com/rss/IT-services-and-outsourcing.xml";
    public static final String WWW_COMPUTERWEEKLY_COM_RSS_SECURITY = "http://www.computerweekly.com/rss/IT-security.xml";
    public static final String WWW_COMPUTERWEEKLY_COM_RSS_NETWORKING_AND_COMMUNICATION = "http://www.computerweekly.com/rss/Networking-and-communication.xml";
    public static final String WWW_COMPUTERWEEKLY_COM_RSS_MOBILE_TECHOLOGY = "http://www.computerweekly.com/rss/Mobile-technology.xml";
    public static final String WWW_COMPUTERWEEKLY_COM_RSS_INTERNET_TECHOLOGY ="http://www.computerweekly.com/rss/Internet-technology.xml";
    public static final String WWW_COMPUTERWEEKLY_COM_RSS_IT_MENAGEMENT = "http://www.computerweekly.com/rss/IT-management.xml";
    public static final String WWW_COMPUTERWEEKLY_COM_RSS_IT_CAREERS_AND_IT_SKILLS = "http://www.computerweekly.com/rss/IT-careers-and-IT-skills.xml";
    public static final String WWW_COMPUTERWEEKLY_COM_RSS_PUBLIC_SECTOR_IT_NEWS = "http://www.computerweekly.com/rss/Public-sector-IT-news.xml";
    public static final String WWW_COMPUTERWEEKLY_COM_RSS_FINANCIAL_SERVICES_IT_NEWS = "http://www.computerweekly.com/rss/Financial-services-IT-news.xml";


    /*
    only top news
     */
    public static final String WWW_FOXNEWS_COM_ABOUT_RSS_FEEDBURNER_FOXNEWS_LATEST = "http://www.foxnews.com/about/rss/feedburner/foxnews/latest";


    private Context context;
    private ProgressDialog progressDialog;
    private URL url;
    private HttpURLConnection connection = null;
    private InputStream inputStream = null;
    /*
    main container
     */
    private ArrayList<News> newsFeed;
    /*
    search container
     */
    private ArrayList<News> search;
    private RecyclerView recyclerView;
    public static String adress ;
    private String phrase = "";



    public RSSReaderTask(Context context, RecyclerView recyclerView, String adress, String phrase) {

        this.context = context;
        this.progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading ...");
        this.recyclerView = recyclerView;
        this.adress = adress;
        this.phrase = phrase;

    }

    public static String getAdress() {
        return adress;
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        MyAdapter myAdapter;
        /*
        adding different layout for adapter depending the info from xml document(image resolution)
         */
        if(adress.contains("www.sciencemag.org")){
            if(phrase == ""){
                myAdapter = new MyAdapter(newsFeed,context, R.layout.recycler_row_layout_science_org);
            }else{
                /*
                if we press search button and try to search by word or phrase
                 */
                startSearch(phrase);
                myAdapter = new MyAdapter(search,context, R.layout.recycler_row_layout_science_org);
            }
        }else{
            if(phrase =="") {
                myAdapter = new MyAdapter(newsFeed, context, R.layout.recycler_row_layout_sky_news);
            }else{
                 /*
                if we press search button and try to search by word or phrase
                 */
                startSearch(phrase);
                myAdapter = new MyAdapter(search,context, R.layout.recycler_row_layout_sky_news);
            }
        }
        /*
        setting layout manager and adapter to recycler view
         */
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(myAdapter);

    }

    @Override
    protected Void doInBackground(Void... params) {
        processXml(getXmlData());
        return null;
    }
/*
    working with done xml document, deviate to items and find tags and their values
    adding to collection that we will use for making the adapter
 */
    private void processXml(Document xmlData) {
        newsFeed = new ArrayList<>();
        if(xmlData != null){
            Element root = xmlData.getDocumentElement();
            Node chanel = root.getChildNodes().item(1);
            NodeList items = chanel.getChildNodes();
            for(int i = 0 ; i < items.getLength(); i++){
                Node currentNode = items.item(i);
                /*
                trying to find all childs with tags item and explore their tags and save their values
                 */
                if(currentNode.getNodeName().equalsIgnoreCase("item")){
                    NodeList currentChildren = currentNode.getChildNodes();
                    News item = new News();
                    for (int j = 0 ; j < currentChildren.getLength(); j ++){
                        Node currentChild = currentChildren.item(j);
                        if(currentChild.getNodeName().equalsIgnoreCase("title")){
                            item.setTitle(currentChild.getTextContent());
                        }else{
                            if(currentChild.getNodeName().equalsIgnoreCase("description")){
                                item.setDescription(currentChild.getTextContent());
                            }else{
                                if(currentChild.getNodeName().equalsIgnoreCase("pubdate")){
                                    item.setPubDate(currentChild.getTextContent());
                                }else{
                                    if(currentChild.getNodeName().equalsIgnoreCase("link")){
                                        item.setLink(currentChild.getTextContent());
                                    }else{
                                        if(currentChild.getNodeName().equalsIgnoreCase("media:thumbnail")){
                                            String url = currentChild.getAttributes().item(0).getTextContent();
                                            item.setThumbnail(url);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    /*
                    adding news to our main container
                     */
                    newsFeed.add(item);
                }
            }
        }

    }

    /*
    trying to take rss file from server for our use
     */

    private Document getXmlData(){
        try {
            url = new URL(adress);
            connection = (HttpURLConnection) url.openConnection();
            inputStream = connection.getInputStream();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document xml = documentBuilder.parse(inputStream);
            return xml;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        } catch (SAXException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /*
    searching if phrase from search  contains  to news title or news description
     */

    private void startSearch(String phrase) {
        search = new ArrayList<>();
        for (News news : newsFeed) {
            String description = "" ;
            if(news.getDescription() != null)
                 description = news.getDescription().toLowerCase();
            String title = news.getTitle().toLowerCase();
            if (description.contains(phrase.toLowerCase())|| title.contains(phrase.toLowerCase())) {
                /*
                adding my favorite news to new container
                 */
                search.add(news);
            }
        }
        /*
        dangerous code can invoke class cast exception if we try to use this task with other components
         */
          /*
            if search result is null news calling noNews fragment
           */
        if(search.isEmpty()){
            NoNewsToShowFragment.IcomunicatorNoNews icomunicatorNoNews = (NoNewsToShowFragment.IcomunicatorNoNews) context;
            icomunicatorNoNews.comunicateNoNews();
        }else{
            /*
            else setting only title to search filter
             */
            NewsProvirdersActivity newsProviders = (NewsProvirdersActivity) context;
            newsProviders.getSupportActionBar().setTitle("SEARCH FILTER '" + phrase + "'");
        }
    }
}
