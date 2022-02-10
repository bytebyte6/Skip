package com.bytebyte6.skip

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle

class AddSportDialog(context: Context) : AlertDialog(context){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_sport)
    }
}