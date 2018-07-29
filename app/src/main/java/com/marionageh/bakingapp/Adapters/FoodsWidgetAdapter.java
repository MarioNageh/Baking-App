package com.marionageh.bakingapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.marionageh.bakingapp.Module.Foods;
import com.marionageh.bakingapp.R;

import java.util.List;

public class FoodsWidgetAdapter extends RecyclerView.Adapter<FoodsWidgetAdapter.ViewHolder> {
    List<Foods> foods;
    private FoodsWidgetAdapter.ListItemClick listItemClick;

    public FoodsWidgetAdapter(List<Foods> foods, FoodsWidgetAdapter.ListItemClick listItemClick) {
        this.foods = foods;
        this.listItemClick = listItemClick;
    }

    @NonNull
    @Override
    public FoodsWidgetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_items, parent, false);
        FoodsWidgetAdapter.ViewHolder viewHolder = new FoodsWidgetAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodsWidgetAdapter.ViewHolder holder, int position) {
        holder.PutTextes(foods.get(position).getName(), foods.get(position).getServings());
    }

    @Override
    public int getItemCount() {
        return foods == null ? 0 :
                foods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView title_txt, subject_txt;


        public ViewHolder(View itemView) {
            super(itemView);
            title_txt = itemView.findViewById(R.id.list_item_title_text);
            subject_txt = itemView.findViewById(R.id.list_item_subject_text);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        public void PutTextes(String title, int subject) {
            title_txt.setText(title);
            subject_txt.setText("Servings: " + subject);
        }

        @Override
        public void onClick(View v) {
            listItemClick.OnItemClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            listItemClick.OnlongClick(getAdapterPosition());
            return false;
        }
    }

    public void Swapadapter(List<Foods> foodsList) {
        if (foodsList != null) {
            foods = foodsList;
            notifyDataSetChanged();

        }
    }

    public interface ListItemClick {
        void OnItemClick(int Postion);
        void OnlongClick(int Postion);
    }

}