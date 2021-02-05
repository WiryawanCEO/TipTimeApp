package com.example.tiptime

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Button listener followed by calculateTip function
        binding.calculateButton.setOnClickListener {
            calculateTip()
        }

        // Edit Text listener for auto-hiding keyboard after use
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(view, keyCode)
        }
    }

    // Function for calculate tip cost
    private fun calculateTip() {
        //Get the input from EditText View
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        //Convert String cost value from EditText to Double that has null capability
        val cost = stringInTextField.toDoubleOrNull()
        // For handling the null value
        if (cost == null) {
            // Display a warning toast
            Toast.makeText(this, "Input cost amount first!", Toast.LENGTH_LONG).show()
            // Resetting result TextView to blank when it catches null
            binding.calculationResult.text = ""
            // Return the app to default condition
            return
        }

        // Handling option from RadioButton corresponding to its percentage value
        val tipPercentage = when (binding.tipOption.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        // Calculate the Tip Cost
        var tip = cost * tipPercentage
        //Handling the Switch for rounding the value of tip
        val roundUp = binding.roundUpSwitch.isChecked
        if (roundUp) {
            tip = kotlin.math.ceil(tip)
        }
        // Formatting the value to correspond for the device currency system ($ or Rp., ect.)
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        // Displaying the value into TextView
        binding.calculationResult.text = getString(R.string.tip_amount, formattedTip)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}