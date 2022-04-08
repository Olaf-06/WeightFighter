package com.example.weightfight.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import com.example.weightfight.MainActivity.Companion.LOSE_GAIN_WEIGHT
import com.example.weightfight.MainActivity.Companion.PARAM_SET
import com.example.weightfight.MainActivity.Companion.WEIGHT_PARAM
import com.example.weightfight.MainActivity.Companion.editor
import com.example.weightfight.MainActivity.Companion.router
import com.example.weightfight.R
import com.example.weightfight.cicerone_navigation.Screens

class SetParamFragment : Fragment(), View.OnClickListener {

    lateinit var etWeightParam: EditText
    lateinit var radioGroup: RadioGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_set_param, container, false)

        radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
        etWeightParam = view.findViewById<EditText>(R.id.etWeightParam)

        view.findViewById<Button>(R.id.btnParamConfirm)
            .apply { setOnClickListener(this@SetParamFragment) }

        return view
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.btnParamConfirm) {
            val weight = etWeightParam.text.toString()
            if (weight.matches(Regex("[0-9]*")) && weight.isNotEmpty()) {

                val loseGainWeightChecked = when(radioGroup.checkedRadioButtonId){
                    R.id.rbGainWeight -> false
                    R.id.rbLoseWeight -> true
                    else -> true
                }

                editor.apply {
                    putBoolean(PARAM_SET, true).apply()
                    putBoolean(LOSE_GAIN_WEIGHT, loseGainWeightChecked)
                    putInt(WEIGHT_PARAM, weight.toInt()).apply()
                }
                router.navigateTo(Screens.mainFragment())
            }
            else {
                Toast.makeText(context, "В поле может быть только целое число!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}