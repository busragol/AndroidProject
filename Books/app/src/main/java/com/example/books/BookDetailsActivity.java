package com.example.books;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class BookDetailsActivity extends AppCompatActivity {
    private EditText mAuthor_editText,mTitle_editText,mFEE_editText;
    private Spinner mBook_categories_spinner;
    private Button mUpdate_btn,mDelete_btn,mBack_btn;

    private String key,title,author,fee,category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        key=getIntent().getStringExtra("key");
        title=getIntent().getStringExtra("title");
        author=getIntent().getStringExtra("author");
        fee=getIntent().getStringExtra("fee");
        category=getIntent().getStringExtra("category");

        mAuthor_editText=(EditText)findViewById(R.id.author_editTxt);
        mAuthor_editText.setText(author);
        mTitle_editText=(EditText)findViewById(R.id.title_editTxt);
        mTitle_editText.setText(title);
        mFEE_editText=(EditText)findViewById(R.id.fee_editTxt);
        mFEE_editText.setText(fee);
        mBook_categories_spinner=(Spinner)findViewById(R.id.book_categories_spinner);
        mBook_categories_spinner.setSelection(getIndex_SpinnerItem(mBook_categories_spinner,category));

        mUpdate_btn=(Button)findViewById(R.id.update_btn);
        mDelete_btn=(Button)findViewById(R.id.delete_btn);
        mBack_btn=(Button)findViewById(R.id.back_btn);
        mUpdate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book=new Book();
                book.setTitle(mTitle_editText.getText().toString());
                book.setAuthor(mAuthor_editText.getText().toString());
                book.setFee(mFEE_editText.getText().toString());
                book.setCategory_name(mBook_categories_spinner.getSelectedItem().toString());

                new FirebaseDatabaseHelper().updateBook(key,book, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Book> books, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(BookDetailsActivity.this,"Book record has been"+
                                " updated successfully",Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });
        mDelete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FirebaseDatabaseHelper().deleteBook(key, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Book> books, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {
                        Toast.makeText(BookDetailsActivity.this,"Book record has been"+
                                " deleted successfully",Toast.LENGTH_LONG).show();
                        finish(); return;

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
    private int getIndex_SpinnerItem(Spinner spinner,String item)
    {
        int index=0;
        for(int i=0;i<spinner.getCount();i++)

        {
            index=i;
            break;
        }
        return index;
    }
}
