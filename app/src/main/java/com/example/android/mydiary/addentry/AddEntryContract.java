package com.example.android.mydiary.addentry;

import com.example.android.mydiary.BasePresenter;
import com.example.android.mydiary.BaseView;

/**
 * Created by Jalil on 28/06/2018.
 */

public interface AddEntryContract {

    interface View extends BaseView<Presenter> {

        void setTitle(String title);

        void setBody(String body);

        void showExistingEntry(String title, String body);

        void showEmptyEntryError();

        void finishActivity();

    }

    interface Presenter extends BasePresenter {

        void saveEntry(String title, String body);

        void deleteEntry ();

    }
}
