package app.ishizaki.ryu.devgoal.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import app.ishizaki.ryu.devgoal.R
import app.ishizaki.ryu.devgoal.Utils
import app.ishizaki.ryu.devgoal.dataclass.Bookmark
import kotlinx.android.synthetic.main.activity_add_bookmark.*
import kotlinx.android.synthetic.main.item_bookmark_cell.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddBookmarkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bookmark)

        val db = Utils.getDatabase(applicationContext)

        saveBookmarkButton.setOnClickListener {
            val bookmark = Bookmark(0, addUrlEditText.text.toString(), addMemoEditText.text.toString(), System.currentTimeMillis())

            lifecycleScope.launch(Dispatchers.Default){
                val bookmarkDao = db.bookmarkDao()
                bookmarkDao.insert(bookmark)
                val all = bookmarkDao.getAll()
                Log.d("framgnents", all.toString())
            }

            finish()

        }


    }
}