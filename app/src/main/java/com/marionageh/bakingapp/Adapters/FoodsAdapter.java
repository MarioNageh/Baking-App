package com.marionageh.bakingapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marionageh.bakingapp.Module.Foods;
import com.marionageh.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

public class FoodsAdapter extends RecyclerView.Adapter<FoodsAdapter.ViewHolder> {
    List<Foods> foods;
    private ListItemClick listItemClick;

    public FoodsAdapter(List<Foods> foods, ListItemClick listItemClick) {
        this.foods = foods;
        this.listItemClick = listItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.PutTextes(foods.get(position).getName(), foods.get(position).getServings());
    }

    @Override
    public int getItemCount() {
        return foods == null ? 0 :
                foods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title_txt, subject_txt;

        public ViewHolder(View itemView) {
            super(itemView);
            title_txt = itemView.findViewById(R.id.list_item_title_text);
            subject_txt = itemView.findViewById(R.id.list_item_subject_text);
            itemView.setOnClickListener(this);
        }

        public void PutTextes(String title, int subject) {
            title_txt.setText(title);
            subject_txt.setText("Servings: " + subject);
        }

        @Override
        public void onClick(View v) {
            listItemClick.OnItemClick(getAdapterPosition());
        }
    }

    public void Swapadapter(List<Foods> foodsList) {
        if (foodsList != null) {
            //   Log.e("mario",String.valueOf(foods==null));
            foods = foodsList;
            notifyDataSetChanged();

        }
    }

    public interface ListItemClick {
        void OnItemClick(int Postion);
    }
}
