package com.technolearn.rasoulonlineshop.mapper

import androidx.room.PrimaryKey
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.vo.entity.FavoriteEntity
import com.technolearn.rasoulonlineshop.vo.entity.UserAddressEntity
import com.technolearn.rasoulonlineshop.vo.entity.UserCartEntity
import com.technolearn.rasoulonlineshop.vo.entity.UserLoginEntity
import com.technolearn.rasoulonlineshop.vo.req.UpdateReq
import com.technolearn.rasoulonlineshop.vo.res.CategoryRes
import com.technolearn.rasoulonlineshop.vo.res.ColorRes
import com.technolearn.rasoulonlineshop.vo.res.ProductRes
import com.technolearn.rasoulonlineshop.vo.res.SignUpRes
import com.technolearn.rasoulonlineshop.vo.res.SizeRes
import com.technolearn.rasoulonlineshop.vo.res.SliderRes
import com.technolearn.rasoulonlineshop.vo.res.UpdateRes

fun toFavoriteEntity(productRes: ProductRes): FavoriteEntity {
    return FavoriteEntity(
        id = productRes.id,
        brand = productRes.brand,
        title = productRes.title,
        image = productRes.image,
        addDate = productRes.addDate,
        price = productRes.price,
        rate = productRes.rate,
        hasDiscount = productRes.hasDiscount,
        isAddToFavorites = productRes.isAddToFavorites
    )
}

fun toProductRes(favoriteEntity: FavoriteEntity): ProductRes {
    return ProductRes(
        id = favoriteEntity.id,
        brand = favoriteEntity.brand,
        title = favoriteEntity.title,
        image = favoriteEntity.image,
        addDate = favoriteEntity.addDate,
        price = favoriteEntity.price,
        rate = favoriteEntity.rate,
        hasDiscount = favoriteEntity.hasDiscount,
        isAddToFavorites = favoriteEntity.isAddToFavorites
    )
}

fun toSliderRes(productRes: ProductRes): SliderRes {
    return SliderRes(
        id = productRes.id,
        image = productRes.image[0],
        link = null,
        subTitle = productRes.brand,
        title = productRes.title,
        category=productRes.category,
    )
}

fun toUpdateUserReq(userAddressEntity: UserAddressEntity): UpdateReq {
    return UpdateReq(
        userAddressEntity.userId,
        userAddressEntity.firstName,
        userAddressEntity.lastName,
        userAddressEntity.phone,
        userAddressEntity.addressName,
        userAddressEntity.address,
        userAddressEntity.city,
        userAddressEntity.province,
        userAddressEntity.postalCode,
        userAddressEntity.country,
    )
}

fun toSignUpRes(userLoginEntity: UserLoginEntity): SignUpRes {
    return SignUpRes(
        id = userLoginEntity.id,
        userName = userLoginEntity.username.orDefault(),
        password = userLoginEntity.password.orDefault(),
        email = userLoginEntity.email.orDefault(),
        customer = SignUpRes.Customer(
            id = userLoginEntity.customerId.orDefault(),
            firstName = userLoginEntity.firstName.orDefault(),
            lastName = userLoginEntity.lastName.orDefault(),
            phone = userLoginEntity.phone.orDefault(),
            addressName = userLoginEntity.addressName.orDefault(),
            address = userLoginEntity.address.orDefault(),
            city = userLoginEntity.city.orDefault(),
            province = userLoginEntity.province.orDefault(),
            postalCode = userLoginEntity.postalCode.orDefault(),
            country = userLoginEntity.country.orDefault()
        )
    )
}
fun toProductResByUserCartEntity(userCartEntity: UserCartEntity): ProductRes {
    return ProductRes(
        id = userCartEntity.id.orDefault(),
        title = userCartEntity.name,
        image = arrayListOf(userCartEntity.image.orDefault()),
        colors = arrayListOf(ColorRes(title = userCartEntity.color.orDefault())),
        sizes = arrayListOf(SizeRes(title = userCartEntity.size.orDefault())),
        price = userCartEntity.price,
    )
}
//fun toUserCartEntity(productRes: ProductRes): UserCartEntity {
//    return UserCartEntity(
//        id = productRes.id,
//        name = productRes.title.orDefault(),
//        image = productRes.image[0],
//        color = productRes.colors[0].title,
//        size =,
//        price =,
//        quantity =
//    )
//}