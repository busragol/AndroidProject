package com.example.books;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class RecyclerView_Config {
    FirebaseAuth mAuth;
    private static FirebaseUser user;
    private Context mContext;
    private BooksAdapter mBooksAdapter;
    public void setConfig(RecyclerView recyclerView,Context context,List<Book> books,List<String> keys )
    {
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        mContext=context;
        mBooksAdapter=new BooksAdapter(books,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mBooksAdapter);
    }

    class BookItemView extends RecyclerView.ViewHolder
    {
        private TextView mTitle;
        private TextView mAuthor;
        private TextView mFEE;
        private TextView mCategory;

        private String key;

        public BookItemView(ViewGroup parent)
        {
            super(LayoutInflater.from(mContext).inflate(R.layout.book_list_item,parent,false));
            mTitle=(TextView)itemView.findViewById(R.id.title_txtView);
            mAuthor=(TextView)itemView.findViewById(R.id.author_txtView);
            mFEE=(TextView)itemView.findViewById(R.id.fee_txtView);
            mCategory=(TextView)itemView.findViewById(R.id.category_txtView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(user!=null)
                    {
                        Intent intent=new Intent(mContext,BookDetailsActivity.class);
                        intent.putExtra("key",key);
                        intent.putExtra("author",mAuthor.getText().toString());
                        intent.putExtra("title",mTitle.getText().toString());
                        intent.putExtra("category",mCategory.getText().toString());
                        intent.putExtra("fee",mFEE.getText().toString());
                        mContext.startActivity(intent);
                    }
                    else
                    {
                        mContext.startActivity(new Intent(mContext,SignInActivity.class));
                    }

                }
            });
        }
        public void bind(Book book,String key)
        {
            mTitle.setText(book.getTitle());
            mAuthor.setText(book.getAuthor());
            mFEE.setText(book.getFee());
            mCategory.setText(book.getCategory_name());
            this.key=key;
        }

    }
    class BooksAdapter extends RecyclerView.Adapter<BookItemView>
    {
        private List<Book> mBookList;
        private List<String> mKeys;

        public BooksAdapter(List<Book> mBookList, List<String> mKeys) {
            this.mBookList = mBookList;
            this.mKeys = mKeys;
        }

        @Override
        public BookItemView onCreateViewHolder( ViewGroup parent, int viewType) {
            return new BookItemView(parent);
        }

        @Override
        public void onBindViewHolder( BookItemView holder, int position) {
            holder.bind(mBookList.get(position),mKeys.get(position));

        }

        @Override
        public int getItemCount() {
            return mBookList.size();
        }
    }

    public static void logout()
    {
        user=null;
    }
}
