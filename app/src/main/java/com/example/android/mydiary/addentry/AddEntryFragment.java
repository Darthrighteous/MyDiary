package com.example.android.mydiary.addentry;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.android.mydiary.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEntryFragment extends Fragment implements AddEntryContract.View {
    private AddEntryContract.Presenter mPresenter;

    private EditText mEditTextTitle;
    private EditText mEditTextBody;

    public AddEntryFragment() {
        // Required empty public constructor
    }

    public static AddEntryFragment newInstance() {
         return new AddEntryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_entry_detail, container, false);

        mEditTextTitle = rootView.findViewById(R.id.edit_add_entry_title);
        mEditTextBody = rootView.findViewById(R.id.edit_add_entry_body);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab_add_entry_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mEditTextTitle.getText().toString();
                String body = mEditTextBody.getText().toString();
                mPresenter.saveEntry(title, body);
            }
        });
    }

    @Override
    public void setPresenter(AddEntryContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setTitle(String title) {
        mEditTextTitle.setText(title);
    }

    @Override
    public void setBody(String body) {
        mEditTextBody.setText(body);
    }

    @Override
    public void showEmptyEntryError() {
        Snackbar.make(mEditTextBody, getString(R.string.empty_entry_message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void finishActivity() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }
}
