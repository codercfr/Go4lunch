package com.example.go4lunch;

import com.example.go4lunch.model.Users;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {



    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void getUser(){
        final Users task1 = new Users();
        final Users task2 = new Users();
        final Users task3 = new Users();

        task1.setUid("1");
        task1.setUid("2");
        task1.setUid("3");

        assertEquals("1",task1.getUid());
        assertEquals("2",task2.getUid());
        assertEquals("3",task3.getUid());
    }

    @Test
    public void getStars(){
        final Users task1 = new Users();

    }

    // récupérer le restaurant.

    //récupérer la liste des restauarants




}