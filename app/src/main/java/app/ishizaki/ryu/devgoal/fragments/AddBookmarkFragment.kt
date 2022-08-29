package app.ishizaki.ryu.devgoal.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import app.ishizaki.ryu.devgoal.R
import app.ishizaki.ryu.devgoal.Utils
import app.ishizaki.ryu.devgoal.activities.MainActivity
import app.ishizaki.ryu.devgoal.dataclass.Bookmark
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_add_bookmark.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup


class AddBookmarkFragment : BottomSheetDialogFragment() {


    private lateinit var listener: AddedBookmarkListener

    interface AddedBookmarkListener {
        fun clickedSaveBookmarkButton()
    }
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        try {
//            val bookmarkFragment: MAin = activity as MainActivity
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_add_bookmark, container, false)
        val saveBookmarkButton: Button = root.findViewById(R.id.saveBookmarkButton)
        saveBookmarkButton.setOnClickListener {
            listener.clickedSaveBookmarkButton()
        }

        return root
//        return inflater.inflate(R.layout.fragment_add_bookmark, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val db = Utils.getDatabase(requireContext())

        saveBookmarkButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {

                if (URLUtil.isValidUrl(addUrlEditText.text.toString())){
                    val bookmark = Bookmark(0, addUrlEditText.text.toString(), addMemoEditText.text.toString(), System.currentTimeMillis())

                    val bookmarkDao = db.bookmarkDao()
                    bookmarkDao.insert(bookmark)
                    val all = bookmarkDao.getAll()
                    Log.d("framgnents", all.toString())

                    dismiss()





                }else{
                    withContext(Dispatchers.Main){
                        Toast.makeText(requireContext(), "有効なURLを入力してください", Toast.LENGTH_SHORT).show()
                    }
                }


            }

        }


    }

//    fun onClickedSave(view: View){
//        listener?.onClickedSave()
//    }


}