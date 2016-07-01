package com.deuxlapins.amsterdam;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import com.deuxlapins.amsterdam.view.MainActivity;
import com.deuxlapins.amsterdam.vo.Forecast;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;


/**
 * Tests various features in the MainActivity
 * Created by chetan on 30/06/16.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTests {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Tests that the correct place name is displayed
     */
    @Test
    public void testWeatherView() {
        onView(withId(R.id.placeName))
                .check(matches(withText("Amsterdam")));
    }

    /**
     * Tests that the forecast list displays forecast from up to 10 days
     */
    @Test
    public void testForecastList() {
        onData(instanceOf(Forecast.class))
                .inAdapterView(allOf(withId(R.id.forecastList), isDisplayed()))
                .atPosition(9)
                .check(matches(isDisplayed()));
    }

    /**
     * Tests that changing units in the settings updates the view as expected
     */
    @Test
    public void testUnitsChangedInSettings() {
        String currentUnitsInView = getText(withId(R.id.currentUnits));
        if (currentUnitsInView != null) {
            boolean wasFahrenheit = "F".equals(currentUnitsInView);

            onView(withId(R.id.action_settings)).perform(click());
            onView(withText("Temperature")).perform(click());
            if (wasFahrenheit) {
                onView(withText("Celsius")).perform(click());
            } else {
                onView(withText("Fahrenheit")).perform(click());
            }
            Espresso.pressBack();
            onView(withId(R.id.currentUnits)).check(matches(withText(wasFahrenheit ? "C" : "F")));
        }

    }

    /**
     * A utility function that returns the text from a TextView
     * @param matcher
     * @return The text from the TextView or null
     */
    String getText(final Matcher<View> matcher) {

        final String[] holder = {null};

        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "retrieves the text from a TextView as String.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView textView = (TextView) view;
                holder[0] = textView.getText().toString();
            }
        });
        return holder[0];
    }

}

