package com.zhu.nav

import android.app.Dialog
import android.os.Bundle
import android.widget.SeekBar
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
//import kotlinx.android.synthetic.main.bottom_dialog.* //kotlin的废弃
import com.zhu.nav.databinding.BottomDialogBinding
/**
 * https://medium.com/over-engineering/hands-on-with-material-components-for-android-bottom-sheet-970c5f0f1840
 * */
class BtnBottomDialog : BottomSheetDialogFragment() {
private lateinit var bottomSheetDialogViewBinding: BottomDialogBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (activity == null) return super.onCreateDialog(savedInstanceState)

        bottomSheetDialogViewBinding = BottomDialogBinding.inflate(layoutInflater)//Migrate from Kotlin synthetics to Jetpack view binding
        val view = bottomSheetDialogViewBinding.root //Migrate from Kotlin synthetics to Jetpack view binding

        val dialog =
            BottomSheetDialog(requireActivity(), R.style.SheetDialog)//设置样式这里
        dialog.setContentView(view)            //Migrate from Kotlin synthetics to Jetpack view binding



        bottomSheetDialogViewBinding.sleepSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                bottomSheetDialogViewBinding.latency.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        bottomSheetDialogViewBinding.numberpicker.maxValue = 900
        bottomSheetDialogViewBinding.numberpicker.minValue = 50
        bottomSheetDialogViewBinding.numberpicker.value = 200 //人的反应为0.2秒
        bottomSheetDialogViewBinding.numberpicker.setOnValueChangedListener { picker, oldVal, newVal ->
            bottomSheetDialogViewBinding.latency.text = newVal.toString()
        }
        return dialog
    }


}