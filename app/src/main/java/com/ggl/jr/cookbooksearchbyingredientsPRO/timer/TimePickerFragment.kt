package com.ggl.jr.cookbooksearchbyingredientsPRO.timer

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

import android.widget.Toast
import com.ggl.jr.cookbooksearchbyingredientsPRO.R
import com.ikovac.timepickerwithseconds.MyTimePickerDialog

class TimePickerFragment : DialogFragment(), MyTimePickerDialog.OnTimeSetListener {
    var timerPickerCallback: TimePickerCallback? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MyTimePickerDialog(
                activity,
                R.style.MyTimePickerDialogStyle,
                this,
                0,
                0,
                0,
                true
        ).apply {
            setButton(MyTimePickerDialog.BUTTON_POSITIVE, "Применить", this)
            setButton(MyTimePickerDialog.BUTTON_NEGATIVE, "Отмена", null as? DialogInterface.OnClickListener?)
        }
    }

    override fun onTimeSet(view: com.ikovac.timepickerwithseconds.TimePicker, hourOfDay: Int, minute: Int, seconds: Int) {
        timerPickerCallback?.onTimeSet(hourOfDay, minute, seconds)
    }

    interface TimePickerCallback {
        fun onTimeSet(hourOfDay: Int, minute: Int, seconds: Int)
    }
}