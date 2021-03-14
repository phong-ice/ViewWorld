package com.example.watchworld.Adapter

import android.content.Context
import android.content.Intent
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.watchworld.R
import com.example.watchworld.Search
import kotlinx.android.synthetic.main.item_list_search.view.*

class AdapterSearch(
    val context: Context,
    val listTag: ArrayList<String>,
    val listTagBackup: ArrayList<String>
) : RecyclerView.Adapter<AdapterSearch.ViewHolder>(), Filterable {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var item = view.tv_itemSearch
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_search, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listTag.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item.text = listTag[position]
        holder.itemView.setOnClickListener {
            var intent = Intent(context,Search::class.java)
            intent.putExtra("searchPattern",listTag[position])
            context.startActivity(intent)
        }
    }

    override fun getFilter(): Filter {
       return filter
    }

    private var filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var _listTag: ArrayList<String> = ArrayList()

            if (constraint == null || constraint.length == 0) {
                _listTag.addAll(listTagBackup)
            } else {
                var searchPattern = constraint.toString().toLowerCase().trim()
                for (tag in listTag) {
                    if (tag.contains(searchPattern)) {
                        _listTag.add(tag)

                    }
                }
            }

            var result = FilterResults()
            result.values = _listTag
            return result
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            listTag.clear()
            listTag.addAll(results?.values as ArrayList<String>)
            notifyDataSetChanged()
        }

    }

    public fun pushTag(tag:String):String{
        return tag
    }


}
