package com.mrea.weatherapp

import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.mrea.weatherapp.databinding.MainActivityBinding
import com.mrea.weatherapp.domain.WeatherType.*
import com.mrea.weatherapp.presentation.hideKeyboard
import com.mrea.weatherapp.presentation.showKeyboard
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDatabinding()
        setListeners()
    }

    private fun initDatabinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.lifecycleOwner = this
        binding.setVariable(BR.uiState, viewModel.uiStateLiveData)
        viewModel.uiStateLiveData.observe(this, ::onUiStateUpdated)
    }

    private fun setListeners() {
        binding.refreshIcon.setOnClickListener { viewModel.onRefresh() }
        binding.errorTryAgainIcon.setOnClickListener { viewModel.onRefresh() }
        binding.toolbar.setOnClickListener { viewModel.onSearch() }

        binding.searchBox.editText?.doOnTextChanged { text: CharSequence?, _: Int, _: Int, _: Int ->
            viewModel.onSearchTextChanged(text.toString())
        }
        binding.animationRadioGroup.setOnCheckedChangeListener { radioGroup: RadioGroup, id: Int ->
            when (id) {
                R.id.snow -> viewModel.onAnimationChanged(Snow)
                R.id.rain -> viewModel.onAnimationChanged(Rain)
                R.id.sun -> viewModel.onAnimationChanged(Sunny)
                R.id.clouds -> viewModel.onAnimationChanged(Cloudy)
            }
            binding.drawerLayout.closeDrawers()
        }
    }

    private fun onUiStateUpdated(uiState: MainUiState) {
        Timber.d("Received UiState: $uiState")

        binding.searchBox.editText?.run {
            if (uiState.showKeyboard) showKeyboard() else hideKeyboard()
        }
    }

    override fun onPause() {
        super.onPause()
        binding.searchBox.editText?.hideKeyboard()
    }

    override fun onResume() {
        super.onResume()
        binding.searchBox.editText?.run {
            if (viewModel.uiState.showKeyboard) showKeyboard() else hideKeyboard()
        }
    }
}