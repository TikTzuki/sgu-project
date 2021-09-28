package com.tiktzuki.hyper_contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainActivity extends AppCompatActivity {
    Button btnExport, btnImport;
    ListView lvItem;
    List<ContactModel> items;
    ItemAdapter adapter;
    EditText edtExportFileName, edtImportFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnExport = findViewById(R.id.btnExport);
        btnImport = findViewById(R.id.btnImport);
        lvItem = findViewById(R.id.lvResource);
        edtExportFileName = findViewById(R.id.edtExportFileName);
        edtImportFileName = findViewById(R.id.edtImportFileName);

        items = loadContracts(this);
        adapter = new ItemAdapter(this, R.layout.contact_item, items);
        lvItem.setAdapter(adapter);


        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String file_name = edtExportFileName.getText().toString();
                exportData(file_name);
            }
        });

        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String file_name = edtImportFileName.getText().toString();
                importData(file_name);
            }
        });

        lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        items = loadContracts(this);
        adapter = new ItemAdapter(this, R.layout.contact_item, items);
        lvItem.setAdapter(adapter);
    }


    private List<ContactModel> loadContracts(Context context) {
        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        }
        List<ContactModel> list = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor cursorInfo = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id},
                            null
                    );

                    while (cursorInfo.moveToNext()) {
                        ContactModel info = new ContactModel();
                        info.id = id;
                        info.name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        info.phone = cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        list.add(info);
                    }

                    cursorInfo.close();
                }
            }
            cursor.close();
        }
        return list;
    }

    private void importData(String file_name) {
        if (checkSelfPermission(Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS}, PERMISSIONS_REQUEST_WRITE_CONTACTS);
        }
        BufferedReader reader;
        CSVReader csvReader;
        ArrayList<ContactModel> importContacts = new ArrayList<>();
        try {
            String[] nextLine;

            File file = new File(this.getExternalFilesDir(Environment.getStorageDirectory().toString()) + "/" + file_name + ".csv");
            if (!file.exists() || file.isDirectory()) {
                Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
                return;
            }

            FileInputStream fis = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
            csvReader = new CSVReader(reader);
            while ((nextLine = csvReader.readNext()) != null) {
                if (nextLine.length != 2)
                    continue;
                ContactModel item = new ContactModel(nextLine[0], nextLine[1]);
                importContacts.add(item);
            }
            List<ContactModel> oldItems = new ArrayList<>(this.items);
            for (ContactModel importContact : importContacts) {
                Optional<ContactModel> contactExisted = oldItems.stream().filter(c -> c.phone.equals(importContact.phone)).findAny();
                if (!contactExisted.isPresent()) {
//                    this.items.add(importContact);
                    Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, importContact.phone);
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, importContact.name);
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
                    startActivity(intent);
                }
            }
            ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportData(String file_name) {
        try {
            CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(
                    new FileOutputStream(this.getExternalFilesDir(Environment.getStorageDirectory().toString()) + "/" + file_name + ".csv", Boolean.parseBoolean("UTF-8"))
            ));
            for (ContactModel item : items) {
                String[] line = new String[]{item.name, item.phone};
                csvWriter.writeNext(line);
            }
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                loadContracts(this);
            } else {
                Toast.makeText(this, "You dont have permission!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static final int PERMISSIONS_REQUEST_WRITE_CONTACTS = 101;
}