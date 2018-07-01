package com.example.android.mydiary;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.mydiary.addentry.AddEntryActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Jalil on 01/07/2018.
 */

@RunWith(AndroidJUnit4.class)
public class AddEntryActivityScreenTest {

    @Rule
    ActivityTestRule<AddEntryActivity> mActivityTestRule =
            new ActivityTestRule<>(AddEntryActivity.class);

    @Test
    public void clickDelete_DiscardsEntries() {
        //todo write this test

    }

}
