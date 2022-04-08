package com.example.weightfight.fragments

import android.content.Context.MODE_APPEND
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.weightfight.MainActivity
import com.example.weightfight.MainActivity.Companion.EMPTY_DATABASE
import com.example.weightfight.MainActivity.Companion.LOSE_GAIN_WEIGHT
import com.example.weightfight.MainActivity.Companion.SHARED_PREFERENCES
import com.example.weightfight.MainActivity.Companion.WEIGHT_PARAM
import com.example.weightfight.MainActivity.Companion.db
import com.example.weightfight.MainActivity.Companion.editor
import com.example.weightfight.MainActivity.Companion.router
import com.example.weightfight.R
import com.example.weightfight.cicerone_navigation.Screens
import com.example.weightfight.room_database.Data
import java.util.*

class AddProgressFragment : Fragment(), View.OnClickListener {

    lateinit var etWeight: EditText
    lateinit var etDistance: EditText

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_progress, container, false)

        context?.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)
            ?.let { sharedPreferences = it }

        etWeight = view.findViewById<EditText>(R.id.etWeight)
        etDistance = view.findViewById<EditText>(R.id.etDistance)

        view.findViewById<Button>(R.id.btnConfirm)
            .apply { setOnClickListener(this@AddProgressFragment) }

        return view
    }

    private fun setData(weight: String, distance: String) {
        val p = sharedPreferences?.getInt(MainActivity.ID_DB, 1) ?: 1

        editor.putInt(MainActivity.ID_DB, p + 1).apply()

        val data: Data
        val time = Calendar.getInstance()

        data = Data(
            p,
            weight.toFloat(),
            distance.toFloat(),
            time.get(Calendar.YEAR),
            time.get(Calendar.MONTH) + 1,
            time.get(Calendar.DAY_OF_MONTH)
        )

        db.getDataDao().insert(data)
    }

    override fun onClick(p0: View?) {
        if(p0?.id == R.id.btnConfirm){
            val weight = etWeight.text.toString()
            val distance = etDistance.text.toString()
            if (weight.matches(Regex("[0-9]*")) && weight.isNotEmpty()
                && distance.matches(Regex("[0-9]*")) && distance.isNotEmpty()) {
                setData(weight, distance)
                editor.putBoolean(EMPTY_DATABASE, false)

                val loseGainWeight = sharedPreferences?.getBoolean(LOSE_GAIN_WEIGHT, true) ?: true
                val goalWeight = sharedPreferences?.getInt(WEIGHT_PARAM, 0) ?: 0
                val currentWeight = getData().last().weight.toInt()

                if((loseGainWeight && currentWeight <= goalWeight)
                    || (!loseGainWeight && currentWeight >= goalWeight)){
                    router.navigateTo(Screens.goalAchievedFragment())
                } else {
                    router.navigateTo(Screens.progressFragment())
                }

            } else {
                Toast.makeText(context, "В полях могут быть только целые числа!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getData() : MutableList<Data> = db.getDataDao().getAllData()
}