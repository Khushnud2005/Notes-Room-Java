package uz.exemple.notes_room_java.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import java.util.List;

import uz.exemple.notes_room_java.model.Notes;

@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long saveNotes(Notes note);

    @Query("select * from notes_table")
    List<Notes> getNotes();

    @Query("DELETE FROM notes_table WHERE id = :noteId")
    abstract void deleteByNoteId(int noteId);

}
