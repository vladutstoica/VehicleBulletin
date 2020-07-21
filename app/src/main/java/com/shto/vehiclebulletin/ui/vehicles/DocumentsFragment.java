package com.shto.vehiclebulletin.ui.vehicles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.shto.vehiclebulletin.R;
import com.shto.vehiclebulletin.ui.vehicles.dialog.AddDocumentDialogFragment;

public class DocumentsFragment extends Fragment {

    public DocumentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_documents, container, false);

        ExtendedFloatingActionButton fab = root.findViewById(R.id.documents_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When you want to show your dialog, create an instance of your DialogFragment
                // and call show(), passing the FragmentManager and a tag name for the dialog fragment.
                DialogFragment addDocumentDialog = new AddDocumentDialogFragment();
                addDocumentDialog.show(getParentFragmentManager(), "ADD DOCUMENT DIALOG");
            }
        });

        return root;
    }
}