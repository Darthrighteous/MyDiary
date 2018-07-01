package com.example.android.mydiary.journal;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.mydiary.R;
import com.example.android.mydiary.addentry.AddEntryActivity;
import com.example.android.mydiary.data.JournalEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class JournalFragment extends Fragment implements JournalContract.View {

    private JournalContract.Presenter mPresenter;

    private JournalEntryAdapter mAdapter;

    public JournalFragment() {
        // Required empty public constructor
    }

    public static JournalFragment newInstance() {
        return new JournalFragment();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String snackbarText = "unknown";
        if (requestCode == AddEntryActivity.REQUEST_ADD_ENTRY) {
            if(resultCode == Activity.RESULT_OK) {
                snackbarText = "Journal Entry added";
            } else if (resultCode == Activity.RESULT_CANCELED) {
                snackbarText = "Cancelled";
            }
        } else if (requestCode == AddEntryActivity.REQUEST_EDIT_ENTRY) {
            if(resultCode == Activity.RESULT_OK) {
                snackbarText = "Changes saved";
            } else if (resultCode == Activity.RESULT_CANCELED) {
                snackbarText = "Cancelled";
            } else if (resultCode == AddEntryActivity.RESULT_DELETED) {
                snackbarText ="Entry Deleted";
            }
        }

        try {
            Snackbar.make(getActivity().findViewById(R.id.fab_open_new_entry),
                    snackbarText,
                    Snackbar.LENGTH_LONG).show();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    JournalEntryAdapter.EntryClickListener mEntryClickListener = new JournalEntryAdapter.EntryClickListener() {
        @Override
        public void onEntryClick(JournalEntry clickedEntry) {
            //edit entry
            Intent editEntryIntent = new Intent(getContext(), AddEntryActivity.class);
            Bundle editEntryBundle = new Bundle();
            editEntryBundle.putString(JournalContract.ENTRY_DATE_CREATED, clickedEntry.getDateCreated());
            editEntryBundle.putString(JournalContract.UNIQUE_USER_ID, mPresenter.getUId());
            editEntryBundle.putString(JournalContract.UNIQUE_ENTRY_ID, clickedEntry.getUniqueId());
            editEntryBundle.putString(JournalContract.ENTRY_TITLE, clickedEntry.getTitle());
            editEntryBundle.putString(JournalContract.ENTRY_BODY, clickedEntry.getBody());

            editEntryIntent.putExtra(JournalContract.EDIT_ENTRY_BUNDLE, editEntryBundle);


            editEntryIntent.putExtra(JournalContract.UNIQUE_USER_ID, mPresenter.getUId());
            editEntryIntent.putExtra(JournalContract.UNIQUE_ENTRY_ID, clickedEntry.getUniqueId());
            editEntryIntent.putExtra(JournalContract.ENTRY_TITLE, clickedEntry.getTitle());
            editEntryIntent.putExtra(JournalContract.ENTRY_BODY, clickedEntry.getBody());

            startActivityForResult(editEntryIntent, AddEntryActivity.REQUEST_EDIT_ENTRY);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new JournalEntryAdapter(getContext(),
                R.layout.item_journal_entry,
                new ArrayList<JournalEntry>(0),
                mEntryClickListener);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_journal, container, false);

        //listview setup
        ListView journalList = rootView.findViewById(R.id.list_view_journal);
        journalList.setAdapter(mAdapter);

        //swipe refresh
        SwipeRefreshLayout swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_journal);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadEntries();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadEntries();

    }

    @Override
    public void clear() {
        //clear adapter and stop loading indicator
        mAdapter.clear();
        setLoadingIndicator(false);
    }

    @Override
    public void showJournalEntries(List<JournalEntry> entries) {
        mAdapter.updateData(entries);
        setLoadingIndicator(false);
    }

    @Override
    public void showAddEntryActivity() {
        Intent addEntryIntent = new Intent(getContext(), AddEntryActivity.class);
        addEntryIntent.putExtra(JournalContract.UNIQUE_USER_ID, mPresenter.getUId());
        startActivityForResult(addEntryIntent, AddEntryActivity.REQUEST_ADD_ENTRY);
    }


    @Override
    public void showNoEntriesMessage(boolean show) {
        try {
            TextView textView = getView().findViewById(R.id.text_no_entries);
            if(show) {
                textView.setVisibility(View.VISIBLE);
            } else {
                textView.setVisibility(View.INVISIBLE);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setLoadingIndicator(boolean loading) {
        try {
            SwipeRefreshLayout swipeRefreshLayout = getView()
                    .findViewById(R.id.swipe_refresh_journal);
            swipeRefreshLayout.setRefreshing(loading);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setPresenter(JournalContract.Presenter presenter) {
        mPresenter = presenter;
    }

}

