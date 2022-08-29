package app.ishizaki.ryu.devgoal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.ishizaki.ryu.devgoal.dataclass.Stopwatch
import com.google.android.material.chip.Chip
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.w3c.dom.Text

class ChartDetailCommitAdapter(context: Context): RecyclerView.Adapter<ChartDetailCommitAdapter.ChartDetailCommitViewHolder>() {

    val commitList: MutableList<Stopwatch> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartDetailCommitViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.chart_detail_commit_cell, parent, false)
        return ChartDetailCommitViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ChartDetailCommitViewHolder, position: Int) {

        val commit = commitList[position]

        CoroutineScope(Dispatchers.Default).launch{

            val url = commit.commitUrl

            if (URLUtil.isValidUrl(url)){
                val commitTitleClass = Jsoup.connect(url).get().getElementsByClass("commit-title").firstOrNull()
                if (commitTitleClass != null){
                    withContext(Dispatchers.Main){
                        holder.commitTextChip.text = "[" + commitTitleClass?.html() + "]"
                    }
                }else{
                    withContext(Dispatchers.Main){
                        holder.commitTextChip.text = "((github以外のurlを検出))"
                    }

                }
            }else{
                withContext(Dispatchers.Main){
                    holder.commitTextChip.text = url
                }
            }

        }




    }



    override fun getItemCount(): Int {
        return commitList.size
    }

    fun update(list: List<Stopwatch>){
        commitList.clear()
        commitList.addAll(list)
        notifyDataSetChanged()
    }

    class ChartDetailCommitViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val commitTextChip: TextView = view.findViewById(R.id.commitTextChip)
    }


}