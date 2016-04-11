package com.example.zhivko.stayintouch.sqlLite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.zhivko.stayintouch.model.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Panche on 4/10/2016.
 */
public class DBNewsDAO implements NewsDAO{

    private static DBNewsDAO instance;
    private static Context context;
    private static ArrayList<News> favorite;

    private DBNewsDAO(Context context){
        DBNewsDAO.context = context;
    }

    public static DBNewsDAO getInstance(Context context) {
        if(instance == null)
            instance = new DBNewsDAO(context);
        return instance;
    }

    @Override
    public boolean saveNews(News news) {
        SQLiteDatabase db = DBHelper.getInstance(context).getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.NEWS_TITLE, news.getTitle());
        cv.put(DBHelper.NEWS_DESCRIPTION, news.getDescription());
        cv.put(DBHelper.NEWS_LINK, news.getLink());
        cv.put(DBHelper.NEWS_PUBLISH_DATE, news.getPubDate());
        cv.put(DBHelper.NEWS_THUMBNAIL, news.getThumbnail());
        long id = db.insert(DBHelper.TABLE_NAME_NEWS, null, cv);
        if(favorite != null && !favorite.contains(news))
            favorite.add(news);
        return id > 0;
    }

    @Override
    public boolean deleteNews(News news) {
        SQLiteDatabase db = DBHelper.getInstance(context).getWritableDatabase();
        long deletedRows = db.delete(DBHelper.TABLE_NAME_NEWS, DBHelper.NEWS_LINK + " =?", new String[]{news.getLink()});
        if(favorite != null)
            favorite.remove(news);
        return deletedRows > 0;
    }
    /*
    getting container from database
     */

    @Override
    public List<News> getNews() {
        if(favorite != null)
            return favorite;
        else {
            favorite = new ArrayList<>();
            SQLiteDatabase db = DBHelper.getInstance(context).getWritableDatabase();
            Cursor cursor = db.query(DBHelper.TABLE_NAME_NEWS, new String[]{"*"}, null, null, null, null, null, null);
            int titleColumn = cursor.getColumnIndex(DBHelper.NEWS_TITLE);
            int descriptionColumn = cursor.getColumnIndex(DBHelper.NEWS_DESCRIPTION);
            int linkColumn = cursor.getColumnIndex(DBHelper.NEWS_LINK);
            int publishDateColumn = cursor.getColumnIndex(DBHelper.NEWS_PUBLISH_DATE);
            int thumbnailColumn = cursor.getColumnIndex(DBHelper.NEWS_THUMBNAIL);

            while (cursor.moveToNext()) {
                News news = new News();
                news.setTitle(cursor.getString(titleColumn));
                news.setDescription(cursor.getString(descriptionColumn));
                news.setLink(cursor.getString(linkColumn));
                news.setPubDate(cursor.getString(publishDateColumn));
                news.setThumbnail(cursor.getString(thumbnailColumn));
                favorite.add(news);
            }
            return favorite;
        }
    }
}
