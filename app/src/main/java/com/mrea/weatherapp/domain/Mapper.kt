package com.mrea.weatherapp.domain

interface Mapper<in Type, out Entity> {
    fun map(input: Type): Entity
}
