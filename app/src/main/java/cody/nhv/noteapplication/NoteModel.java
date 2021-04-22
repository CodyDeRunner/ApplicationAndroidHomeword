package cody.nhv.noteapplication;

public class NoteModel {
    int NoteId;
    String NoteName, NoteDetail, NoteDate;
    int UserId;

    public int getNoteId() {
        return NoteId;
    }

    public void setNoteId(int noteId) {
        NoteId = noteId;
    }

    public String getNoteName() {
        return NoteName;
    }

    public void setNoteName(String noteName) {
        NoteName = noteName;
    }

    public String getNoteDetail() {
        return NoteDetail;
    }

    public void setNoteDetail(String noteDetail) {
        NoteDetail = noteDetail;
    }

    public String getNoteDate() {
        return NoteDate;
    }

    public void setNoteDate(String noteDate) {
        NoteDate = noteDate;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public NoteModel(int noteId, String noteName, String noteDetail, String noteDate, int userId) {
        NoteId = noteId;
        NoteName = noteName;
        NoteDetail = noteDetail;
        NoteDate = noteDate;
        UserId = userId;
    }
}
