package com.andro.swap.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.andro.swap.R
import com.andro.swap.adapter.BookAdapter
import com.andro.swap.model.FollowUser
import com.andro.swap.model.User
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_view_profile.*


class ViewProfile : AppCompatActivity() {

    companion object {
        const val TAG = "ViewProfile"
    }

    private var booksAdapter: BookAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_profile)

        setSupportActionBar(app_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        val user = intent.getSerializableExtra("userdata") as User

        user_email_profile.text = user.email
        user_name_profile.text = user.name
        user_library.text = String.format("%s's Library", user.name)

        booksAdapter = BookAdapter(ArrayList(user.bookCollection?.values), this, this)

        library_profile.layoutManager = GridLayoutManager(this, 2)
        library_profile.setHasFixedSize(true)
        library_profile.adapter = booksAdapter

        user_follow_profile.setOnClickListener {
            val uId = getSharedPreferences("userData", Context.MODE_PRIVATE).getString("uId", "")
            val userName = getSharedPreferences("userData", Context.MODE_PRIVATE)?.getString("userName", "No Data")

            val follower = FollowUser()
            follower.name = userName

            val following = FollowUser()
            following.name = user.name

            val databaseReference = FirebaseDatabase.getInstance().reference.child("userdata").child(user.uid).child("followers").child(uId)
            databaseReference.setValue(follower)

            val followingReference = FirebaseDatabase.getInstance().reference.child("userdata").child(uId).child("following").child(user.uid)
            followingReference.setValue(following)
        }
    }
}
