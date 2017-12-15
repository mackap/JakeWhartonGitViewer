package whartongitviewer.com.jakewhartongitviewer.view;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import whartongitviewer.com.jakewhartongitviewer.R;
import whartongitviewer.com.jakewhartongitviewer.util.EspressoIdlingResource;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertNotNull;
import static whartongitviewer.com.jakewhartongitviewer.util.EspressoIdlingResource.getIdlingResource;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    MainActivity activity;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void startActivity() {
        activity = mainActivityActivityTestRule.getActivity();
        Espresso.registerIdlingResources(EspressoIdlingResource.getIdlingResource());
    }

    @Test
    public void testFloatButton() {
        assertNotNull(activity.findViewById(R.id.fab));
        onView(withId(R.id.fab)).perform(click());
       // onView(withId(R.id.progress_main_activity)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.rec_view_main_activity)).check(matches(isDisplayed()));

    }
}