package uz.exemple.notes_room_java.managers;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import uz.exemple.notes_room_java.database.NoteDao;
import uz.exemple.notes_room_java.model.Notes;


@Database(entities = {Notes.class}, version = 1)
public abstract class RoomManager extends RoomDatabase {

    public abstract NoteDao noteDao();
    private static RoomManager INSTANCE;

    public static RoomManager getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomManager.class, "notes_db.sqlite")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
