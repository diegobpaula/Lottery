package com.diegobpaula.lottery

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // search the object is reference to
        val number: EditText = findViewById(R.id.edt_number)
        val result: TextView = findViewById(R.id.txt_result)
        val generate: Button = findViewById(R.id.btn_send)

        // database is preferences
        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val results = prefs.getString("result", "no records saved")

        results?.let {
            result.text = "last bet: $results"
        }

        generate.setOnClickListener {
            val text = number.text.toString()
            numberGenerator(text, result)
        }
        /* option 2: variable type View.OnClickListener (interface)
        generate.setOnClickListener(buttonClickListener)*/
    }

    private fun numberGenerator(text: String, txtResult: TextView) {

        if (text.isEmpty()) {
            Toast.makeText(this, "Empty field", Toast.LENGTH_LONG).show()
            return
        }

        val qtd = text.toInt()

        if (qtd < 6 || qtd > 15) {
            Toast.makeText(this, "Empty field", Toast.LENGTH_LONG).show()
            return
        }

        val numbers = mutableSetOf<Int>()
        val random = Random()

        while (true) {
            val number = random.nextInt(60)
            numbers.add(number + 1)

            if (numbers.size.equals(qtd)) {
                break
            }
        }

        txtResult.text = numbers.joinToString(" - ")

        val editor = prefs.edit()

        prefs.edit().apply {
            putString("result", txtResult.text.toString())
            apply()
        }

        // commit -> save form sincrona - block is interface
        // apply  -> save form asincrone - is not block is interface
    }

    /*
    val buttonClickListener = View.OnClickListener {
        Log.i("Test", "button clicked")
    }*/

    /* option 1: XML
    fun buttonClicked(view: View){
        Log.i("Test", "button clicked")
    }*/
}