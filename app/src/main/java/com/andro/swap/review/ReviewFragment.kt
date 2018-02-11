package com.andro.swap.review


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.andro.swap.R


/**
 * A simple [Fragment] subclass.
 */
class ReviewFragment : Fragment() {

    companion object {
        fun newInstance(): ReviewFragment {
            return ReviewFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false)
    }

}// Required empty public constructor
