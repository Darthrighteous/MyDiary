package com.example.android.mydiary.journal;

import com.google.firebase.database.Query;

/**
 * Created by Jalil on 27/06/2018.
 */

public interface JournalViewContract {
    void showJournalEntries(Query query);

    void showEditEntry();

    void showAddEntry();

    void setPresenter(JournalPresenter presenter);
}
