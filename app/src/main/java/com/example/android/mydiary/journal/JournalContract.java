package com.example.android.mydiary.journal;

import com.example.android.mydiary.BasePresenter;
import com.example.android.mydiary.BaseView;
import com.example.android.mydiary.data.JournalEntry;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * Created by Jalil on 28/06/2018.
 */

public interface JournalContract {
    int RC_SIGN_IN = 25;
    String ARGUMENT_UNIQUE_USER_ID = "user-uid";
    String ARGUMENT_UNIQUE_ENTRY_ID = "entry-uid";
    String ARGUMENT_ENTRY_TITLE = "entry-title";
    String ARGUMENT_ENTRY_BODY = "entry-body";
    String ARGUMENT_ENTRY_DATE_CREATED = "entry-date-created";
    String BUNDLE_EDIT_ENTRY = "entry-bundle";

    interface View extends BaseView<Presenter> {

        void clear();

        void showJournalEntries(List<JournalEntry> entries);

        void showAddEntryActivity();

        void showNoEntriesMessage(boolean show);

        void setLoadingIndicator(boolean loading);

    }

    interface Presenter extends BasePresenter {

        void setUser(FirebaseUser user);

        String getUId();

        void loadEntries();

        void processEntries(List<JournalEntry> entries);

        void openSettings();

    }

}
