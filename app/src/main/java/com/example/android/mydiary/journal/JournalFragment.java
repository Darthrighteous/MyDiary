package com.example.android.mydiary.journal;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.mydiary.R;
import com.example.android.mydiary.addentry.AddEntryActivity;
import com.google.firebase.database.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class JournalFragment extends Fragment implements JournalContract.View {

    private JournalContract.Presenter mPresenter;


    public JournalFragment() {
        // Required empty public constructor
    }

    public static JournalFragment newInstance() {
        return new JournalFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_journal, container, false);


        FloatingActionButton fab = getActivity().findViewById(R.id.fab_open_new_entry);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addNewEntry();
            }
        });

        return rootView;
    }

    @Override
    public void showJournalEntries(Query query) {

    }

    @Override
    public void showEditEntry() {

    }

    @Override
    public void showAddEntry() {
        Intent addEntryIntent = new Intent(getContext(), AddEntryActivity.class);
        startActivityForResult(addEntryIntent, AddEntryActivity.REQUEST_ADD_ENTRY);
    }

    @Override
    public void setPresenter(JournalContract.Presenter presenter) {
        mPresenter = presenter;
    }

}

