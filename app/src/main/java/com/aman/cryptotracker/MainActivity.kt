package com.aman.cryptotracker

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.aman.cryptotracker.adapters.CryptoAdapter
import com.aman.cryptotracker.databinding.ActivityMainBinding
import com.aman.cryptotracker.viewmodels.CryptoUiState
import com.aman.cryptotracker.viewmodels.CryptoViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var cryptoAdapter: CryptoAdapter

    private  val viewModel: CryptoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        observeUiState()
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect {

                    when(it){

                        is CryptoUiState.Success -> {
                            binding.progressBar.isVisible = false
                            binding.rcvCrpto.isVisible = true
                            binding.tvError.isVisible = false
                            cryptoAdapter.submitList(it.cryptoList)
                        }

                        is CryptoUiState.Loading -> {
                            binding.progressBar.isVisible = true
                            binding.rcvCrpto.isVisible = false
                            binding.tvError.isVisible = false
                        }

                        is CryptoUiState.Error -> {
                            binding.progressBar.isVisible = false
                            binding.rcvCrpto.isVisible = false
                            binding.tvError.isVisible = true

                            binding.tvError.text = it.message
                        }
                    }

                }
            }

        }
    }

    private fun setupRecyclerView() {
      cryptoAdapter = CryptoAdapter()

        binding.rcvCrpto.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = cryptoAdapter
        }
    }

}
