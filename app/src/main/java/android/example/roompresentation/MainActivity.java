package android.example.roompresentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = MainActivity.class.getSimpleName();
    private PersonDao dao;
    Person person;
    private Button btnAdd, btnDelete, btnSearch;
    private EditText ETname, ETemail;
    private TextView Tname, Temail;
    PersonDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ETname= findViewById(R.id.editTextName);
        ETemail = findViewById(R.id.editTextEmail);
        Tname = findViewById(R.id.textName);
        Temail = findViewById(R.id.textEmail);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        btnSearch = (Button)findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(MainActivity.this);

        db = PersonDatabase.getDatabase(this);
        dao = db.personDao();
    }

    @Override
    public void onClick(View view) {
        String name = ETname.getText().toString();
        String email = ETemail.getText().toString();
        Log.d(TAG, "starting onClick");
        if (btnAdd.equals(view)) {
            Log.d(TAG, "btnAdd");
            person = new Person(name,email);
            db.databaseWriteExecutor.execute(() -> {
                dao.insert(person);
            });
        } else if (btnDelete.equals(view)) {

            if(name!=null){
                AsyncTask.execute(()->{
                    dao.deletePerson(name);

                });
            }
            else if(email!=null){
                AsyncTask.execute(()->{
                    dao.deletePerson(email);

                });
            }
            Log.d(TAG, "btnDelete");

        }
        else if(btnSearch.equals(view)){
            Log.d(TAG, "btnSearch");
            if(email!=null){
                AsyncTask.execute(() -> {
                    Person p = (Person) dao.search(email);
                    Tname.setText(p.name);
                    Temail.setText(p.email);
                });
            }
            else if(name!=null){
                AsyncTask.execute(()->{
                    Person p;
                    p = (Person) dao.search(name);
                    Tname.setText(p.name);
                    Temail.setText(p.email);
                });

            }

        }
    }
}