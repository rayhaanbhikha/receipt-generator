package com.rayhaan.receipt_generator;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private AlertDialog dialog;
    private EditText etPrice;
    private EditText etDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started.");

        ListView mListView = (ListView) findViewById(R.id.invoice_items);

        Item item1 = new Item(5.00, "Gold dress");
        Item item2 = new Item(45.00, "Blue dress with Lining");
        Item item3 = new Item(53.00, "Pink dress repair");
        Item item4 = new Item(70.00, "silver dress with embroidery");


        ArrayList<Item> invoiceList = new ArrayList<>();
        invoiceList.add(item1);
        invoiceList.add(item2);
        invoiceList.add(item3);
        invoiceList.add(item4);


        CustomListAdapter adapter = new CustomListAdapter(this, R.layout.row_item, invoiceList);

        mListView.setAdapter(adapter);
    }

    public void openDialog(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.add_new_item_dialog, null);
        etPrice = (EditText) mView.findViewById(R.id.dialog_price);
        etDesc = (EditText) mView.findViewById(R.id.dialog_description);
        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();
    }

    public void submitItem(View view) {
        dialog.cancel();
        Toast.makeText(MainActivity.this, "DIALOG SUBMITTED", Toast.LENGTH_LONG).show();
        System.out.println("HELLO" + etPrice.getText().toString() + " : " + etDesc.getText().toString());
    }


}