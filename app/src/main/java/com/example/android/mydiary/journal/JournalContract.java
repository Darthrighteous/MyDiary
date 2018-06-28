package com.example.android.mydiary.journal;

import com.example.android.mydiary.BasePresenter;
import com.example.android.mydiary.BaseView;
import com.google.firebase.database.Query;

/**
 * Created by Jalil on 28/06/2018.
 */

public interface JournalContract {

    interface View extends BaseView<Presenter> {

        void showJournalEntries(Query query);

        void showEditEntry();

        void showAddEntry();

    }

    interface Presenter extends BasePresenter {
        void loadEntries();

        void addNewEntry();

        void openEntryDetails();

        void openSettings();

        void logout();


    }

}
