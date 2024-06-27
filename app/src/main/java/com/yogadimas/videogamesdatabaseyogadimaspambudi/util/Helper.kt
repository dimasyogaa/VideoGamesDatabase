package com.yogadimas.videogamesdatabaseyogadimaspambudi.util

import com.yogadimas.videogamesdatabaseyogadimaspambudi.data.local.database.entities.GameFavoriteEntity
import com.yogadimas.videogamesdatabaseyogadimaspambudi.data.model.ResultsItem

fun GameFavoriteEntity.toResultsItem(): ResultsItem {
    return ResultsItem(
        added = null,
        rating = this.rating,
        metacritic = null,
        playtime = null,
        shortScreenshots = null,
        platforms = null,
        userGame = null,
        ratingTop = null,
        reviewsTextCount = null,
        ratings = null,
        genres = null,
        saturatedColor = null,
        id = this.id,
        addedByStatus = null,
        parentPlatforms = null,
        ratingsCount = null,
        slug = null,
        released = this.released,
        suggestionsCount = null,
        stores = null,
        tags = null,
        backgroundImage = this.backgroundImage,
        tba = null,
        dominantColor = null,
        esrbRating = null,
        name = this.name,
        updated = null,
        clip = null,
        reviewsCount = null
    )
}

fun List<GameFavoriteEntity>.toResultsItemList(): List<ResultsItem> {
    return this.map { it.toResultsItem() }
}