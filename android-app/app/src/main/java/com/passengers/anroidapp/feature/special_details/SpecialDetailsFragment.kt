package com.passengers.anroidapp.feature.special_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.passengers.anroidapp.R
import com.passengers.anroidapp.core.BaseFragment
import com.passengers.anroidapp.core.RosbankApplication.Companion.ARG_ITEM
import com.passengers.anroidapp.core.SimpleDisposable
import com.passengers.anroidapp.feature.news.NewsViewModel
import com.passengers.anroidapp.network.model.FeedItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class SpecialDetailsFragment : BaseFragment() {

    val specialsViewModel : SpecialDetailsViewModel by viewModel()

    lateinit var title: TextView
    lateinit var description: TextView
    lateinit var photo: ImageView

    companion object {


        fun newInstance(feedItem: FeedItem): SpecialDetailsFragment {

            val args: Bundle = Bundle()
            args.putSerializable(ARG_ITEM, feedItem)

            val fragment = SpecialDetailsFragment()
            fragment.arguments = args

            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        specialsViewModel.init()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_special_details, container, false)

        title = view.findViewById(R.id.title)
        description = view.findViewById(R.id.description)
        photo = view.findViewById(R.id.photo)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val feedItem: FeedItem = arguments?.get(ARG_ITEM) as FeedItem

        title.text = feedItem.title
        description.text = feedItem.simpleDescription

        Glide
                .with(view)
                .load(feedItem.imageUrl)
                .centerCrop()
                .into(photo)
    }

}