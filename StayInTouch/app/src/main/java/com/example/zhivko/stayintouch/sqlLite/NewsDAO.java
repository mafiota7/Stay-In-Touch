package com.example.zhivko.stayintouch.sqlLite;


import com.example.zhivko.stayintouch.model.News;

import java.util.List;

/**
 * Created by Panche on 4/10/2016.
 */
public interface NewsDAO {

    public boolean saveNews(News news);
    public boolean deleteNews(News news);
    public List<News> getNews();
}
