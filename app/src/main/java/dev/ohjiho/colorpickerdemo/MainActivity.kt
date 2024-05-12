package dev.ohjiho.colorpickerdemo

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.jh8oh.colorpickerdemo.R

class MainActivity : AppCompatActivity(), ColorPickerDialog.Listener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.open_dialog).apply {
            setOnClickListener {
                ColorPickerDialog().show(supportFragmentManager, ColorPickerDialog.TAG)
            }
        }
    }

    override fun onColorClick(colorInt: Int) {
        findViewById<Button>(R.id.open_dialog).setTextColor(colorInt)
    }
}
