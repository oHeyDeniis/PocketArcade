package com.oheydeniis.pocketarcade;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oheydeniis.pocketarcade.databinding.ItemRandomBinding;

import java.util.List;

public class RandomAdapter extends RecyclerView.Adapter<RandomAdapter.RandomizerViewHolder> {

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    private final List<ItemModel> itemList;
    private final OnItemLongClickListener longClickListener;

    public RandomAdapter(List<ItemModel> itemList, OnItemLongClickListener longClickListener) {
        this.itemList = itemList;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public RandomizerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRandomBinding binding = ItemRandomBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RandomizerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RandomizerViewHolder holder, int position) {
        ItemModel item = itemList.get(position);
        holder.binding.txtItemWord.setText(item.getWord());
        holder.binding.txtItemCategory.setText(item.getCategory());

        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onItemLongClick(holder.getAdapterPosition());
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class RandomizerViewHolder extends RecyclerView.ViewHolder {
        ItemRandomBinding binding;
        public RandomizerViewHolder(ItemRandomBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}