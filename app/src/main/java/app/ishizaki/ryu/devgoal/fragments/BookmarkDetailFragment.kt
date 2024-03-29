package app.ishizaki.ryu.devgoal.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
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


class BookmarkDetailFragment : DialogFragment() {




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
        val bookmarkId = bundle!!.getInt("ID")
        val title = bundle!!.getString("TITLE")
        val url = bundle!!.getString("URL")
        val memo = bundle!!.getString("MEMO")

        detailUrlTextView.text = url
        detailTitleTextView.text = title
        detailMemoTextView.text = memo

        openBookmarkWebsiteButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        deleteBookmarkButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default){
                val bookmarkToDelete = bookmarkDao.getByBookmarkId(bookmarkId)
                bookmarkDao.delete(bookmarkToDelete)
                setFragmentResult("requestKey", bundleOf("resultKey" to "result"))
                dismiss()

            }



        }





    }





}