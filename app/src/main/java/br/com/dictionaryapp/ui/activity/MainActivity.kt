package br.com.dictionaryapp.ui.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.dictionaryapp.databinding.ActivityMainBinding
import br.com.dictionaryapp.feature_dictionary.presentation.WordInfoViewModel
import br.com.dictionaryapp.ui.adapter.MainAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: WordInfoViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val mainAdapter by lazy { MainAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editTextTextPersonName.setText(viewModel.searchQuery.value)
        binding.editTextTextPersonName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.onSearch(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        observer()
    }

    override fun onStart() {
        super.onStart()
        configRecycle()
    }


    private fun configRecycle() {
        binding.rv.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun observer() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    val wordInfoItems = it.wordInfoItems
                    if (it.isLoading) {
                        Toast.makeText(this@MainActivity, "rodando", Toast.LENGTH_SHORT).show()
                    } else {
                        if (wordInfoItems.isNotEmpty()) {
                            mainAdapter.words = wordInfoItems
                        } else {
                            mainAdapter.words = emptyList()
                        }
                    }
                }
            }
        }
    }
}