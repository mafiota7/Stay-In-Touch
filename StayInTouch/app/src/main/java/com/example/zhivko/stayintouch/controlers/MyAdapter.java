package com.example.zhivko.stayintouch.controlers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.zhivko.stayintouch.*;
import com.example.zhivko.stayintouch.model.News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Panche on 4/4/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

   private ArrayList<News> newFeed;
    @LayoutRes private int layoutRes;
    private Context context;
    /*
       boolean for showing and hiding my favorite button and remove news from my favorite category button
     */
    private boolean hideMyFavoriteButton = false;
    private IComunicatorAdapter iComunicatorAdapter;
    public MyAdapter(ArrayList<News> newFeed, Context context, @LayoutRes int res) {
        this.newFeed = newFeed;
        this.iComunicatorAdapter = (IComunicatorAdapter) context;
        this.layoutRes = res;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutRes, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        /*
        YoYo library for animation of cardview
         */
        YoYo.with(Techniques.FadeInLeft).playOn(holder.cardView);
        final News news = newFeed.get(position);
        holder.title.setText(news.getTitle());
        holder.description.setText(news.getDescription());
        holder.pubDate.setText(news.getPubDate());
        /*
        Picassso library for downloading images from server by setting only url
         */
        Picasso.with(context).load(news.getThumbnail()).into(holder.newsImage);
        /*
        if construction for setting images to news from source that doesn't provide url for news images from their xml files
         setting default image (logo of news provider )
         */
        if (news.getLink().contains("bbc.co.uk")) {
                holder.newsImage.setImageResource(R.drawable.bbc);
        } else {
            if (news.getLink().contains("cbsnews.com")) {
                holder.newsImage.setImageResource(R.drawable.cbs);
            } else {
                if (news.getLink().contains("foxnews.com")) {
                    holder.newsImage.setImageResource(R.drawable.fox_news);
                } else {
                    if ( news.getLink().contains("computerweekly.com")) {
                        holder.newsImage.setImageResource(R.drawable.computer_weekly_big);
                    }
                }
            }
        }
        /*
        on click listener to item of the recyclerview for moving on next activity(news deatils)
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDeatils.class);
                intent.putExtra("link", news.getLink());
                context.startActivity(intent);

            }
        });

        if(hideMyFavoriteButton) {
            holder.myFavoriteButton.setVisibility(View.GONE);
            holder.removeButton.setVisibility(View.VISIBLE);
        }else{
            holder.myFavoriteButton.setVisibility(View.VISIBLE);
            holder.removeButton.setVisibility(View.GONE);
        }
        /*
        adding news to database
         */
        holder.myFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean save = DBNewsDAO.getInstance(context).saveNews(news);
                if(!save)
                    Toast.makeText(context, "THIS NEWS IS ALREADY IN YOUR FAVORITE NEWS", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "NEW ITEM HAS BEEN ADDED TO YOUR FAVORITE LIST", Toast.LENGTH_SHORT).show();
            }
        });
        /*
        removing news from database
         */

        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBNewsDAO.getInstance(context).deleteNews(news);
                notifyDataSetChanged();
                iComunicatorAdapter.notifyContext();

            }
        });

    }

    @Override
    public int getItemCount() {
        if(newFeed != null)
            return newFeed.size();
        else
        /*
        if we didn't get any data from xml file to prevent null pointer exception for trying to reach  newsFeed
         */
            return 0;
    }

    public void removeMyFavoriteButton() {
        hideMyFavoriteButton = true;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView pubDate;
        TextView title;
        TextView description;
        ImageView newsImage;
        CardView cardView ;
        Button myFavoriteButton, shareButton, removeButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            pubDate = (TextView) itemView.findViewById(R.id.text_date);
            title = (TextView) itemView.findViewById(R.id.text_title);
            description = (TextView) itemView.findViewById(R.id.text_desc);
            newsImage = (ImageView) itemView.findViewById(R.id.imageView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            myFavoriteButton = (Button) itemView.findViewById(R.id.button_my_favorite);
            shareButton = (Button) itemView.findViewById(R.id.button_share);
            removeButton = (Button) itemView.findViewById(R.id.button_remove_from_my_favorite);

        }
    }
    /*
    communicator pattern for communication  between adapter and context that is using this adapter
     */
    public interface IComunicatorAdapter{
         void notifyContext();
    }
}