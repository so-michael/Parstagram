package com.example.parstagram


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.parse.ParseException
import java.util.*

private val SECOND_MILLIS = 1000;
private val MINUTE_MILLIS = 60 * SECOND_MILLIS
private val HOUR_MILLIS = 60 * MINUTE_MILLIS
private val DAY_MILLIS = 24 * HOUR_MILLIS
class PostAdapter(val context: Context, val posts: List<Post>)
    : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ViewHolder {
        //Speciify the layout file to use for this item
        val view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostAdapter.ViewHolder, position: Int) {
        val post = posts.get(position)
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return posts.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvUsername: TextView
        val ivImage: ImageView
        val tvDescription: TextView
        val tvTimestamp: TextView

        init {
            tvUsername = itemView.findViewById(R.id.tvUserName)
            ivImage = itemView.findViewById(R.id.ivImageView)
            tvDescription = itemView.findViewById(R.id.tvDescription)
            tvTimestamp = itemView.findViewById(R.id.tvTime)

        }
        fun bind(post: Post){
            tvDescription.text = post.getDescription()
            tvUsername.text = post.getUser()?.username
//            tvTimestamp.text = post.createdAt.toString()
            tvTimestamp.text = getRelativeTimeAgo(post.createdAt).toString()

            //populate the image view
            Glide.with(itemView.context).load(post.getImage()?.url).into(ivImage)

        }

        fun getRelativeTimeAgo(date: Date) : String{
            try {
                val time = date.time
                val now = System.currentTimeMillis()

                val diff = now - time
                if(diff < MINUTE_MILLIS) {
                    return "just now"
                } else if(diff < 2 * MINUTE_MILLIS) {
                    return "a minute ago"
                } else if(diff < 60 * MINUTE_MILLIS){
                    val mins = diff / MINUTE_MILLIS
                    return "$mins minutes ago"
                } else if(diff < 120 * MINUTE_MILLIS) {
                    return "an hour ago"
                } else if(diff < 24 * HOUR_MILLIS){
                    val hours = diff / HOUR_MILLIS
                    return "$hours hours ago"
                } else {
                    val days = diff / DAY_MILLIS
                    if(days > 1) {
                        return "$days days ago"
                    }
                    else {
                        return "$days days ago"
                    }
                }
            } catch (e: ParseException) {
                Log.e(TAG, "Relative time Error $e")
            }
            return ""
        }

    }
    companion object {
        val TAG = "Post Adapter"
    }
}