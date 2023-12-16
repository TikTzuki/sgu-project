package com.tiktzuki.hyper_contacts;

import android.app.AlertDialog;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends ArrayAdapter<ContactModel> {
    List<ContactModel> items;

    public ItemAdapter(@NonNull Context context, int resource, List<ContactModel> items) {
        super(context, resource, items);
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View row = inflater.inflate(R.layout.contact_item, null);
        TextView txtPhone = row.findViewById(R.id.txtPhone);
        TextView txtName = row.findViewById(R.id.txtName);
        Button btnDeleteContact = row.findViewById(R.id.btnDeleteContact);

        txtName.setText(items.get(position).name);
        txtPhone.setText(items.get(position).phone);

        btnDeleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = DialogUtil.createAlertDialog(getContext(), "Are you sure", "Delete contact",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteContact(getContext(), items.get(position).id);
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                getContext().startActivity(intent);
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                dialog.show();
            }
        });
        return row;
    }

    public static void deleteContact(Context context, String contactId) {
        ContentResolver contentResolver = context.getContentResolver();
        ArrayList<ContentProviderOperation> ops = new
                ArrayList<ContentProviderOperation>();
        String[] args = new String[]{String.valueOf(contactId)};
        ops.add(
                ContentProviderOperation.newDelete(
                        ContactsContract.RawContacts.CONTENT_URI
                ).withSelection(
                        ContactsContract.RawContacts.CONTACT_ID + "=?", args
                ).build()
        );
        try {
            contentResolver.applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (RemoteException | OperationApplicationException e) {
            e.printStackTrace();
        }
    }
}
