package com.andro.swap.fragment.library


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.andro.swap.R

class LibraryFragment : Fragment() {

    companion object {
        fun newInstance(): LibraryFragment {
            return LibraryFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_library, container, false)
    }
}
