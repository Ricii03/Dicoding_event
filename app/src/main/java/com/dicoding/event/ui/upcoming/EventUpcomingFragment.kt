package com.dicoding.event.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dicoding.event.R
import com.dicoding.event.databinding.FragmentEventUpcomingBinding
import com.dicoding.event.ui.EventListAdapter
import com.dicoding.event.ui.EventViewModel
import com.dicoding.event.ui.ViewModelFactory
import com.dicoding.event.utils.Result

class EventUpcomingFragment : Fragment() {

    private var _binding: FragmentEventUpcomingBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventUpcomingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: EventViewModel by viewModels {
            factory
        }

        val eventAdapter = EventListAdapter { eventItem ->
            val bundle = Bundle().apply {
                putInt("event_id", eventItem.eventId.toInt())
                putParcelable("event_item", eventItem)
            }
            findNavController().navigate(
                R.id.action_navigation_upcoming_to_eventDetailFragment,
                bundle
            )
        }

        viewModel.fetchEvent(1).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        val eventData = result.data
                        eventAdapter.submitList(eventData)
                    }

                    is Result.Error -> {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(
                            context,
                            "Error occurs:" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding?.rvUpcomingEvents?.apply {
            adapter = eventAdapter
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}