package com.technolearn.rasoulonlineshop.mapper

import androidx.room.PrimaryKey
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.vo.entity.FavoriteEntity
import com.technolearn.rasoulonlineshop.vo.entity.UserCartEntity
import com.technolearn.rasoulonlineshop.vo.res.ProductRes
import com.technolearn.rasoulonlineshop.vo.res.SliderRes

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
        title = productRes.title
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