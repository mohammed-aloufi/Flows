package com.example.flows

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var stateFlowTv: TextView
    private lateinit var sharedFlowTv: TextView
    private lateinit var stateFlowBtn: Button
    private lateinit var sharedFlowBtn: Button


    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stateFlowTv = findViewById(R.id.stateFlowTv)
        sharedFlowTv = findViewById(R.id.sharedFlowTv)
        stateFlowBtn = findViewById(R.id.stateFlowBtn)
        sharedFlowBtn = findViewById(R.id.sharedFlowBtn)

        stateFlowBtn.setOnClickListener {
            viewModel.triggerStateFlow()
        }

        sharedFlowBtn.setOnClickListener {
            viewModel.triggerSharedFlow()
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.stateFlow.collectLatest {
                    stateFlowTv.text = it.toString()
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.sharedFlow.collectLatest {
                    sharedFlowTv.text = it.toString()
                }
            }
        }
    }
}