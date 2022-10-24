package com.example.parstagram.fragments

import android.util.Log
import com.example.parstagram.MainActivity
import com.example.parstagram.Post
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser

class ProfileFragment: FeedFragment() {

    override fun queryPosts() {
        // Specify which class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        // Find all post objects and return it
        query.include(Post.KEY_USER)
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser())
        query.addDescendingOrder("createdAt")
        query.limit = 20
        query.findInBackground(object : FindCallback<Post> {
            override fun done(posts : MutableList<Post>?, e: ParseException?) {
                if(e != null)
                {
                    // Something wrong
                    Log.e(TAG, "Error fetching posts")
                } else {
                    if(posts != null) {
                        for(post in posts) {
                            Log.i(
                                MainActivity.TAG, "Post: " + post.getDescription() + " ,username: " +
                                        post.getUser()?.username)
                        }

                        allPosts.addAll(posts)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}