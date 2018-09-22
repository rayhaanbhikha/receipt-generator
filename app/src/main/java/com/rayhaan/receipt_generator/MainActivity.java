package com.rayhaan.receipt_generator;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private AlertDialog dialog;
    private EditText etPrice;
    private EditText etDesc;
    private ArrayList<Item> invoiceList;
    private CustomListAdapter adapter;
    private String total;
    private TextView totatPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started.");
        totatPrice = (TextView) findViewById(R.id.total_price);
        final ListView mListView = (ListView) findViewById(R.id.invoice_items);
        invoiceList = new ArrayList<>();

        invoiceList.add(new Item("£5.00", "Gold dress"));
        invoiceList.add(new Item("£45.30", "Blue dress with Lining"));
        invoiceList.add(new Item("£53.07", "Pink dress repair"));
        invoiceList.add(new Item("£70.00", "silver dress with embroidery"));


        calculateTotal();
        adapter = new CustomListAdapter(this, R.layout.row_item, invoiceList);

        mListView.setAdapter(adapter);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // dialog box to appear with yes and no question.
                removeItemFromInvoiceList(position);
                return true;
            }
        });
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
        String price = etPrice.getText().toString();
        String desc = etDesc.getText().toString();
        if(price.isEmpty() || desc.isEmpty()){
            Toast.makeText(MainActivity.this, "Please fill in empty fields", Toast.LENGTH_LONG).show();
        } else  {
            dialog.cancel();
            Toast.makeText(MainActivity.this, "ITEM SUBMITTED", Toast.LENGTH_SHORT).show();
            addItemToInvoiceList(price, desc);
        }
    }

    public void addItemToInvoiceList(String price, String desc){
        Item newItem = new Item("£"+price, desc);
        invoiceList.add(newItem);
        calculateTotal();
        adapter.notifyDataSetChanged();
    }

    public void calculateTotal() {
        Double total = 0d;
        for(Item i : invoiceList){
            total += i.getRealPrice();
        }
        String totalSum = "£"+total.toString();
        totatPrice.setText(totalSum);
    }

    public void removeItemFromInvoiceList(int position) {
        invoiceList.remove(position);
        adapter.notifyDataSetChanged();
        calculateTotal();
    }

}