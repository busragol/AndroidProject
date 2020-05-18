package com.example.books;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

//This is BookListActivity
public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView ;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();
        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerview_books);
        new FirebaseDatabaseHelper().readBooks(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Book> books, List<String> keys) {
                findViewById(R.id.loading_books_pb).setVisibility(View.GONE);
                new RecyclerView_Config().setConfig(mRecyclerView,MainActivity.this,books,keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        FirebaseUser user=mAuth.getCurrentUser();
        getMenuInflater().inflate(R.menu.booklist_activity_menu,menu);
        if(user!=null)
        {//user authenticated
            menu.getItem(0).setVisible(true);//new book
            menu.getItem(1).setVisible(false);//sign in /register
            menu.getItem(2).setVisible(true);//log out
        }
        else
        {//user is not authenticated
            menu.getItem(0).setVisible(false);//new book
            menu.getItem(1).setVisible(true);//sign in /register
            menu.getItem(2).setVisible(false);//log out
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null)
        {//user authenticated
            menu.getItem(0).setVisible(true);//new book
            menu.getItem(1).setVisible(false);//sign in /register
            menu.getItem(2).setVisible(true);//log out
        }
        else
        {//user is not authenticated
            menu.getItem(0).setVisible(false);//new book
            menu.getItem(1).setVisible(true);//sign in /register
            menu.getItem(2).setVisible(false);//log out
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.new_book:
                startActivity(new Intent(this,NewBookActivity.class));
                return true;
            case R.id.sign_in:
                startActivity(new Intent(this,SignInActivity.class));
                return true;
            case R.id.sign_out:
                mAuth.signOut();
                invalidateOptionsMenu();
                RecyclerView_Config.logout();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
