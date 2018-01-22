package com.example.warrier.readysetbake;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by Galahad on 02/11/2017.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeShareIntentTest {
    public static final String RECIPE_NAME = "Brownies";

    @Rule
    public IntentsTestRule<BaseActivity> mActivityRule = new IntentsTestRule<>(
            BaseActivity.class);

    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }
    @Test
    public void clickRecipe_LaunchActivityIntent() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Click on Recipe
        onView(withId(R.id.recipe_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(RECIPE_NAME)), click()));

        //Make sure a new detail activity is launched if we're on a non-tablet
        Context targetContext = InstrumentationRegistry.getTargetContext();
        targetContext.getResources().getBoolean(R.bool.isTablet);
        Boolean isTablet = targetContext.getResources().getBoolean(R.bool.isTablet);
        if(!isTablet) {
            intended(hasComponent(RecipeActivity.class.getName()));
        }
    }

}
