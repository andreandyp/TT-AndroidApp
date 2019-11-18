package com.apptt.axdecor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.apptt.axdecor.R
import com.apptt.axdecor.databinding.ModeloItemBinding
import com.apptt.axdecor.databinding.PinturaItemBinding
import com.apptt.axdecor.domain.Model
import com.apptt.axdecor.domain.ModelWithCategory
import com.apptt.axdecor.domain.Paint

class CatalogoAdapter(
    private val callback: (modelo: Any) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var modelos: List<Any> = emptyList()
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

    class PaintViewHolder(var binding: PinturaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.pintura_item
        }
    }

    override fun getItemCount() = modelos.size
    override fun getItemViewType(position: Int): Int {
        return when (modelos[position]) {
            is ModelWithCategory -> 0
            else -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                ModelViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        ModelViewHolder.LAYOUT,
                        parent,
                        false
                    )
                )
            }
            else -> {
                PaintViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        PaintViewHolder.LAYOUT,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (modelos[position]) {
            is ModelWithCategory -> {
                holder as ModelViewHolder
                holder.binding.also { binding ->
                    val modelo = modelos[position]
                    binding.modelo = modelo as ModelWithCategory
                    holder.itemView.setOnClickListener {
                        callback(modelo)
                    }

                    binding.executePendingBindings()
                }
            }
            else -> {
                holder as PaintViewHolder
                holder.binding.also { binding ->
                    val modelo = modelos[position]
                    binding.paint = modelo as Paint
                    holder.itemView.setOnClickListener {
                        callback(modelo)
                    }

                    binding.executePendingBindings()
                }
            }
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Model>() {
        override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean {
            return oldItem.idModel == newItem.idModel
        }
    }
}
