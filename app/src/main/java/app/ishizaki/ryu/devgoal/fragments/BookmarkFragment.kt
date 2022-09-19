package app.ishizaki.ryu.devgoal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import app.ishizaki.ryu.devgoal.*
import kotlinx.android.synthetic.main.fragment_add_bookmark.*
import kotlinx.android.synthetic.main.fragment_bookmark.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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

        setFragmentResultListener("requestKey") { key, bundle ->

            lifecycleScope.launch(Dispatchers.Default) {
                val bookmarkDao = db.bookmarkDao()
                val all = bookmarkDao.getAll()
                withContext(Dispatchers.Main) {

                    bookmarkAdapter.update(all)
                }
            }

        }


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
//            val transaction = (requireContext() as FragmentActivity).supportFragmentManager.beginTransaction()
//            transaction.add(R.id.add_bookmark_container, AddBookmarkFragment()).commit()
            AddBookmarkFragment().show(childFragmentManager, "add_bookmark_fragment")
        }

    }



}