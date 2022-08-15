package app.ishizaki.ryu.devgoal.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import app.ishizaki.ryu.devgoal.BookmarkRecyclerviewAdapter
import app.ishizaki.ryu.devgoal.R
import app.ishizaki.ryu.devgoal.TaskRecyclerviewAdapter
import app.ishizaki.ryu.devgoal.Utils
import app.ishizaki.ryu.devgoal.activities.AddBookmarkActivity
import kotlinx.android.synthetic.main.fragment_bookmark.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import org.w3c.dom.Document
import org.w3c.dom.Element


class BookmarkFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onResume() {
        super.onResume()
        onViewCreated(requireView(), null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = Utils.getDatabase(requireContext())
        val bookmarkAdapter = BookmarkRecyclerviewAdapter(requireContext())

        bookmarkRecyclerview.apply {
//            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bookmarkAdapter
        }

        lifecycleScope.launch(Dispatchers.Default) {
            val bookmarkDao = db.bookmarkDao()
            val all = bookmarkDao.getAll()

            withContext(Dispatchers.Main) {
                bookmarkAdapter.update(all)
            }
        }

        openAddBookmarkActivityButton.setOnClickListener {
            val intent = Intent(activity, AddBookmarkActivity::class.java)
            activity?.startActivity(intent)
        }


//        val url = "https://news.yahoo.co.jp/pickup/6435537"





//        lifecycleScope.launch(Dispatchers.Default){
//            val title = Jsoup.connect(url).get().title()
////            val image = Jsoup.connect(url).get().select("img").first()
//
//            withContext(Dispatchers.Main){
//                    urlTitleTest.setText(title)
//                    urlTitleTest.setOnClickListener {
//                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                        startActivity(intent)
//                    }
//            }
//        }


    }

}