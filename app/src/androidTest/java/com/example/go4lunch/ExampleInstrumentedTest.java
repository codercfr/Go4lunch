package com.example.go4lunch;

import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewInteraction;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {



    @Before
    public void launchActivity() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.go4lunch", appContext.getPackageName());
    }


    @Test
    public void signInByEmail(){
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.sign_in_byMail), withText("sign in by mail"),

                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.etPassword),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("azdzdfsq"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btnCreateAccount), withText("Create your account"),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.etEmail),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("test&@gmail.com"));

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.etEmail), withText("test&@gmail.com"),
                        isDisplayed()));
        appCompatEditText4.perform(closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.btnCreateAccount), withText("Create your account"),
                        isDisplayed()));
        materialButton3.perform(click());
    }

    // voir comment faire marcher le sign in d'abord.
    public void showMap(){

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.action_android), withContentDescription("MapView"),

                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.action_android), withContentDescription("MapView"),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

    }

    public void showListRestaurants(){
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.action_logo), withContentDescription("ListView"),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());
    }


}