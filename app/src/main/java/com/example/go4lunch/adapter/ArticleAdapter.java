package com.example.go4lunch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.go4lunch.R;
import com.example.go4lunch.model.Article;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private final Context context;
    ArrayList<Article> articleArrayList;

    public ArticleAdapter(Context context, ArrayList<Article> articleArrayList) {
        this.context = context;
        this.articleArrayList = articleArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ArticleAdapter.ViewHolder holder, int position) {

        Article article = articleArrayList.get(position);
        holder.tvTitle.setText(article.getTitle());
        Glide.with(context)
                .load(article.getUrlToImage())
                .into(holder.imageViewCover);
    }

    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView imageViewCover;
        private final TextView tvTitle;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imageViewCover=itemView.findViewById(R.id.imgViewCover);
            tvTitle=itemView.findViewById(R.id.TvTitle);
        }
    }


}
