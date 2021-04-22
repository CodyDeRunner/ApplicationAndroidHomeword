package cody.nhv.noteapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class NoteAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<NoteModel> NoteList;

    public NoteAdapter(Context context, int layout, List<NoteModel> noteList) {
        this.context = context;
        this.layout = layout;
        NoteList = noteList;
    }

    @Override
    public int getCount() {
        return NoteList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);

        TextView txtNoteName    = (TextView) convertView.findViewById(R.id.txtNoteName);
        TextView txtNoteDate    = (TextView) convertView.findViewById(R.id.txtNoteDate);
        TextView txtNoteDetail  = (TextView) convertView.findViewById(R.id.txtNoteDetail);

        NoteModel Note = NoteList.get(position);

        txtNoteName.setText(Note.getNoteName());
        txtNoteDate.setText(Note.getNoteDate());
        txtNoteDetail.setText(Note.getNoteDetail());
        return convertView;
    }
}
