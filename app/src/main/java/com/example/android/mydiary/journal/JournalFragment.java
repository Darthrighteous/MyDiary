package com.example.android.mydiary.journal;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

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
        if (requestCode == AddEntryActivity.REQUEST_ADD_ENTRY) {
            if(resultCode == Activity.RESULT_OK) {
                Snackbar.make(getActivity().findViewById(R.id.fab_open_new_entry),
                        "Journal Entry added",
                        Snackbar.LENGTH_LONG).show();
            }
        } else if (requestCode == AddEntryActivity.REQUEST_EDIT_ENTRY) {
            if(resultCode == Activity.RESULT_OK) {
                Snackbar.make(getActivity().findViewById(R.id.fab_open_new_entry),
                        "Changes saved",
                        Snackbar.LENGTH_LONG).show();
            }
        }
    }

    JournalEntryAdapter.EntryClickListener mEntryClickListener = new JournalEntryAdapter.EntryClickListener() {
        @Override
        public void onEntryClick(JournalEntry clickedEntry) {
            //edit entry
            Intent editEntryIntent = new Intent(getContext(), AddEntryActivity.class);
            editEntryIntent.putExtra(JournalContract.UNIQUE_USER_ID, mPresenter.getUId());
            editEntryIntent.putExtra(JournalContract.UNIQUE_ENTRY_ID, clickedEntry.getUniqueId());
            editEntryIntent.putExtra(JournalContract.ENTRY_TITLE, clickedEntry.getTitle());
            editEntryIntent.putExtra(JournalContract.ENTRY_BODY, clickedEntry.getBody());

            startActivityForResult(editEntryIntent, AddEntryActivity.REQUEST_EDIT_ENTRY);
            Toast.makeText(getContext(), "entry clicked"+clickedEntry.getTitle(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new JournalEntryAdapter(getContext(),
                R.layout.item_journal_entry,
                new ArrayList<JournalEntry>(0),
                mEntryClickListener);

        mPresenter.start();
        mPresenter.loadEntries();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_journal, container, false);

        //listview setup
        ListView journalList = rootView.findViewById(R.id.list_view_journal);
        journalList.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void showJournalEntries(List<JournalEntry> entries) {
        mAdapter.updateData(entries);
    }

    @Override
    public void showAddEntryActivity() {
//        mPresenter.addEntry();
        Intent addEntryIntent = new Intent(getContext(), AddEntryActivity.class);
        addEntryIntent.putExtra(JournalContract.UNIQUE_USER_ID, mPresenter.getUId());
        startActivityForResult(addEntryIntent, AddEntryActivity.REQUEST_ADD_ENTRY);
    }

    @Override
    public void setPresenter(JournalContract.Presenter presenter) {
        mPresenter = presenter;
    }

}

