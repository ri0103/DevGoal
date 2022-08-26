package app.ishizaki.ryu.devgoal.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import app.ishizaki.ryu.devgoal.R
import app.ishizaki.ryu.devgoal.Utils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bookmark_detail.*
import kotlinx.android.synthetic.main.item_bookmark_cell.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup


class BookmarkDetailFragment : BottomSheetDialogFragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = Utils.getDatabase(requireContext())
        val bookmarkDao = db.bookmarkDao()



        val bundle = arguments
        val bookmarkId = bundle!!.getInt("BOOKMARKID")
        val url = bundle!!.getString("URL")
        val memo = bundle!!.getString("MEMO")

        openBookmarkWebsiteButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        lifecycleScope.launch(Dispatchers.Default){

            val urlTitle = Jsoup.connect(url).get().title()

            withContext(Dispatchers.Main){
                detailUrlTextView.text = url
                detailTitleTextView.text = urlTitle
                detailMemoTextView.text = memo
            }

        }

        deleteBookmarkButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default){
                val allBookmark = bookmarkDao.getAll()
                bookmarkDao.delete(allBookmark[bookmarkId])
                dismiss()
            }

        }



    }


}