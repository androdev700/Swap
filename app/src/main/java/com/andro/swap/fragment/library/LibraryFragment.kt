package com.andro.swap.fragment.library


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.andro.swap.R


/**
 * A simple [Fragment] subclass.
 */
class LibraryFragment : Fragment() {

    companion object {
        fun newInstance(): LibraryFragment {
            return LibraryFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_library, container, false)
    }

}// Required empty public constructor
