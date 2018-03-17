package com.andro.swap.fragment.review

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.andro.swap.R

class ReviewFragment : Fragment() {

    companion object {
        fun newInstance(): ReviewFragment {
            return ReviewFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_review, container, false)
    }
}
