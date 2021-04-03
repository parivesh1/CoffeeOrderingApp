package com.example.coffeorderingapp

import android.R.id.message
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var honey: CheckBox
    private lateinit var chocolateChips: CheckBox
    private lateinit var cocoaPowder: CheckBox
    private lateinit var nutella: CheckBox
    private lateinit var reset: Button
    private lateinit var email: Button
    private lateinit var submit: Button
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var fabMinus: FloatingActionButton
    private lateinit var orderSummary: TextView
    private lateinit var quantity: TextView
    var currentQuantitiy: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.name_edittext)
        honey = findViewById(R.id.checkBox)
        chocolateChips = findViewById(R.id.checkBox2)
        cocoaPowder = findViewById(R.id.checkBox3)
        nutella = findViewById(R.id.checkBox4)
        reset = findViewById(R.id.button5)
        email = findViewById(R.id.button2)
        submit = findViewById(R.id.button1)
        fabAdd = findViewById(R.id.floatingActionButton2)
        fabMinus = findViewById(R.id.floatingActionButton3)
        orderSummary = findViewById(R.id.textView5)
        quantity = findViewById(R.id.textView3)

        submit.setOnClickListener {
            makeOrder()
        }

        fabAdd.setOnClickListener {
            currentQuantitiy++
            quantity.setText(currentQuantitiy.toString())
        }

        fabMinus.setOnClickListener {
            if (currentQuantitiy != 0) {
                currentQuantitiy--
                quantity.setText(currentQuantitiy.toString())
            }
        }

        reset.setOnClickListener {
            editText.text.clear()
            honey.isChecked = false
            chocolateChips.isChecked = false
            cocoaPowder.isChecked = false
            nutella.isChecked = false
            quantity.setText("0")
            currentQuantitiy = 0
            orderSummary.setText("")
        }

        email.setOnClickListener {
            var mailto = "mailto:" + "?cc=" +
                    "&subject=" + Uri.encode("Order Summary from your Coffee Order") +
                    "&body=" + Uri.encode(orderSummary.text.toString());
            var emailIntent: Intent = Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse(mailto));
            try {
                startActivity(emailIntent);
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Enter details and Order First!!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private fun makeOrder() {
        val name = editText.text
        var toppings: String = ""

        if (honey.isChecked)
            toppings += " " + honey.text

        if (chocolateChips.isChecked)
            toppings += " " + chocolateChips.text

        if (cocoaPowder.isChecked)
            toppings += " " + cocoaPowder.text

        if (nutella.isChecked)
            toppings += " " + nutella.text

        val String = "Name: " + name + "\n" +
                "Toppings Selected: " + toppings + "\n" +
                "Number of Coffees Ordered: " + quantity.text.toString() + "\n" +
                "Total: $" + calculateTotal() + "\n" +
                if (currentQuantitiy != 0)
                    "Enjoy your Coffee!!"
                else
                    "Specify The Number of Coffees!"

        orderSummary.setText(String)
    }

    private fun calculateTotal(): String {
        var price: Int = 2
        var q: Int = Integer.parseInt(quantity.text as String)

        if (honey.isChecked)
            price += 1

        if (chocolateChips.isChecked)
            price += 1

        if (cocoaPowder.isChecked)
            price += 1

        if (nutella.isChecked)
            price += 1

        price *= q

        return price.toString()
    }
}