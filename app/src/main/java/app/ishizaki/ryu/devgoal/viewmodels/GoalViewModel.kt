package app.ishizaki.ryu.devgoal.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class GoalViewModel: ViewModel() {
    var goalText = MutableLiveData<String>()
    var goalDueDate = MutableLiveData<Date>()
}