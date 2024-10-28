package com.dicoding.event.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.dicoding.event.R
import com.dicoding.event.data.local.EventEntity
import com.dicoding.event.data.response.EventDetailResponse
import com.dicoding.event.databinding.FragmentEventDetailBinding
import com.dicoding.event.ui.EventViewModel
import com.dicoding.event.ui.ViewModelFactory
import com.dicoding.event.utils.Result
import java.text.SimpleDateFormat
import java.util.Locale

class EventDetailFragment : Fragment() {

    private var _binding: FragmentEventDetailBinding? = null

    private val binding get() = _binding

    private lateinit var eventItem: EventEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventDetailBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val eventId = arguments?.getInt("event_id") ?: -1
        val passedEventData: EventEntity = arguments?.getParcelable("event_item")!!

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: EventViewModel by viewModels {
            factory
        }


        viewModel.fetchEventDetail(eventId).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        val eventData = result.data

                        eventItem = EventEntity(
                            eventId = passedEventData.eventId,
                            name = passedEventData.name,
                            mediaCover = passedEventData.mediaCover,
                            isActive = passedEventData.isActive,
                            isFavorite = passedEventData.isFavorite
                        )
                        setEventDetailData(eventData)
                        toggleFavorite(!eventItem.isFavorite)
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

        binding?.btnFavorite?.setOnClickListener {
            toggleFavorite(eventItem.isFavorite)
            if (!eventItem.isFavorite) {
                viewModel.saveEvents(eventItem)
            } else {
                viewModel.deleteEvents(eventItem)
            }
        }

    }

    private fun setEventDetailData(eventDetailList: EventDetailResponse) {
        val eventDetail = eventDetailList.event
        binding?.apply {
            Glide.with(root)
                .load(eventDetail.imageLogo)
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_error)
                .into(imgMediaCover)
            tvName.text = eventDetail.name
            tvOwnerName.text = eventDetail.ownerName
            val quota = (eventDetail.quota - eventDetail.registrants).toString()
            tvQuota.text = quota
            tvTime.text = formatEventTime(eventDetail.beginTime, eventDetail.endTime)
            tvDescription.text = HtmlCompat.fromHtml(
                eventDetail.description,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            btnLink.setOnClickListener {
                val url = eventDetail.link
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
        }
    }


    private fun formatEventTime(beginTime: String, endTime: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val outputTimeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

        return try {
            val parsedBeginTime = inputFormat.parse(beginTime)
            val parsedEndTime = inputFormat.parse(endTime)

            val formattedDate = parsedBeginTime?.let { outputDateFormat.format(it) }
            val formattedBeginTime = parsedBeginTime?.let { outputTimeFormat.format(it) }
            val formattedEndTime = parsedEndTime?.let { outputTimeFormat.format(it) }

            "$formattedDate, $formattedBeginTime - $formattedEndTime"

        } catch (e: Exception) {
            Log.e("EventDetailFragment", "onError : ${e.message.toString()}")
            "Invalid input format"
        }
    }

    private fun toggleFavorite(isFavorite: Boolean) {
        binding?.btnFavorite?.setImageResource(if (!isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border)
    }

}
