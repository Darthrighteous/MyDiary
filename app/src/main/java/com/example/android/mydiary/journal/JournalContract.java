package com.example.android.mydiary.journal;

import com.example.android.mydiary.BasePresenter;
import com.example.android.mydiary.BaseView;
import com.example.android.mydiary.data.JournalEntry;
import com.firebase.ui.auth.AuthUI;

import java.util.List;

/**
 * Created by Jalil on 28/06/2018.
 */

public interface JournalContract {
    int RC_SIGN_IN = 25;

    interface View extends BaseView<Presenter> {
        void displayUserInfo(String username, String emailAddress);

        void showSignInFlow(List<AuthUI.IdpConfig> providers);

        void clearAdapter();

        void addEntry(JournalEntry journalEntry);

        void showJournalEntries();

        void showEditEntry();

        void showAddEntry();

    }

    interface Presenter extends BasePresenter {

        void attachDatabaseReadListener();

        void detachDatabaseReadListener();

        void signIn(String username, String emailAddress);

        void signOut();

        void addNewEntry();

        void openEntryDetails();

        void openSettings();

    }

}
