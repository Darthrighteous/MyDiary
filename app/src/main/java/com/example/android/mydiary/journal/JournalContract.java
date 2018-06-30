package com.example.android.mydiary.journal;

import com.example.android.mydiary.BasePresenter;
import com.example.android.mydiary.BaseView;
import com.example.android.mydiary.data.JournalEntry;

import java.util.List;

/**
 * Created by Jalil on 28/06/2018.
 */

public interface JournalContract {
    int RC_SIGN_IN = 25;
    String UNIQUE_USER_ID = "user-uid";
    String UNIQUE_ENTRY_ID = "entry-uid";
    String ENTRY_TITLE = "entry-title";
    String ENTRY_BODY = "entry-body";

    interface View extends BaseView<Presenter> {

        void showJournalEntries(List<JournalEntry> entries);

        void showAddEntryActivity();

    }

    interface Presenter extends BasePresenter {
        String getUId();

        void loadEntries();

        void processEntries(List<JournalEntry> entries);

        void openEntryDetails();

        void openSettings();

    }

}
