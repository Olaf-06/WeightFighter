package com.example.weightfight.cicerone_navigation

import com.example.weightfight.fragments.*
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun mainFragment() = FragmentScreen { MainFragment() }
    fun progressFragment() = FragmentScreen { ProgressFragment() }
    fun setParamFragment() = FragmentScreen { SetParamFragment() }
    fun graphFragment() = FragmentScreen { GraphFragment() }
    fun addProgressFragment() = FragmentScreen { AddProgressFragment() }
    fun goalAchievedFragment() = FragmentScreen { GoalAchievedFragment() }
}