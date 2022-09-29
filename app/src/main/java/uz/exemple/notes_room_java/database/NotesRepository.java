package uz.exemple.notes_room_java.database;

import android.app.Application;



import java.util.List;

import uz.exemple.notes_room_java.managers.RoomManager;
import uz.exemple.notes_room_java.model.Notes;

public class NotesRepository {
    private NoteDao noteDao;

    public NotesRepository(Application application) {
        RoomManager db = RoomManager.getDatabase(application);
        noteDao = db.noteDao();
    }

    public List<Notes> getNotes() {
        return noteDao.getNotes();
    }

    public void saveNote(Notes note) {
        noteDao.saveNotes(note);
    }
    public void deleteNote(int id){
        noteDao.deleteByNoteId(id);
    }

}
