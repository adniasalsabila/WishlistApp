package com.gits.mywishlist

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gits.mywishlist.model.DataItem

class RecyclerViewAdapter(val results: ArrayList<DataItem>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    fun setData(data: List<DataItem>) {
        results.clear()
        results.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        val data = results[position]
        holder.nameItem.text = data.nameItem
        holder.cost.text = data.cost

        holder.buttonEdit.setOnClickListener {
//            holder.itemView.findViewById<EditText>(R.id.textFieldItem).setText(data.nameItem)
//            holder.itemView.findViewById<EditText>(R.id.textFieldCost).setText(data.cost)
            val intent = Intent(holder.itemView.context, MainActivity::class.java)
            intent.putExtra("ID",data.id.toString())
            intent.putExtra("NAMEITEM", data.nameItem)
            intent.putExtra("COST", data.cost)
            intent.setAction("Edit")
            holder.itemView.context.startActivity(intent)
        }

        holder.buttonDelete.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return results.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameItem: TextView = itemView.findViewById(R.id.tv_nameItem)
        val cost: TextView = itemView.findViewById(R.id.tv_costItem)
        val buttonEdit: Button = itemView.findViewById(R.id.buttonEdit)
        val buttonDelete: Button = itemView.findViewById(R.id.buttonDelete)
    }
}