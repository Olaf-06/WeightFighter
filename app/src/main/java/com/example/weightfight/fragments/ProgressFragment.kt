package com.example.weightfight.fragments

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import com.example.weightfight.MainActivity
import com.example.weightfight.MainActivity.Companion.PARAM_SET
import com.example.weightfight.MainActivity.Companion.SHARED_PREFERENCES
import com.example.weightfight.MainActivity.Companion.router
import com.example.weightfight.R
import com.example.weightfight.cicerone_navigation.Screens
import com.example.weightfight.room_database.Data
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProgressFragment : Fragment(), View.OnClickListener {

    lateinit var table: TableLayout

    lateinit var allData: MutableList<Data>

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_progress, container, false)

        table = view.findViewById<TableLayout>(R.id.table)

        allData = getData()

        for (data in allData) {
            val tableRow: TableRow = TableRow(context)
            tableRow.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT)

            var textView = TextView(context)
            textView.text = "${data.year}-${data.month}-${data.day}"
            tableRow.addView(textView)

            textView = TextView(context)
            textView.text = "${data.weight.toInt()}"
            tableRow.addView(textView)

            textView = TextView(context)
            textView.text = "${data.distance.toInt()}"
            tableRow.addView(textView)
            table.addView(tableRow)
        }

        view.findViewById<FloatingActionButton>(R.id.fabAdd)
            .apply { setOnClickListener(this@ProgressFragment) }

        Log.d("myLog", "onCreateView: ")
        return view
    }

    fun getData() : MutableList<Data> = MainActivity.db.getDataDao().getAllData()

    override fun onClick(p0: View?) {
        if(p0?.id == R.id.fabAdd) {
            val paramSet =
                context?.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)
                    ?.getBoolean(PARAM_SET, false) ?: false

            if(paramSet){
                router.navigateTo(Screens.addProgressFragment())
            } else {
                Toast.makeText(context, "Задайте значение вашей цели!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}