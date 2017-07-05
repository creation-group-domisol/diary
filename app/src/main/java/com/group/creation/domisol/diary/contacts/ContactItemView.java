package com.group.creation.domisol.diary.contacts;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.group.creation.domisol.diary.R;

/**
 * Created by cob on 2017. 5. 15..
 */

public class ContactItemView extends LinearLayout {
    private final TextView nameView;
    private final TextView emailView;
    private final TextView phoneView;

    public ContactItemView(Context context, final ContactItem aItem) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.contact_list_item, this, true);

        String name = aItem.getName();
        String email = aItem.getEmail();
        String phone = aItem.getPhone();

        nameView = (TextView) findViewById(R.id.contact_name);
        emailView = (TextView) findViewById(R.id.contact_email);
        phoneView = (TextView) findViewById(R.id.contact_phone);


        nameView.addTextChangedListener(new ContactTextWatcher(aItem) {
            @Override
            public void afterTextChanged(Editable s) {
                this.contactItem.setName(s.toString());
            }
        });

        emailView.addTextChangedListener(new ContactTextWatcher(aItem) {
            @Override
            public void afterTextChanged(Editable s) {
                this.contactItem.setEmail(s.toString());
            }
        });

        phoneView.addTextChangedListener(new ContactTextWatcher(aItem) {
            @Override
            public void afterTextChanged(Editable s) {
                this.contactItem.setPhone(s.toString());
            }
        });

        setContactItem(aItem);
    }

    private static abstract class ContactTextWatcher implements TextWatcher{

        ContactItem contactItem;

        public ContactTextWatcher(ContactItem contactItem) {
            this.contactItem = contactItem;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
    }

    public void setContactItem(ContactItem contactItem) {
        nameView.setText(contactItem.getName());
        emailView.setText(contactItem.getEmail());
        phoneView.setText(contactItem.getPhone());
    }
}
