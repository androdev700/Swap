package com.andro.swap.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.ArrayAdapter
import com.andro.swap.R
import com.andro.swap.model.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : AppCompatActivity() {

    companion object {
        const val TAG = "SearchActivity"
    }

    private var usersList: ArrayList<User>? = null
    private var userListener: ValueEventListener? = null
    private var userReference: DatabaseReference? = null
    private val userNameList = ArrayList<String>()
    private val filterNameList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(app_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        userReference = FirebaseDatabase.getInstance().reference.child("userdata")
        usersList = ArrayList()

        userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot.children
                children.forEach {
                    usersList?.add(it.getValue<User>(User::class.java)!!)
                }

                usersList?.forEach {
                    userNameList.add(it.name!!)
                    filterNameList.add(it.name!!)
                }

                user_search_list.adapter = ArrayAdapter<String>(this@SearchActivity, android.R.layout.simple_list_item_1, userNameList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        userReference?.addValueEventListener(userListener)

        search_user_button.setOnClickListener {
            filterNameList.clear()
            userNameList.forEach {
                if (it.contains(search_query_text.text.toString(), true)) {
                    filterNameList.add(it)
                }
            }
            user_search_list.adapter = ArrayAdapter<String>(this@SearchActivity, android.R.layout.simple_list_item_1, filterNameList)
        }

        user_search_list.setOnItemClickListener { parent, view, position, id ->
            usersList?.forEach {
                if (it.name?.equals(filterNameList.get(position))!!) {
                    val intent = Intent(this, ViewProfile::class.java)
                    intent.putExtra("userdata", it)
                    Log.d(TAG, "Chosen user = ${it.name}")
                    startActivity(intent)
                }
            }
        }
    }

    override fun onPause() {
        userReference?.removeEventListener(userListener)
        super.onPause()
    }
}
