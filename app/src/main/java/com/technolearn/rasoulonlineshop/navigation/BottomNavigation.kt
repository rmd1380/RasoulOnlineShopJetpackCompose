package com.technolearn.rasoulonlineshop.navigation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BadgedBox
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.technolearn.rasoulonlineshop.ui.theme.Gray
import com.technolearn.rasoulonlineshop.ui.theme.Primary
import com.technolearn.rasoulonlineshop.ui.theme.White
import com.technolearn.rasoulonlineshop.vo.model.BottomNavigationItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    list: List<BottomNavigationItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
) {

    var selectedItemIndex by remember { mutableIntStateOf(0) }
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route

    // Update the selected item index when the current route matches the item route
    if (currentRoute != null) {
        val index = list.indexOfFirst { it.route == currentRoute }
        if (index != -1 && selectedItemIndex != index) {
            selectedItemIndex = index
        }
    }

    NavigationBar(
        containerColor = White,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp))
    ) {
        list.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.route ?: "") {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = {
                    Text(text = item.title)
                },
                alwaysShowLabel = false,
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.badgeCount > 0) {
                                Badge {
                                    Text(text = item.badgeCount.toString())
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (index == selectedItemIndex) {
                                ImageVector.vectorResource(id = item.selectedIcon)
                            } else ImageVector.vectorResource(id = item.unSelectedIcon),
                            contentDescription = item.title,
                            tint = if (index == selectedItemIndex) {
                                Primary
                            } else Gray,
                        )
                    }
                },
                colors = NavigationBarItemDefaults
                    .colors(
                        selectedIconColor = Primary,
                        indicatorColor = White
                    ),
            )
        }
    }
}







