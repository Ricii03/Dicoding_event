package com.dicoding.event.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.event.R
import com.dicoding.event.databinding.FragmentEventFavoriteBinding
import com.dicoding.event.ui.EventListAdapter
import com.dicoding.event.ui.EventViewModel
import com.dicoding.event.ui.ViewModelFactory

class EventFavoriteFragment : Fragment() {

    private var _binding: FragmentEventFavoriteBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventFavoriteBinding.inflate(inflater, container, false)
        return _binding?.root
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
                R.id.action_eventFavoriteFragment_to_eventDetailFragment,
                bundle
            )
        }

        viewModel.getFavoriteEvent().observe(viewLifecycleOwner) { event ->
            if (event.isEmpty()) {
                binding?.tvFavoriteEvent?.visibility = View.VISIBLE
            } else {
                binding?.tvFavoriteEvent?.visibility = View.GONE
                eventAdapter.submitList(event)
            }
        }

        binding?.rvFavoriteEvent?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = eventAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
