package com.example.valuebetsapp

interface BetsRepository {
        fun getBets() : MutableList<Bet>
}
