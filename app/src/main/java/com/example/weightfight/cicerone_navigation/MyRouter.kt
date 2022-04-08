package com.example.weightfight.cicerone_navigation

import com.github.terrakok.cicerone.Cicerone

class MyRouter {

    private val cicerone = Cicerone.create()
    val router = cicerone.router
    val navigatorHolder = cicerone.getNavigatorHolder()
}