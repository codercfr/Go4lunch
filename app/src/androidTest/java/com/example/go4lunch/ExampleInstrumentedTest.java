package com.example.go4lunch;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

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
    public void createSignInByEmail(){
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

    @Test
    public void signInByEmail(){

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.sign_in_byMail), withText("sign in by mail"),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.etEmail),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("test&@gmail.com"));

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.etPassword),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("azdzdfsq"), closeSoftKeyboard());


        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.btnSignIn2), withText("Sign_in"),
                        isDisplayed()));
        materialButton3.perform(click());

    }

    // voir comment faire marcher le sign in d'abord.
    @Test
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
    @Test
    public void showListRestaurants(){
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.action_logo), withContentDescription("ListView"),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        onView(isRoot()).perform(waitFor(500));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.savedRecyclerView),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));



        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.add_choices),
                        isDisplayed()));
        floatingActionButton.perform(click());
    }

    @Test
    public void showCoworker(){
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.action_like), withContentDescription("LIKE"),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

    }

    @Test
    public void addLikes(){
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.action_logo), withContentDescription("ListView"),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        onView(isRoot()).perform(waitFor(500));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.savedRecyclerView),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction likeAction = onView(
                allOf(withId(R.id.action_like),
                        isDisplayed()));
        likeAction.perform(click());
    }

    @Test
    public void openCall(){
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.action_logo), withContentDescription("ListView"),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        onView(isRoot()).perform(waitFor(500));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.savedRecyclerView),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction callAction = onView(
                allOf(withId(R.id.action_call),
                        isDisplayed()));
        callAction.perform(click());

    }

    @Test
    public void openWebsite(){
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.action_logo), withContentDescription("ListView"),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        onView(isRoot()).perform(waitFor(500));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.savedRecyclerView),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction websiteAction = onView(
                allOf(withId(R.id.action_website),
                        isDisplayed()));
        websiteAction.perform(click());

    }

    @Test
    public void openYourLunch(){
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.lunch_restaurant),
                        isDisplayed()));
        navigationMenuItemView.perform(click());
    }
      @Test
    public void notofication(){
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.setings),
                        isDisplayed()));
        navigationMenuItemView.perform(click());
    }
    @Test
    public void logout(){
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.logout),
                        isDisplayed()));
        navigationMenuItemView.perform(click());
    }



    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    public static ViewAction waitFor(long delay) {
        return new ViewAction() {
            @Override public Matcher<View> getConstraints() {
                return isRoot();
            }
            @Override public String getDescription() {
                return "wait for " + delay + "milliseconds";
            }

            @Override public void perform(UiController uiController, View view) {
                uiController.loopMainThreadForAtLeast(delay);
            }
        };
    }




}