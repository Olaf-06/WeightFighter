package com.example.weightfight.fragments

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.weightfight.MainActivity.Companion.EMPTY_DATABASE
import com.example.weightfight.MainActivity.Companion.ID_DB
import com.example.weightfight.MainActivity.Companion.PARAM_SET
import com.example.weightfight.MainActivity.Companion.SHARED_PREFERENCES
import com.example.weightfight.MainActivity.Companion.WEIGHT_PARAM
import com.example.weightfight.MainActivity.Companion.db
import com.example.weightfight.MainActivity.Companion.editor
import com.example.weightfight.MainActivity.Companion.router
import com.example.weightfight.R
import com.example.weightfight.cicerone_navigation.Screens
import com.example.weightfight.room_database.Data
import kotlin.properties.Delegates

class MainFragment : Fragment(), View.OnClickListener {

    private lateinit var sharedPreferences: SharedPreferences
    private var paramSet by Delegates.notNull<Boolean>()

    private lateinit var btnSetParam: Button
    private lateinit var tvGoalWeight: TextView
    private lateinit var tvCurrentWeight: TextView

    private lateinit var allData: MutableList<Data>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> "Портретная ориентация"
            Configuration.ORIENTATION_LANDSCAPE -> "Альбомная ориентация"
            else -> ""
        }

        val view = inflater.inflate(R.layout.fragment_main, container, false)

        allData = getData()

        context?.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)
            ?.let { sharedPreferences = it }

        paramSet = sharedPreferences.getBoolean(PARAM_SET, false)

        btnSetParam = view.findViewById<Button>(R.id.btnSetParam)
            .apply { setOnClickListener(this@MainFragment) }

        val databaseEmpty = sharedPreferences.getBoolean(EMPTY_DATABASE, true)

        tvCurrentWeight = view.findViewById<TextView>(R.id.tvCurrentWeight)

        if(allData.isEmpty()) {
            tvCurrentWeight.text = "0"
        } else {
            tvCurrentWeight.text = "${db.getDataDao().getAllData().last().weight.toInt()}"
        }

        tvGoalWeight = view.findViewById<TextView>(R.id.tvGoalWeight)
            .apply { text = "${sharedPreferences.getInt(WEIGHT_PARAM, 0)}" }

        if (paramSet) {
            btnSetParam.text = "Сбросить статистику"
        }

        allData = getData()

        return view
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnSetParam -> {
                paramSet = sharedPreferences.getBoolean(PARAM_SET, false)
                if (paramSet) {
                    deleteAll()
                    editor.apply() {
                        putInt(ID_DB, 1).apply()
                        putBoolean(PARAM_SET, false).apply()
                        putBoolean(EMPTY_DATABASE, true).apply()
                        putInt(WEIGHT_PARAM, 0).apply()
                    }
                    tvCurrentWeight.text = "0"
                    tvGoalWeight.text = "0"
                    btnSetParam.text = "Задать параметры"
                } else {
                    router.navigateTo(Screens.setParamFragment())
                }
            }
        }
    }

    private fun getData() : MutableList<Data> = db.getDataDao().getAllData()

    private fun deleteAll() = db.getDataDao().deleteAll()
}