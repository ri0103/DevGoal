package app.ishizaki.ryu.devgoal.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import app.ishizaki.ryu.devgoal.dataclass.Bookmark

class BookmarkViewModel: ViewModel() {

    private val bookmarksRaw = mutableListOf<Bookmark>()

    private val _bookmarks = MutableLiveData<List<Bookmark>>(emptyList())
    val bookmarks: LiveData<List<Bookmark>> = _bookmarks.distinctUntilChanged()

    fun addBookmark(){
//        bookmarksRaw.add()
        _bookmarks.value = ArrayList(bookmarksRaw)
    }

    fun onClickItem(item: Bookmark){

    }



    var urlTitle = MutableLiveData<String>()
    var memo = MutableLiveData<String>()

}