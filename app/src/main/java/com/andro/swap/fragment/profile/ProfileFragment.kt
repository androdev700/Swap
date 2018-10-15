package com.andro.swap.fragment.profile


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andro.swap.R
import com.andro.swap.activity.WelcomeActivity
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : androidx.fragment.app.Fragment() {

    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profile_name.text = context?.getSharedPreferences("userData", Context.MODE_PRIVATE)?.getString("userName", "No Data")
        profile_email.text = context?.getSharedPreferences("userData", Context.MODE_PRIVATE)?.getString("email", "No Data")
        Glide.with(view)
                .load(context?.getSharedPreferences("userData", Context.MODE_PRIVATE)?.getString("photoUrl", "No Data"))
                .into(profile_image)

        profile_logout.setOnClickListener {
            AuthUI.getInstance().signOut(context!!)
            activity?.finish()
            startActivity(Intent(context, WelcomeActivity::class.java))
        }
    }
}
