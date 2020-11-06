package com.example.newsio.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsio.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ArticleHolder> {
    List<Article> articlelist;
    RecyclerAdapter(List<Article> articlelist){
        this.articlelist = articlelist;
    }

    @NonNull
    @Override
    public ArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlayout, parent, false);
        CardView card = view.findViewById(R.id.card);
        if(card.getParent()!=null) {
            ((ViewGroup) card.getParent()).removeView(card);
        }
        return new ArticleHolder(card);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleHolder holder, int position) {
        Article currentArticle = articlelist.get(position);
        holder.descriptionText.setText(articlelist.get(position).getDescription());
        holder.dateText.setText(articlelist.get(position).getPublishedAt());
    }

    @Override
    public int getItemCount() {
        return articlelist.size();
    }

    public class ArticleHolder extends RecyclerView.ViewHolder{
        TextView descriptionText;
        TextView dateText;
        ImageView imageView;
        public ArticleHolder(@NonNull View itemView) {
            super(itemView);
            descriptionText = (TextView) itemView.findViewById(R.id.description_text);
            dateText = (TextView) itemView.findViewById(R.id.date_text);
            imageView = (ImageView) itemView.findViewById(R.id.image);

        }
    }

}
