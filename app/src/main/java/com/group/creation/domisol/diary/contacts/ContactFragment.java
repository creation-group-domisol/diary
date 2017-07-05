package com.group.creation.domisol.diary.contacts;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.group.creation.domisol.diary.MainActivity;
import com.group.creation.domisol.diary.R;
import com.group.creation.domisol.diary.Swipable;

import java.util.ArrayList;

/**
 * Created by cob on 2017. 3. 29..
 */

public class ContactFragment extends Fragment implements Swipable {

    private int currentPage = 0;
    private SQLiteDatabase db;
//    private TextView pageNumber;
    private ContactItemListAdapter itemAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity) getContext();
        db = mainActivity.getReadableDatabase();

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_contacts, container, false);
        ListView contactListContainer = (ListView) rootView.findViewById(R.id.contact_list_container);

        itemAdapter = new ContactItemListAdapter(getContext(), R.layout.contact_list_item, new ArrayList<ContactItem>());
        itemAdapter.init();

//        pageNumber = (TextView) rootView.findViewById(R.id.page_number);
//        pageNumber.setText(currentPage+"");
        contactListContainer.setAdapter(itemAdapter);

        loadContact();
        return rootView;
    }

    private void saveContacts(int page){
        for (int i = 0; i < itemAdapter.getCount(); i++) {
            ContactItem item = (ContactItem) itemAdapter.getItem(i);
            saveContact(item, page, i);
        }
    }

    private void saveContact(ContactItem contactItem, int page, int idx){
        String name = contactItem.getName();
        String email = contactItem.getEmail();
        String phone = contactItem.getPhone();

        Cursor curPageCursor = db.rawQuery("select * from CONTACT where page = ? and idx = ?", new String[]{page + "", idx + ""});
        if (curPageCursor.getCount() < 1 && (
                (name != null && !name.equals("")) ||
                (email != null && !email.equals("")) ||
                (phone != null && !phone.equals("")))) {
            db.execSQL("insert into CONTACT (name, email, phone, page, idx) values(?, ?, ?, ?, ?)", new Object[]{name, email, phone, page, idx});
        } else if(curPageCursor.getCount() > 0){
            db.execSQL("update CONTACT set name = ?, email = ?, phone = ? where page = ? and idx = ?", new Object[]{name, email, phone, page, idx});
        }
        curPageCursor.close();
    }

    private void loadContacts(int page) {
        Cursor curPageCursor = db.rawQuery("select * from CONTACT where page = ?", new String[]{page + ""});
        if (curPageCursor.getCount() < 1)
            return;
        for (int i = 0; i < curPageCursor.getCount(); i++) {
            curPageCursor.moveToNext();

            int idx = curPageCursor.getInt(2);
            String name = curPageCursor.getString(3);
            String email = curPageCursor.getString(4);
            String phone = curPageCursor.getString(5);

            itemAdapter.setItem(idx, name, email, phone);
        }
        curPageCursor.close();
    }

    @Override
    public void swipeLeft() {
        saveContacts();
        currentPage++;
        loadContact();
    }

    private void saveContacts() {
        saveContacts(currentPage);
        itemAdapter.clear();
    }

    @Override
    public void swipeRight() {
        if (currentPage == 0) {
            return;
        }
        saveContacts();
        currentPage--;
        loadContact();
    }

    private void loadContact() {
        loadContacts(currentPage);
        itemAdapter.notifyDataSetChanged();
//        pageNumber.setText(currentPage+"");
    }
}
