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

    interface View extends BaseView<Presenter> {

        void clearAdapter();

        void showJournalEntries(List<JournalEntry> entries);

        void showAddEntryActivity(String userUniqueId);

    }

    interface Presenter extends BasePresenter {

        void loadEntries();

        void processEntries(List<JournalEntry> entries);

        void openEntryDetails();

        void openSettings();

    }

}
