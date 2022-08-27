package app.ishizaki.ryu.devgoal

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import app.ishizaki.ryu.devgoal.dataclass.Bookmark
import app.ishizaki.ryu.devgoal.fragments.BookmarkDetailFragment
import app.ishizaki.ryu.devgoal.fragments.EditTaskFragment
import app.ishizaki.ryu.devgoal.viewmodels.BookmarkViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import java.net.URL

class BookmarkRecyclerviewAdapter(context: Context): RecyclerView.Adapter<BookmarkRecyclerviewAdapter.BookmarkViewHolder>() {

    val bookmarkList: MutableList<Bookmark> = mutableListOf()
//    private lateinit var listener: OnTaskCellClickListener
    private lateinit var bookmarkViewModel: BookmarkViewModel


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_bookmark_cell, parent, false)
        return BookmarkViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {


//        bookmarkViewModel = ViewModelProvider().get(BookmarkViewModel::class.java)
//
//        bookmarkViewModel.urlTitle.observe(this){
//
//        }


        holder.shimmerFrameLayout.startShimmerAnimation()

        val bookmark = bookmarkList[position]
        val scope = CoroutineScope(Dispatchers.Default)

        scope.launch{

            val urlTitle = Jsoup.connect(bookmark.url).get().title()
            val imageTag = Jsoup.connect(bookmark.url).get().select("img").firstOrNull()
            val imageUrl = imageTag?.absUrl("src")
            val imageBMP = URL(imageUrl).openStream().use { BitmapFactory.decodeStream(it) }

            withContext(Dispatchers.Main){
                holder.urlTextView.text = urlTitle
                holder.urlImageView.setImageBitmap(imageBMP)
                holder.memoTextView.text = bookmark.memo
                holder.shimmerFrameLayout.stopShimmerAnimation()


                holder.bookmarkCell.setOnClickListener {
                    val bookmarkDetailFragment = BookmarkDetailFragment()
                    val bundle = Bundle()
                    bundle.putInt("ID", bookmark.id)
                    bundle.putString("TITLE", urlTitle)
                    bundle.putString("URL", bookmark.url)
                    bundle.putString("MEMO", bookmark.memo)
                    bookmarkDetailFragment.arguments = bundle

                    val transaction = (holder.itemView.context as FragmentActivity).supportFragmentManager.beginTransaction()
                    transaction.add(R.id.bookmark_detail_container, bookmarkDetailFragment).commit()
                }

            }
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