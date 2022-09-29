package uz.exemple.notes_room_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import uz.exemple.notes_room_java.adapter.NotesAdapter;
import uz.exemple.notes_room_java.database.NotesRepository;
import uz.exemple.notes_room_java.model.Notes;

public class MainActivity extends AppCompatActivity {
    FrameLayout btn_add;
    RecyclerView recyclerView;
    EditText et_note;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    void initViews(){
        context = this;
        btn_add = findViewById(R.id.btn_add);
        recyclerView = findViewById(R.id.recyclerview);
        et_note = findViewById(R.id.et_note);

        GridLayoutManager manager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(manager);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlert();
            }
        });
        NotesAdapter adapter = new NotesAdapter(this,getNotes());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Log.d("@@@","Posittion - "+position);
                        Notes item = getNotes().get(position);
                        updateAlert(item.getId(),item.getNote());
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }
    void openAlert(){
        EditText editText = new EditText(this);
        editText.setHint("Enter Your Note");
        editText.setHintTextColor(Color.parseColor("#C6C6C6"));
        editText.setPadding(32,0,16,32);
        editText.setHeight(100);
        TextView titleView = new TextView(context);
        titleView.setText("New Note");
        titleView.setGravity(Gravity.LEFT);
        titleView.setPadding(20, 20, 20, 5);
        titleView.setTextSize(20F);
        titleView.setTypeface(Typeface.DEFAULT_BOLD);
        //titleView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        titleView.setTextColor(Color.parseColor("#00C6AE"));
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setCustomTitle(titleView)
                .setView(editText)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String note = editText.getText().toString().trim();
                        if (!note.isEmpty()){
                            saveNote(note);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                        }

                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#00C6AE"));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#00C6AE"));
            }
        });
        dialog.show();
    }
    void updateAlert(int id, String note){
        EditText editText = new EditText(this);
        editText.setText(note);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Update Note")
                .setView(editText)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String note = editText.getText().toString();
                        updateNote(id,note);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NotesRepository repository = new NotesRepository(getApplication());
                        repository.deleteNote(id);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    }
                })
                .setNeutralButton("Cancel", null)
                .create();
        dialog.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#00C6AE"));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#00C6AE"));
                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.parseColor("#00C6AE"));
            }
        });
        dialog.show();
    }
    void saveNote(String note) {
        String dateT = getTime();
        NotesRepository repository = new NotesRepository(getApplication());
        Notes new_note = new Notes(note,dateT);
        repository.saveNote(new_note);
        //new UserAsyncTask(repository).execute(user);

    }
    void updateNote(int id,String note){
        String dateT = getTime();
        NotesRepository repository = new NotesRepository(getApplication());
        Notes new_note = new Notes(id,note,dateT);
        repository.saveNote(new_note);
    }
    ArrayList<Notes> getNotes(){
            NotesRepository repository = new NotesRepository(getApplication());
            ArrayList<Notes> all_notes = (ArrayList<Notes>) repository.getNotes();

        ArrayList<Notes> notes = new ArrayList<>();
        for (Notes n:all_notes){
            notes.add(n);
        }
        return notes;
    }
    String getTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("LLL dd");
        String dateT = simpleDateFormat.format(calendar.getTime()).toString();
        return dateT;
    }
}