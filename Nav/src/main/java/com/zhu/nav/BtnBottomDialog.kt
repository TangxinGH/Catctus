package com.zhu.nav

import android.app.Dialog
import android.os.Bundle
import android.widget.SeekBar
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_dialog.*

/**
 * https://medium.com/over-engineering/hands-on-with-material-components-for-android-bottom-sheet-970c5f0f1840
 * */
class BtnBottomDialog : BottomSheetDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (activity == null) return super.onCreateDialog(savedInstanceState)
        val dialog =
            BottomSheetDialog(requireActivity(), R.style.SheetDialog)//设置样式这里
        dialog.setContentView(R.layout.bottom_dialog)


        dialog.sleep_SeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                dialog.latency.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        dialog.numberpicker.maxValue = 900
        dialog.numberpicker.minValue = 50
        dialog.numberpicker.value = 200 //人的反应为0.2秒
        dialog.numberpicker.setOnValueChangedListener { picker, oldVal, newVal ->
            dialog.latency.text = newVal.toString()
        }
        return dialog
    }


}