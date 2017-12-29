package br.com.githubprofile.activities;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.githubprofile.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

/**
 * Created by Priscylla-SSD-2016 on 28/12/2017.
 * Testa se a pesquisa está funcionando e se os dados consumidos da API aparecem na tela.
 * */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void waitForAction() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void mainActivityTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.et_search),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("rafael"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.bt_search), withText("Pesquisar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1),
                        isDisplayed()));
        appCompatButton.perform(click());

        waitForAction();

        ViewInteraction textView = onView(
                allOf(withId(R.id.tv_login), withText("rafael"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_usersList),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("rafael")));

        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.lv_usersList),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                2)))
                .atPosition(0);
        linearLayout.perform(click());

        waitForAction();

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.tv_username), withText("rafael"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("rafael")));

        waitForAction();

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.tv_repoName), withText("active_model_serializers"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("active_model_serializers")));
        waitForAction();

        pressBack();

        waitForAction();

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.et_search),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.et_search),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("Priscylla"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.bt_search), withText("Pesquisar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1),
                        isDisplayed()));
        appCompatButton2.perform(click());

        waitForAction();

        DataInteraction linearLayout2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.lv_usersList),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                2)))
                .atPosition(0);
        linearLayout2.perform(click());

        waitForAction();

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.tv_repoName), withText("advanced-programming-course"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView6.check(matches(withText("advanced-programming-course")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.tv_langage), withText("C++"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView7.check(matches(withText("C++")));

        pressBack();
        waitForAction();


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
}
