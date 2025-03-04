package com.example.exploreit.data.mapper

import com.example.exploreit.data.api.TourDto

fun TourDto.toPresenter():Tour{
    return Tour(
        id = this.id,
        title = this.title,
        location = this.location,
        price = this.price,
        duration = this.duration,
        rating = this.rating,
        image = this.image,
        description = this.description
    )
}
