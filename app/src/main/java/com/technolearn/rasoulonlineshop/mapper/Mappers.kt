package com.technolearn.rasoulonlineshop.mapper

import com.technolearn.rasoulonlineshop.vo.entity.FavoriteEntity
import com.technolearn.rasoulonlineshop.vo.res.ProductRes

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