package cody.nhv.noteapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class NoteActivity extends AppCompatActivity implements AddNoteDialog.AddNoteDiaLogListener {
    public Database database = new Database (this, "noteApplication.sql", null, 1);
    ListView lvNotes;
    ArrayList<NoteModel> notes;
    NoteAdapter noteAdapter;
    int userId = -1;
    FloatingActionButton btnAddNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Intent intentSignIn = getIntent();
        //Catch Intent from Sign In form.
        userId = intentSignIn.getIntExtra("UserId", -1);
        //Map components to XML file and add Notes to list
        Mapping();

        noteAdapter = new NoteAdapter(this, R.layout.note_item, notes);

        //Set Adapter for ListView Notes in Actitity_Note
        Log.i("UserId", String.valueOf(userId));
        lvNotes.setAdapter(noteAdapter);

        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNote();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void Mapping()
    {
        lvNotes = (ListView) findViewById(R.id.lvNote);

        notes = new ArrayList<>();
        Cursor dataNote = database.GetData("SELECT * FROM Note WHERE User_id =  " + userId);

        while(dataNote.moveToNext())
        {
            int noteId = dataNote.getInt(0);
            String noteName = dataNote.getString(1);
            String noteDate = dataNote.getString(3);
            String noteDetail = dataNote.getString(2);

            notes.add(new NoteModel(noteId, noteName, noteDetail, noteDate, userId));
        }
        Log.i("Note", String.valueOf(notes.size()));
        btnAddNote = (FloatingActionButton) findViewById(R.id.btnAddNote);
    }

    public void AddNote()
    {
        AddNoteDialog addNote = new AddNoteDialog();

        addNote.show(getSupportFragmentManager(), "Add new note");
    }

    @Override
    public void applyTexts(String noteName, String noteDetail) {
        String sqlQuery = "Insert into Note values (NULL,'"+noteName+"','"+noteDetail+"',DATEtime('now'), "+userId+");";
        database.QueryData(sqlQuery);
        finish();
        startActivity(getIntent());
    }
}