package com.mahmoud.albums.util.spinnerdialog

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ListPopupWindow
import com.mahmoud.albums.R

object SpinnerDialog {

    fun attachSpinnerMenu(
        mContext: Context,
        anchoredView: View,
        items: List<String>,
        onItemSelected: (pos: Int) -> Unit
    ): ListPopupWindow {
        val listPopupWindow = ListPopupWindow(mContext, null, R.attr.listPopupWindowStyle)
        listPopupWindow.anchorView = anchoredView


        val adapter = CustomSpinnerAdapter(mContext, R.layout.item_spinner, items)
        listPopupWindow.setAdapter(adapter)

        listPopupWindow.setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            onItemSelected(position)
            listPopupWindow.dismiss()
        }

        return listPopupWindow
    }

}