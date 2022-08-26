package app.ishizaki.ryu.devgoal.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BookmarkViewModel: ViewModel() {
    var urlTitle = MutableLiveData<String>()
    var memo = MutableLiveData<String>()

}