package com.example.parstagram

import android.content.Intent
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.parstagram.Post
import com.example.parstagram.fragments.ComposeFragment
import com.example.parstagram.fragments.FeedFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.*
import java.io.File

/**
 * Let user create post by taking a photo with camera
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
            item ->
            var fragmentToShow : Fragment? = null
            when(item.itemId) {
                R.id.action_home -> {
                    fragmentToShow = FeedFragment()
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                }
                R.id.action_compose -> {
                    fragmentToShow = ComposeFragment()
                }
                R.id.action_profile -> {
                    Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                }
            }

            if (fragmentToShow != null) {
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow).commit()
            }

            true
        }
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.action_home
    }

    // Query for all posts in our server
    fun queryPosts() {
        // Specify which class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        // Find all post objects and return it
        query.include(Post.KEY_USER)
        query.findInBackground(object : FindCallback<Post> {
            override fun done(posts : MutableList<Post>?, e: ParseException?) {
                if(e != null)
                {
                    // Something wrong
                    Log.e(TAG, "Error fetching posts")
                } else {
                    if(posts != null) {
                        for(post in posts) {
                            Log.i(TAG, "Post: " + post.getDescription() + " ,username: " +
                                    post.getUser()?.username)
                        }
                    }
                }
            }
        })
    }

    companion object {
        const val TAG = "MainActivity"
    }


}