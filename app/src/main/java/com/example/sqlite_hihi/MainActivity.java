package com.example.sqlite_hihi;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText etnhapmaso,etnhaptieude,ettentacgia;
    Button btnexit,btnselect,btnsave,btndelete,btnupdate;
    GridView gridview1;
    BDHelper dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etnhapmaso = (EditText)findViewById(R.id.etnhapmaso);
        etnhaptieude = (EditText)findViewById(R.id.etnhaptieude);
        ettentacgia = (EditText)findViewById(R.id.ettentacgia);
        gridview1 = (GridView)findViewById(R.id.gridview1);
        dbhelper = new BDHelper(this);
        btnsave = (Button)findViewById(R.id.btnsave);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = new Book();
                book.setId(Integer.parseInt(etnhapmaso.getText().toString()));
                book.setTitle(etnhaptieude.getText().toString());
                book.setAuthor(ettentacgia.getText().toString());
                if(dbhelper.insertbook(book))
                    Toast.makeText(getApplicationContext(),"Đã lưu thành công",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(),"Lưu không thành công",Toast.LENGTH_LONG).show();
            }
        });
        btnexit = (Button)findViewById(R.id.btnexit);
        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnselect = (Button)findViewById(R.id.btnselect);
        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> list = new ArrayList<>();
                ArrayList<Book> booklist = new ArrayList<>();

                try{
                    booklist = dbhelper.getBook(Integer.parseInt(etnhapmaso.getText().toString()));

                }catch (Exception e){
                    booklist = dbhelper.getAllBook();
                }
                for(Book b:booklist){
                    list.add(b.getId()+"");
                    list.add(b.getTitle()+"");
                    list.add(b.getAuthor()+"");
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,list);
                gridview1.setAdapter(adapter);
            }
        });

    }
}
