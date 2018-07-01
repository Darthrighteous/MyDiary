package com.example.android.mydiary;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.mydiary.data.JournalEntry;
import com.example.android.mydiary.journal.JournalActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;

/**
 * Created by Jalil on 01/07/2018.
 */

@RunWith(AndroidJUnit4.class)
public class JournalActivityScreenTest {

    private final static String TEST_TITLE1 = "mock title 1";
    private final static String TEST_TITLE2 = "mock title 2";
    private final static String TEST_DESCRIPTION1 = "first mock description";
    private final static String TEST_DESCRIPTION2 = "second mock description";

    @Rule
    public ActivityTestRule<JournalActivity> mActivityTestRule =
            new ActivityTestRule<>(JournalActivity.class);


//    private static Matcher<JournalEntry> withTitle (final String title) {
//        return new TypeSafeMatcher<JournalEntry>() {
//            @Override
//            protected boolean matchesSafely(JournalEntry item) {
//                return title.equals(item.getTitle());
//            }
//
//            @Override
//            protected void describeMismatchSafely(JournalEntry item, Description mismatchDescription) {
//                mismatchDescription.appendText("actual title: " + item.getTitle());
//            }
//
//            @Override
//            public void describeTo(Description description) {
//                description.appendText("expected title: " + title);
//            }
//        };
//    }



    @Test
    public void clickAddEntry_OpensAddEntryActivity () {
        //perform click on add entry button
        onView(withId(R.id.fab_open_new_entry))
                .perform(click());

        onView(withId(R.id.edit_add_entry_title))
                .check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void clickListItem_OpensAddEntryActivity () {
        //first, create a new entry
        createJournalEntry(TEST_TITLE1, TEST_DESCRIPTION1);

        //click on the entry
        onData(is(instanceOf(JournalEntry.class)))
                .inAdapterView(withId(R.id.list_view_journal))
                .perform(click());

        //check if the add entry done fab is visible
        onView(withId(R.id.edit_add_entry_title))
                .check(matches(ViewMatchers.isDisplayed()));

    }

    private void createJournalEntry (String title, String body) {
        //click add new entry button
        onView(withId(R.id.fab_open_new_entry))
                .perform(click());

        //type text into title edit text
        onView(withId(R.id.edit_add_entry_title))
                .perform(typeText(title), closeSoftKeyboard());

        //type text into body edit text
        onView(withId(R.id.edit_add_entry_body))
                .perform(typeText(body), closeSoftKeyboard());

        //click save entry button
        onView(withId(R.id.fab_add_entry_done))
                .perform(click());
    }
}
