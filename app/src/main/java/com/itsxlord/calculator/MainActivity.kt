package com.itsxlord.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val etNo1 = findViewById<TextInputEditText>(R.id.etNo1)
        val etNo2 = findViewById<TextInputEditText>(R.id.etNo2)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnSub = findViewById<Button>(R.id.btnSub)
        val btnMul = findViewById<Button>(R.id.btnMul)
        val btnDiv = findViewById<Button>(R.id.btnDiv)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        btnAdd.setOnClickListener {
            val no1 = etNo1.text.toString().toDoubleOrNull() ?: 0.0
            val no2 = etNo2.text.toString().toDoubleOrNull() ?: 0.0
            val result = no1 + no2
            tvResult.text = result.toString()
        }
        btnSub.setOnClickListener {
            val no1 = etNo1.text.toString().toDoubleOrNull() ?: 0.0
            val no2 = etNo2.text.toString().toDoubleOrNull() ?: 0.0
            val result = no1 - no2
            tvResult.text = result.toString()
        }
        btnMul.setOnClickListener {
            val no1 = etNo1.text.toString().toDoubleOrNull() ?: 0.0
            val no2 = etNo2.text.toString().toDoubleOrNull() ?: 0.0
            val result = no1 * no2
            tvResult.text = result.toString()
        }
        btnDiv.setOnClickListener {
            val no1 = etNo1.text.toString().toDoubleOrNull() ?: 0.0
            val no2 = etNo2.text.toString().toDoubleOrNull() ?: 1.0
            val result = no1 / no2
            tvResult.text = result.toString()
        }
    }
}