package com.apptt.axdecor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apptt.axdecor.R
import com.apptt.axdecor.databinding.ModeloItemBinding
import com.apptt.axdecor.domain.Model

class CatalogoAdapter : ListAdapter<Model, CatalogoAdapter.ModelViewHolder>(DiffCallback) {
    var modelos: List<Model> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ModelViewHolder(var binding: ModeloItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.modelo_item
        }

    }

    override fun getItemCount() = modelos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val withDataBinding: ModeloItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ModelViewHolder.LAYOUT,
            parent,
            false
        )
        return ModelViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        holder.binding.also {
            it.modelo = modelos[position]
        }
    }

    /*class OnClickListener(val clickListener: (modelo: Model) -> Unit) {
        fun onClick(modelo: Model) = clickListener(modelo)
    }*/

    companion object DiffCallback : DiffUtil.ItemCallback<Model>() {
        override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean {
            return oldItem.idModel == newItem.idModel
        }
    }
}
