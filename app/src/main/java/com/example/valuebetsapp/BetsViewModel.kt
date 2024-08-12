package com.example.valuebetsapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BetsViewModel @Inject constructor(private val betsRepository: BetsRepository) : ViewModel() {

    var bets : MutableLiveData<MutableList<Bet>> = MutableLiveData()

    init {
        fetchData()
    }

     fun fetchData(){
        viewModelScope.launch(IO) {
            bets.postValue(betsRepository.getBets())
        }
    }
}