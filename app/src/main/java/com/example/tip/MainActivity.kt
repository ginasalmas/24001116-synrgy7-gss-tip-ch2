package com.example.tip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.checkbox.MaterialCheckBox
import kotlin.math.ceil


private const val TAG = "MainActivity"
private const val INTIAL_TIP_Percent = 15
class MainActivity : AppCompatActivity() {
    private lateinit var etBaseAmount: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvTipPercentLabel: TextView
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var tvAmountPpl: EditText
    private lateinit var roundUpTipCheckbox: MaterialCheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etBaseAmount = findViewById(R.id.etBaseAmount)
        seekBarTip = findViewById(R.id.seekBarTip)
        tvTipPercentLabel = findViewById(R.id.tvTipPercent)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        tvAmountPpl = findViewById(R.id.tvAmountPpl)
        roundUpTipCheckbox = findViewById(R.id.roundUpTip)

        seekBarTip.progress = INTIAL_TIP_Percent
        tvTipPercentLabel.text = "$INTIAL_TIP_Percent%"
        seekBarTip.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                tvTipPercentLabel.text = "$progress%"
                computeTipAndTotal()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
        etBaseAmount.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterTextChange $s")
                computeTipAndTotal()

            }



        })
        tvAmountPpl.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterTextChange $s")
                computeTipAndTotal()

            }



        })

        roundUpTipCheckbox.setOnCheckedChangeListener { _, isChecked ->
            computeTipAndTotal(isChecked)
        }
    }



    private fun computeTipAndTotal(roundUpTip: Boolean = false) {
        if (etBaseAmount.text.isEmpty() || tvAmountPpl.text.isEmpty()) {
            tvTipAmount.text = ""
            tvTotalAmount.text = ""
            return
        }

        val baseAmount = etBaseAmount.text.toString().toDouble()
        val tipPercent = seekBarTip.progress
        val ppl = tvAmountPpl.text.toString().toInt()

        var tipAmount = baseAmount * tipPercent / 100

        if (roundUpTip) {
            tipAmount = ceil(tipAmount / 10000) * 10000
        }

        var totalAmount = (baseAmount + tipAmount) / ppl

        tvTipAmount.text = "%.0f".format(tipAmount)

        tvTotalAmount.text = "%.0f".format(totalAmount)
    }
}