package com.example.books;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class NewBookActivity extends AppCompatActivity {
    private EditText mAuthor_editText,mTitle_editText,mFEE_editText;
    private Spinner mBook_categories_spinner;
    private Button mAdd_btn,mBack_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book);
        mAuthor_editText=(EditText)findViewById(R.id.author_editTxt);
        mTitle_editText=(EditText)findViewById(R.id.title_editTxt);
        mFEE_editText=(EditText)findViewById(R.id.fee_editTxt);
        mBook_categories_spinner=(Spinner)findViewById(R.id.book_categories_spinner);

        mAdd_btn=(Button)findViewById(R.id.add_btn);
        mBack_btn=(Button)findViewById(R.id.back_btn);
        mAdd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book=new Book();
                book.setAuthor(mAuthor_editText.getText().toString());
                book.setTitle(mTitle_editText.getText().toString());
                book.setFee(mFEE_editText.getText().toString());
                book.setCategory_name(mBook_categories_spinner.getSelectedItem().toString());

                new FirebaseDatabaseHelper().addBook(book, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Book> books, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(NewBookActivity.this,"The book record has"+
                                " been inserted successfully",Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });
        mBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); return;

            }
        });
    }
}
