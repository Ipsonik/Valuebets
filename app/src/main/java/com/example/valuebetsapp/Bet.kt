package com.example.valuebetsapp

data class Bet(
    val id: Int,
    val bookie: String,
    val sport: String,
    val time: String,
    val event: String,
    val market: String,
    val odd: String,
    val value: String
)
