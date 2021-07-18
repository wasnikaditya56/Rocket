package com.aditya.spacexrockets

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import com.aditya.spacexrockets.Rocket
import com.aditya.spacexrockets.databinding.RocketFragmentBinding
import com.aditya.spacexrockets.RocketAdapter.OnRocketClickListener
import com.aditya.spacexrockets.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RocketFragment : Fragment(R.layout.rocket_fragment), OnRocketClickListener {

    private val viewModel: RocketViewModel by viewModels()

    private var _binding: RocketFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = RocketFragmentBinding.bind(view)

        val rocketAdapter = RocketAdapter(this)
        with(binding) {
            rvRocket.apply {
                adapter = rocketAdapter
                setHasFixedSize(true)
            }
        }

        viewModel.rockets.observe(viewLifecycleOwner) { result ->
            rocketAdapter.submitList(result.data)
            with(binding) {
                loadingAnim.isVisible = result is Result.Loading && result.data.isNullOrEmpty()
                errorAnim.isVisible = result is Result.Error && result.data.isNullOrEmpty()
                tvError.isVisible = result is Result.Error && result.data.isNullOrEmpty()
                tvError.text = result.error?.localizedMessage
            }
        }
    }

    override fun onClick(rocket: Rocket) {
        val action = RocketFragmentDirections.actionGlobalWebViewFragment(rocket.wikiUrl)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}