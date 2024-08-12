package com.example.valuebetsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.valuebetsapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val betsViewModel : BetsViewModel by viewModels()
    private lateinit var betAdapter : BetListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupCarRv()
        loadBets()

        binding.loadBtn.setOnClickListener {
            binding.betsPb.visibility = View.VISIBLE
            binding.betsRv.visibility = View.GONE
            betsViewModel.fetchData()
        }
    }

    private fun setupCarRv() {
        betAdapter = BetListAdapter()
        binding.betsRv.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = betAdapter
        }
    }

    private fun loadBets() {
        betsViewModel.bets.observe(this) {
            binding.betsPb.visibility = View.VISIBLE
            if (it.isEmpty()) {
                binding.betsPb.visibility = View.GONE
            } else {
                binding.betsRv.visibility = View.VISIBLE
                binding.betsPb.visibility = View.GONE
                betAdapter.submitList(it)
            }
        }
    }
}