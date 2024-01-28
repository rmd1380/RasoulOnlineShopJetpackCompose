package com.technolearn.rasoulonlineshop.navigation

import com.technolearn.rasoulonlineshop.util.Constants

sealed class Screen(val route: String) {
    data object SignUpScreen : Screen("signup_screen")
    data object LoginScreen : Screen("login_screen")
    data object ProductScreen : Screen("product_screen")
    data object ProductDetailScreen :
        Screen("product_detail_screen/{${Constants.Argument.PRODUCT_ID}}/{${Constants.Argument.CATEGORY_ID}}") {
        fun passProductIdAndCategoryId(productId: Int,categoryId:Int): String {
            return this.route.replace(
                oldValue = "{${Constants.Argument.PRODUCT_ID}}/{${Constants.Argument.CATEGORY_ID}}",
                newValue = "$productId/$categoryId"
            )
        }
    }

    data object MoreProductScreen :
        Screen("more_product_screen/{${Constants.Argument.WHAT_IS_TITLE}}") {
        fun passWhatIsTitle(whatIsTitle: String): String {
            return this.route.replace(
                oldValue = "{${Constants.Argument.WHAT_IS_TITLE}}",
                newValue = whatIsTitle
            )
        }
    }

    data object ProductByCategoryScreen :
        Screen("product_by_category_id_screen/{${Constants.Argument.CATEGORY_ID}}") {
        fun passCategoryId(categoryId: Int): String {
            return this.route.replace(
                oldValue = "{${Constants.Argument.CATEGORY_ID}}",
                newValue = categoryId.toString()
            )
        }
    }

    data object ProductARScreen : Screen("product_ar_screen/{${Constants.Argument.MODEL_NAME}}") {
        fun passModelName(modelName: String): String {
            return this.route.replace(
                oldValue = "{${Constants.Argument.MODEL_NAME}}",
                newValue = modelName
            )
        }
    }

    data object ShippingAddressScreen : Screen("shipping_address_screen")

    data object AddShippingAddressScreen :
        Screen("add_shipping_address_screen/{${Constants.Argument.IS_EDIT}}/{${Constants.Argument.ADDRESS_ID}}") {
        fun passIsEditAndId(passIsEdit: Boolean, addressId:Int): String {
            return this.route.replace(
                oldValue = "{${Constants.Argument.IS_EDIT}}/{${Constants.Argument.ADDRESS_ID}}",
                newValue = "$passIsEdit/$addressId"
            )
        }
    }

    data object OrderScreen : Screen("order_screen")
    data object PaymentMethodsScreen : Screen("payment_methods_screen")
    data object CheckOutScreen : Screen("check_out_screen")
    data object SettingsScreen : Screen("settings_screen")
    data object ProductWith3DViewScreen : Screen("product_with_3d_view_screen")
    data object SuccessfulPaymentScreen : Screen("successful_payment_screen")
    data object PaymentGatewayScreen : Screen("payment_gateway_screen")
    data object VisualSearchScreen : Screen("visual_search_screen")
    data object SearchProductScreen :
        Screen("search_product_screen/{${Constants.Argument.WHAT_IS_TITLE_FOR_SEARCH}}") {
        fun passWhatIsTitleForSearch(whatIsTitleForSearch: String): String {
            return this.route.replace(
                oldValue = "{${Constants.Argument.WHAT_IS_TITLE_FOR_SEARCH}}",
                newValue = whatIsTitleForSearch
            )
        }
    }
}
