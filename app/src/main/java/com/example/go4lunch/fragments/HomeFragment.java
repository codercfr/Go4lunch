package com.example.go4lunch.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.go4lunch.R;
import com.example.go4lunch.adapter.ArticleAdapter;
import com.example.go4lunch.model.Article;
import com.example.go4lunch.view_model.PlaceViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ArrayList<Article>articleArrayList= new ArrayList<>();
    PlaceViewModel placeViewModel;
    private ArticleAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.news_item, container, false);
        recyclerView=rootView.findViewById(R.id.savedRecyclerView);
        getArticles();
        init();
        //getArticles();
        return rootView;
    }

    @Override
    public void onViewCreated( @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void getArticles() {
        placeViewModel.getBashBoardNewsResponseLiveData().observe((LifecycleOwner) requireContext(), placesResponse -> {
            if(placesResponse != null && placesResponse.getArticles()!=null && !placesResponse.getArticles().isEmpty())
            {
                List<Article>articleList= placesResponse.getArticles();
                articleArrayList.addAll(articleList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void init() {
        adapter=new ArticleAdapter(requireContext(),articleArrayList);
        layoutManager=new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        placeViewModel= new ViewModelProvider(this).get(PlaceViewModel.class);

    }
}