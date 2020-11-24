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
class BtnBottomDialog: BottomSheetDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (activity == null) return super.onCreateDialog(savedInstanceState)
        val dialog =
            BottomSheetDialog(activity!!, R.style.Theme_MaterialComponents_BottomSheetDialog)
        dialog.setContentView(R.layout.bottom_dialog)

        return dialog
    }
}