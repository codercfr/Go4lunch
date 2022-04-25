package com.example.go4lunch;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    @Test
    public void mainActivityTest() {
        ViewInteraction gz = onView(
        allOf(withText("Sign in"),
        childAtPosition(
        allOf(withId(R.id.btnsign),
        childAtPosition(
        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
        0)),
        0),
        isDisplayed()));
                gz.perform(click());

                ViewInteraction bottomNavigationItemView = onView(
        allOf(withId(R.id.action_logo), withContentDescription("ListView"),
        childAtPosition(
        childAtPosition(
        withId(R.id.bottom_view),
        0),
        1),
        isDisplayed()));
                bottomNavigationItemView.perform(click());

                ViewInteraction bottomNavigationItemView2 = onView(
        allOf(withId(R.id.action_logo), withContentDescription("ListView"),
        childAtPosition(
        childAtPosition(
        withId(R.id.bottom_view),
        0),
        1),
        isDisplayed()));
                bottomNavigationItemView2.perform(click());

                ViewInteraction bottomNavigationItemView3 = onView(
        allOf(withId(R.id.action_android), withContentDescription("MapView"),
        childAtPosition(
        childAtPosition(
        withId(R.id.bottom_view),
        0),
        0),
        isDisplayed()));
                bottomNavigationItemView3.perform(click());

                ViewInteraction bottomNavigationItemView4 = onView(
        allOf(withId(R.id.action_logo), withContentDescription("ListView"),
        childAtPosition(
        childAtPosition(
        withId(R.id.bottom_view),
        0),
        1),
        isDisplayed()));
                bottomNavigationItemView4.perform(click());

                ViewInteraction bottomNavigationItemView5 = onView(
        allOf(withId(R.id.action_android), withContentDescription("MapView"),
        childAtPosition(
        childAtPosition(
        withId(R.id.bottom_view),
        0),
        0),
        isDisplayed()));
                bottomNavigationItemView5.perform(click());

                ViewInteraction bottomNavigationItemView6 = onView(
        allOf(withId(R.id.action_logo), withContentDescription("ListView"),
        childAtPosition(
        childAtPosition(
        withId(R.id.bottom_view),
        0),
        1),
        isDisplayed()));
                bottomNavigationItemView6.perform(click());

                ViewInteraction recyclerView = onView(
        allOf(withId(R.id.savedRecyclerView),
        childAtPosition(
        withClassName(is("android.widget.RelativeLayout")),
        0)));
                recyclerView.perform(actionOnItemAtPosition(0, click()));
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
                        && view.equals(((ViewGroup)parent).getChildAt(position));
            }
        };
    }
    }
