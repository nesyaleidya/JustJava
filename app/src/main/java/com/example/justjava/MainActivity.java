package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.string.no;
import static android.os.Build.VERSION_CODES.N;


public class MainActivity extends AppCompatActivity {


    public int quantity;

    {
        quantity = 0;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void increment(View view){//perintah tombol tambah
        if(quantity==100){
            Toast.makeText(this,"pesanan maximal 100",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity+1 ;
        display(quantity);
    }
    public void decrement(View view){//perintah tombol tambah
        if (quantity==1){
            Toast.makeText(this,"pesanan minimal 1",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity -1;
        display(quantity);
    }


    public void Submitorder(View view) {
        EditText nameEditText=(EditText)findViewById(R.id.edt_name);
        String name=nameEditText.getText().toString();
        Log.v("MainActivity","Nama:"+name);

        CheckBox whippedcreamChekBox= (CheckBox) findViewById(R.id.WhippedCream_checkbox);
        boolean haswhippedcream=whippedcreamChekBox.isChecked();//mengidentifikasi check
        Log.v("MainActivity","has whippedcream:"+haswhippedcream);

        CheckBox chocolateChekBox= (CheckBox) findViewById(R.id.Chocolate_checkbox);
        boolean haschocolate=chocolateChekBox.isChecked();//mengidentifikasi check
        Log.v("MainActivity","has whippedcream:"+haschocolate);

        int price=calculateprice(haswhippedcream,haschocolate);//memanggil method jumlah harga
        String pricemessage=createOrderSummary(price,name,haswhippedcream,haschocolate);



    // kode kalo pilihan, open dengan apa
   //     Intent email = new Intent(Intent.ACTION_SEND);
    //    email.putExtra(Intent.EXTRA_EMAIL, new String[]{});
      //  email.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order For " + name);
        //email.putExtra(Intent.EXTRA_TEXT, pricemessage);
       // email.setType("message/rfc822");
       // startActivity(Intent.createChooser(email, "Send Mail Using :"));

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order For " + name);
        emailIntent.putExtra(Intent.EXTRA_TEXT, pricemessage);
        emailIntent.setType("text/plain");
        startActivity(emailIntent);
        displayMessage(pricemessage);

    }
    private int calculateprice(boolean addwhipedcream,boolean addchocolate){//jumlah pesanan * harga
        int harga=5;

        if(addwhipedcream){
            harga=harga+1;//harga tambahan toping
        }

        if (addchocolate){
            harga=harga+2;
        }

        return quantity * harga;
    }
    private String createOrderSummary(int price, String name, boolean addChocolate, boolean addWhippedCream) {//hasil pemesanan
        String pricemessage= getString(R.string.order_summary_name, name ) + name;
        pricemessage+="\n" + getString(R.string.order_summary_whippedcream, addWhippedCream) + addWhippedCream;
        pricemessage+="\n" + getString(R.string.order_summary_chocolate, addChocolate) + addChocolate;
        pricemessage+="\n" + getString(R.string.order_summary_quantity, quantity) + quantity;
        pricemessage+="\n" + getString(R.string.order_summary_price, price) + "$" + price;
        pricemessage+="\n" + getString(R.string.thank_you) ;
        return  pricemessage;
    }

    //method ini untuk mencetak hasil perintah yang di tampilkan dengan inisial quantity_textview di textview 0
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_textview);
        priceTextView.setText(message);
    }
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_textview);
        quantityTextView.setText("" + number);
    }
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_textview);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

}