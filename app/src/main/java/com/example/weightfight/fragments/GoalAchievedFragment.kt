package com.example.weightfight.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.weightfight.MainActivity
import com.example.weightfight.MainActivity.Companion.EMPTY_DATABASE
import com.example.weightfight.MainActivity.Companion.PARAM_SET
import com.example.weightfight.MainActivity.Companion.WEIGHT_PARAM
import com.example.weightfight.MainActivity.Companion.editor
import com.example.weightfight.MainActivity.Companion.router
import com.example.weightfight.R
import com.example.weightfight.cicerone_navigation.Screens
import kotlinx.coroutines.handleCoroutineException

class GoalAchievedFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_goal_achieved, container, false)

        view.findViewById<Button>(R.id.btnExit)
            .apply { setOnClickListener(this@GoalAchievedFragment) }

        deleteAll()

        editor.apply {
            putBoolean(EMPTY_DATABASE, true).apply()
            putInt(WEIGHT_PARAM, 0)
            putBoolean(PARAM_SET, false).apply()
        }

        return view
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.btnExit){
            router.navigateTo(Screens.mainFragment())
        }
    }

    private fun deleteAll() = MainActivity.db.getDataDao().deleteAll()
}