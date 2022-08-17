package app.ishizaki.ryu.devgoal

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import app.ishizaki.ryu.devgoal.dataclass.Bookmark
import app.ishizaki.ryu.devgoal.room.AppDatabase
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import java.net.URL

class BookmarkRecyclerviewAdapter(context: Context): RecyclerView.Adapter<BookmarkRecyclerviewAdapter.BookmarkViewHolder>() {

    val bookmarkList: MutableList<Bookmark> = mutableListOf()
//    private lateinit var listener: OnTaskCellClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_bookmark_cell, parent, false)
        return BookmarkViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.shimmerFrameLayout.startShimmerAnimation()

        val bookmark = bookmarkList[position]
        val scope = CoroutineScope(Dispatchers.Default)

        scope.launch{

            val urlTitle = Jsoup.connect(bookmark.url).get().title()

            val imageTag = Jsoup.connect(bookmark.url).get().select("img").first()


            val imageUrl = imageTag?.absUrl("src")
            val imageBMP = URL(imageUrl).openStream().use { BitmapFactory.decodeStream(it) }

            withContext(Dispatchers.Main){
                holder.urlTextView.text = urlTitle
                holder.urlImageView.setImageBitmap(imageBMP)
                holder.memoTextView.text = bookmark.memo
                holder.shimmerFrameLayout.stopShimmerAnimation()
            }
        }

        holder.bookmarkCell.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(bookmark.url))
            holder.itemView.context.startActivity(intent)
        }





    }



//    interface  OnTaskCellClickListener {
//        fun onItemClick(task: Task)
//    }

//    fun setOnTaskCellClickListener(listener: OnTaskCellClickListener) {
//        this.listener = listener
//    }

    override fun getItemCount(): Int {
        return bookmarkList.size
    }

    fun update(list: List<Bookmark>){
        bookmarkList.clear()
        bookmarkList.addAll(list)
//        bookmarkList.clear()
        notifyDataSetChanged()
    }

    class BookmarkViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val urlTextView: TextView= view.findViewById(R.id.urlTextView)
        val memoTextView: TextView= view.findViewById(R.id.memoTextView)
        val bookmarkCell: LinearLayout = view.findViewById(R.id.bookmarkCell)
        val urlImageView: ImageView = view.findViewById(R.id.urlImageView)
        val shimmerFrameLayout: ShimmerFrameLayout = view.findViewById(R.id.shimmerFrameLayout)

    }


}