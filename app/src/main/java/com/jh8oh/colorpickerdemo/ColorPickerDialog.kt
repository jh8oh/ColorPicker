package com.jh8oh.colorpickerdemo

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.jh8oh.colorpicker.ColorPicker

class ColorPickerDialog : DialogFragment(), ColorPicker.Listener {

    private lateinit var listener: Listener

    companion object {
        const val TAG = "ColorPickerDialog"
    }

    interface Listener {
        fun onColorClick(colorInt: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as Listener
        } catch (e: ClassCastException) {
            Log.e("ColorPickerDialog", "Context doesn't implement ColorPickerListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            AlertDialog.Builder(it).apply {
                setTitle(R.string.dialog_title)
                setView(ColorPicker(context, this@ColorPickerDialog).apply {
                    setColors(R.array.colors)
                }.createView())
                setNegativeButton(R.string.dialog_cancel) { dialog, _ ->
                    dialog.cancel()
                }
            }.create()
        } ?: throw IllegalStateException("ColorPickerDialog - Activity cannot be null")
    }

    override fun onColorClick(colorInt: Int) {
        listener.onColorClick(colorInt)
        dialog?.dismiss()
    }
}