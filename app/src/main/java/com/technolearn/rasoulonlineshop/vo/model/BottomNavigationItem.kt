package com.technolearn.rasoulonlineshop.vo.model


data class BottomNavigationItem(
    val title: String,
    val route: String? = null,
    val selectedIcon: Int,
    val unSelectedIcon: Int,
    val badgeCount: Int = 0
)
