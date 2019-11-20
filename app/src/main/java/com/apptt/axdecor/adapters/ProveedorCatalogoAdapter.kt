package com.apptt.axdecor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apptt.axdecor.R
import com.apptt.axdecor.databinding.ProveedorItemBinding
import com.apptt.axdecor.domain.ProveedorCatalogo

class ProveedorCatalogoAdapter(
    private val callback: (proveedor: ProveedorCatalogo) -> Unit
) : ListAdapter<ProveedorCatalogo, ProveedorCatalogoAdapter.ProviderViewHolder>(DiffCallback) {
    var proveedores: List<ProveedorCatalogo> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ProviderViewHolder(var binding: ProveedorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.proveedor_item
        }

    }

    override fun getItemCount() = proveedores.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProviderViewHolder {
        val withDataBinding: ProveedorItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ProviderViewHolder.LAYOUT,
            parent,
            false
        )
        return ProviderViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ProviderViewHolder, position: Int) {
        holder.binding.also { binding ->
            val proveedor = proveedores[position]
            binding.nombreProveedor = proveedor
            holder.itemView.setOnClickListener {
                callback(proveedor)
            }

            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ProveedorCatalogo>() {
        override fun areItemsTheSame(
            oldItem: ProveedorCatalogo,
            newItem: ProveedorCatalogo
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ProveedorCatalogo,
            newItem: ProveedorCatalogo
        ): Boolean {
            return oldItem.idProvider == newItem.idProvider
        }
    }
}