package com.marionageh.bakingapp.Adapters;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marionageh.bakingapp.Module.Foods;
import com.marionageh.bakingapp.Module.Steps;
import com.marionageh.bakingapp.R;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {
    List<Steps> steps;
    private StepsAdapter.ListItemClick listItemClick;

    public StepsAdapter(List<Steps> steps, StepsAdapter.ListItemClick listItemClick) {
        this.steps = steps;
        this.listItemClick = listItemClick;
    }

    @NonNull
    @Override
    public StepsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_item, parent, false);
        StepsAdapter.ViewHolder viewHolder = new StepsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.ViewHolder holder, int position) {
        Log.e("Marop",steps.get(position).getShortDescription());
        holder.PutTextes(steps.get(position).getShortDescription(),position);
    }

    @Override
    public int getItemCount() {
        return steps == null ? 0 :
                steps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title_txt;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            title_txt = itemView.findViewById(R.id.view_list_item_text);
            imageView = itemView.findViewById(R.id.view_list_item_iamge_view);
            itemView.setOnClickListener(this);
        }

        public void PutTextes(String title,int i) {
            StringBuilder builder=new StringBuilder();
            builder.append(i);
            builder.append(".");
            builder.append(" ");
            builder.append(title);
            title_txt.setText(builder.toString());

        }

        @Override
        public void onClick(View v) {
            listItemClick.OnItemClick(getAdapterPosition());
        }
    }

    public void Swapadapter(List<Steps> stepsList) {
        if (stepsList != null) {

            steps = stepsList;
            notifyDataSetChanged();

        }
    }

    public interface ListItemClick {
        void OnItemClick(int Postion);
    }
}
