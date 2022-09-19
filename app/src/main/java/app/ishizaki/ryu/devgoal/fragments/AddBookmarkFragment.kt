package app.ishizaki.ryu.devgoal.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import app.ishizaki.ryu.devgoal.R
import app.ishizaki.ryu.devgoal.Utils
import app.ishizaki.ryu.devgoal.dataclass.Bookmark
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_add_bookmark.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddBookmarkFragment : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_bookmark, container, false)
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


                    requireParentFragment().setFragmentResult("requestKey", bundleOf("resultKey" to "result"))

//                    fragmentManager?.beginTransaction()?.remove(this@AddBookmarkFragment)?.commit()
                    dismiss()


                }else{
                    withContext(Dispatchers.Main){
                        Toast.makeText(requireContext(), "有効なURLを入力してください", Toast.LENGTH_SHORT).show()
                    }
                }


            }



        }


    }

}