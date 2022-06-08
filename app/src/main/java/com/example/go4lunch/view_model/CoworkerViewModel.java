package com.example.go4lunch.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.model.Users;
import com.example.go4lunch.repository.CoworkerRepository;

import java.util.List;

public class CoworkerViewModel extends ViewModel {

    private static final CoworkerRepository coworkerRepository= new CoworkerRepository();

    public LiveData<List<Users>> getListCoworker(){return coworkerRepository.getListUsers();}
}
