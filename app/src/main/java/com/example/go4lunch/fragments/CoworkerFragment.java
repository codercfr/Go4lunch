package com.example.go4lunch.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.R;
import com.example.go4lunch.adapter.CoworkerAdapter;
import com.example.go4lunch.model.GooglePlaceModel;
import com.example.go4lunch.model.Users;
import com.example.go4lunch.view_model.CoworkerViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CoworkerFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Users> usersList = new ArrayList<>();
    private CoworkerAdapter coworkerAdapter = new CoworkerAdapter(usersList);
    private CoworkerViewModel coworkerViewModel;
    private ArrayList<String>restaurantName= new ArrayList<>();


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.coworker_recyclerview, container, false);
        recyclerView=rootView.findViewById(R.id.show_coworker_recyclerview);
        coworkerViewModel=new ViewModelProvider(this).get(CoworkerViewModel.class);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setAdapter(coworkerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        coworkerViewModel.getListCoworker()
                .observe(getViewLifecycleOwner(), usersList -> {
                    this.usersList.clear();
                    this.usersList.addAll(usersList);
                    for (int i =0 ; i<usersList.size();i++){
                        restaurantName.add(usersList.get(i).getRestaurantName());
                    }
                    coworkerAdapter.notifyDataSetChanged();
                });

    }
}
