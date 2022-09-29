package uz.exemple.notes_room_java.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes_table")
public class Notes {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "note")
    public String note;
    @ColumnInfo(name = "date")
    public String date;

    public Notes() {}
    @Ignore
    public Notes(String note, String date) {
        this.id = 0;
        this.note = note;
        this.date = date;
    }
    @Ignore
    public Notes( int id,String note, String date) {
        this.id = id;
        this.note = note;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
