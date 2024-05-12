package dev.ohjiho.colorpicker

import android.content.Context
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow

class ColorPicker(private val context: Context, private val listener: Listener) {

    private var colAmount: Int = DEFAULT_COL_AMOUNT
    private var colorIntList = emptyList<Int>()
    private val rowAmount: Int
        get() = (colorIntList.size + colAmount - 1) / colAmount

    companion object {
        private const val DEFAULT_COL_AMOUNT = 5
    }

    interface Listener {
        fun onColorClick(colorInt: Int)
    }

    /** Returns the ColorPicker TableLayout view */
    fun createView(): FrameLayout {
        return FrameLayout(context).apply {
            addView(TableLayout(context).apply {
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = Gravity.CENTER_HORIZONTAL
                }

                generateTableRows().forEach {
                    addView(it)
                }
            })
        }
    }

    /** Generates TableRows to populate TableLayout */
    private fun generateTableRows(): List<TableRow> {
        return mutableListOf<TableRow>().apply {
            for (row in 0 until rowAmount) {
                add(TableRow(context).apply {
                    layoutParams = TableLayout.LayoutParams(
                        TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        context.resources.getDimensionPixelSize(R.dimen.colorpicker_ib_vertical_margin)
                            .let {
                                setMargins(
                                    0,
                                    if (row == 0) 0 else it,
                                    0,
                                    if (row == rowAmount - 1) 0 else it
                                )
                            }
                    }

                    generateColorButtons(row).forEach {
                        addView(it)
                    }
                })
            }
        }.toList()
    }

    /** Generates the ImageButtons to populate a [row] of the TableLayout */
    private fun generateColorButtons(row: Int): List<ImageButton> {
        return mutableListOf<ImageButton>().apply {
            for (col in 0 until colAmount) {
                try {
                    add(ImageButton(context).apply {
                        layoutParams = TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT
                        ).apply {
                            context.resources.getDimensionPixelSize(R.dimen.colorpicker_ib_horizontal_margin)
                                .let {
                                    setMargins(
                                        if (col == 0) 0 else it,
                                        0,
                                        if (col == colAmount - 1) 0 else it,
                                        0
                                    )
                                }
                        }
                        scaleType = ImageView.ScaleType.FIT_CENTER
                        setImageResource(R.drawable.shape_circle_32dp)
                        setBackgroundResource(0)
                        colorIntList[(row * colAmount) + col].let { colorInt ->
                            setColorFilter(colorInt)
                            setOnClickListener { listener.onColorClick(colorInt) }
                        }
                    })
                } catch (e: IndexOutOfBoundsException) {
                    break
                }
            }
        }.toList()
    }

    /** Sets the number of columns per row to [amount] */
    fun setColumnAmount(amount: Int): ColorPicker {
        colAmount = amount
        return this
    }

    /** Sets the list of colors to be displayed using [resID], the ID of an array resource containing ColorInts */
    fun setColors(resID: Int): ColorPicker {
        colorIntList = context.resources.getIntArray(resID).toList()
        return this
    }

    /** Sets the list of colors to be displayed using [colorInts], a variable argument of ColorInts */
    fun setColors(vararg colorInts: Int): ColorPicker {
        colorIntList = colorInts.toList()
        return this
    }
}