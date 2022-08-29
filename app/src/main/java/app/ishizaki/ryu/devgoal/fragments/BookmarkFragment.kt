package app.ishizaki.ryu.devgoal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import app.ishizaki.ryu.devgoal.*
import kotlinx.android.synthetic.main.fragment_bookmark.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class BookmarkFragment : Fragment(), AddBookmarkFragment.AddedBookmarkListener {

    override fun clickedSaveBookmarkButton() {
        Toast.makeText(requireContext(), "aaa", Toast.LENGTH_SHORT).show()

        val db = Utils.getDatabase(requireContext())
        val bookmarkAdapter = BookmarkRecyclerviewAdapter(requireContext())
        val bookmarkDao = db.bookmarkDao()

        lifecycleScope.launch(Dispatchers.Default){
            val all = bookmarkDao.getAll()
            withContext(Dispatchers.Main){
                bookmarkAdapter.update(all)
            }
        }


    }



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
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
//            layoutManager = LinearLayoutManager(requireContext())
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

            val bookmarkDao = db.bookmarkDao()

            lifecycleScope.launch(Dispatchers.Default){
                val all = bookmarkDao.getAll()
                withContext(Dispatchers.Main){
                    bookmarkAdapter.update(all)
                }
            }



            AddBookmarkFragment().show(childFragmentManager, "newBookmarkTag")
        }

    }



}