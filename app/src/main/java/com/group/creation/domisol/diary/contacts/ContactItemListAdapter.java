package com.group.creation.domisol.diary.contacts;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by cob on 2017. 5. 15..
 */

public class ContactItemListAdapter extends ArrayAdapter<ContactItem> {
    private Context context;
    private List<ContactItem> contactItems;

    public ContactItemListAdapter(@NonNull Context context,
                                  @LayoutRes int resource,
                                  @NonNull List<ContactItem> objects) {
        super(context, resource, objects);
        this.context = context;
        contactItems = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactItemView itemView;
        if (convertView == null) {
            itemView = new ContactItemView(context, contactItems.get(position));
        } else {
            itemView = (ContactItemView) convertView;
        }

        if (contactItems.size()  > position)
            itemView.setContactItem(contactItems.get(position));

        return itemView;
    }

    public void init() {
        addItem(new ContactItem());
        addItem(new ContactItem());
        addItem(new ContactItem());
        addItem(new ContactItem());
    }

    public void clear() {
        for (ContactItem contactItem : contactItems) {
            contactItem = new ContactItem();
        }
    }

    public void setList(List<ContactItem> list) {
        this.contactItems = list;
    }

    public void addItem(ContactItem contactItem) {
        this.contactItems.add(contactItem);
    }

    public void setItem(int idx, String name, String email, String phone) {
        this.contactItems.set(idx, new ContactItem(name, email, phone));
    }
}
