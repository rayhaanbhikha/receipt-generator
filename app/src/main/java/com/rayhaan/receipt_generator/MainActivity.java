package com.rayhaan.receipt_generator;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private AlertDialog dialog;
    private EditText etPrice;
    private EditText etDesc;
    private ArrayList<Item> invoiceList;
    private CustomListAdapter adapter;
    private String total;
    private TextView totalPrice;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView mListView = (ListView) findViewById(R.id.invoice_items);
        // HEADER
        View headerView = getLayoutInflater().inflate(R.layout.header, mListView, false);
        mListView.addHeaderView(headerView);
        // FOOTER
        View footerView = getLayoutInflater().inflate(R.layout.footer, mListView, false);
        mListView.addFooterView(footerView);
        totalPrice = footerView.findViewById(R.id.total_price);

        invoiceList = new ArrayList<>();
        invoiceList.add(new Item("£5.00", "Gold dress"));
        invoiceList.add(new Item("£45.30", "Blue dress with Lining"));
        invoiceList.add(new Item("£534.07", "Pink dress repair"));
        invoiceList.add(new Item("£70.00", "silver dress with embroidery"));
        invoiceList.add(new Item("£5.00", "Gold dress"));
        invoiceList.add(new Item("£4535.30", "Blue dress with Lining"));
        invoiceList.add(new Item("£53.07", "Pink dress repair"));
        invoiceList.add(new Item("£70.00", "silver dress with embroidery"));


        calculateTotal();

        adapter = new CustomListAdapter(this, R.layout.row_item, invoiceList);
        mListView.setAdapter(adapter);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                confirmDeleteDialog(position);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_item:
                openDialog();
                return true;

            case R.id.complete:
                Toast.makeText(this, "COMPLETE", Toast.LENGTH_LONG).show();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void openDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog, null);
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
        String totalSum = "£"+String.format(Locale.ENGLISH,"%.2f", total);
        totalPrice.setText(totalSum);
    }

    public void removeItemFromInvoiceList(int position) {
        invoiceList.remove(position - 1);
        adapter.notifyDataSetChanged();
        calculateTotal();
    }

    public void confirmDeleteDialog(final int position) {
        AlertDialog.Builder removeDialog = new AlertDialog.Builder(MainActivity.this);
        removeDialog.setMessage("Confirm removal of item");
        removeDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        removeDialog.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeItemFromInvoiceList(position);
            }
        });
        AlertDialog dialog = removeDialog.create();
        dialog.show();
    }
}