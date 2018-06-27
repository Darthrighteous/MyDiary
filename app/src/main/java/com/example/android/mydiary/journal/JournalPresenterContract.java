package com.example.android.mydiary.journal;

/**
 * Created by Jalil on 26/06/2018.
 */

public interface JournalPresenterContract {

    void loadEntries();

    void openEntryDetails();

    void openSettings();

    void logout();

    void addNewEntry();

}
