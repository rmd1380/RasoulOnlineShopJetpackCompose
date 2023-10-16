package com.technolearn.rasoulonlineshop.navigation

import androidx.annotation.DrawableRes
import com.technolearn.rasoulonlineshop.R

sealed class NavigationBarItemsGraph(
    val route: String,
    val title: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unSelectedIcon: Int,
) {
    object Home : NavigationBarItemsGraph(
        "home",
        "Home",
        R.drawable.ic_home_activated,
        R.drawable.ic_home_inactive
    )

    object Shop : NavigationBarItemsGraph(
        "shop",
        "Shop",
        R.drawable.ic_shop_activated,
        R.drawable.ic_shop_inactive
    )

    object Bag : NavigationBarItemsGraph(
        "bag",
        "Bag",
        R.drawable.ic_shop_bag_activated,
        R.drawable.ic_shop_bag_inactive
    )

    object Favorites : NavigationBarItemsGraph(
        "favorites",
        "Favorites",
        R.drawable.ic_heart_activated,
        R.drawable.ic_heart_inactive
    )

    object Profile : NavigationBarItemsGraph(
        "profile",
        "Profile",
        R.drawable.ic_profile_activated,
        R.drawable.ic_profile_inactive
    )


}