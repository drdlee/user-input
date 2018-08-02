/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;
    Toast toastMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toastMessage = Toast.makeText(this, "welcome", Toast.LENGTH_SHORT);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamState = findViewById(R.id.toppings_whippedCream);
        boolean withWhippedCream = whippedCreamState.isChecked();
        CheckBox chocolateState = findViewById(R.id.toppings_chocolate);
        boolean withChocolate = chocolateState.isChecked();
        EditText userNameText = findViewById(R.id.name_field);
        String userName = userNameText.getText().toString();

        int price = calculatePrice(withWhippedCream, withChocolate);
        String OrderSummary = createOrderSummary(userName, price, withWhippedCream, withChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order summary");
        intent.putExtra(Intent.EXTRA_TEXT, OrderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     *  this method is called when the "+" button is clicked
     */
    public void increment(View view) {
        if (quantity == 100) {
            toastMessage.setText("order max 100 cups");
            toastMessage.show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * this method is called when the"-" button is pressed
     */
    public void decrement(View view) {
        if (quantity == 1) {
            toastMessage.setText("min order 1 cup");
            toastMessage.show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Calculates the price of the order.
     * @param withWhippedCream is whether the user wants to add whipped cream toppings or not
     * @param withChocolate is whether the user wants to add chocolate toppings or not
     * return total price
     */
    private int calculatePrice(boolean withWhippedCream, boolean withChocolate) {
        int pricePerCup = 5;

        if (withWhippedCream && withChocolate) pricePerCup += 1 + 2;
        if (withChocolate) pricePerCup += 2;
        if (withWhippedCream ) pricePerCup += 1;
        return quantity * pricePerCup;
    }

    /**
     * Create Order Summary from calculatePrice() method
     * @param price of the total order
     * @param withWhippedCream is whether the user wants to add whipped cream toppings or not
     * @param withChocolate is whether the user wants to add chocolate toppings or not
     * @return text summary
     */
    private String createOrderSummary(String userName, int price, boolean withWhippedCream, boolean withChocolate) {
        String orderSummary = getString(R.string.order_summary_name, userName);
        if (withWhippedCream) orderSummary += "\n" + getString(R.string.add) + " " + getString(R.string.whipped_cream);
        if (withChocolate) orderSummary += "\n" + getString(R.string.add) + " " + getString(R.string.chocolate);
        orderSummary += "\n" + getString(R.string.quantity_header) + ": " + quantity;
        orderSummary += "\n" + getString(R.string.total_price, price);
        orderSummary += "\n" + getString(R.string.thank_you);
        return orderSummary;
    }

}