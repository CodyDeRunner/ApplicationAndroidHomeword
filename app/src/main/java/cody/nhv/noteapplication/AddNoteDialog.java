package cody.nhv.noteapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AddNoteDialog extends AppCompatDialogFragment {
    private EditText edtNoteName, edtNoteDetail;
    private AddNoteDiaLogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Create builder for Dialog
        AlertDialog.Builder builder  = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_note, null);
        //Map to XML  file for Note Name and Note Detail
        edtNoteName     = view.findViewById(R.id.edtNoteName);
        edtNoteDetail   = view.findViewById(R.id.edtNoteDetail);
        //Organize builder
        builder.setView(view)
                .setTitle("Add new note")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add note", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String noteName     = edtNoteName.getText().toString();
                        String noteDetail   = edtNoteDetail.getText().toString();
                        listener.applyTexts(noteName, noteDetail);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (AddNoteDiaLogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "bạn chưa nhập thông tin Note");
        }
    }

    //Create Listener for Dialog
    public interface AddNoteDiaLogListener
    {
        void applyTexts(String noteName, String noteDetail);
    }
}
