package com.marionageh.bakingapp.Adapters;

import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marionageh.bakingapp.Module.Ingredients;
import com.marionageh.bakingapp.Module.Steps;
import com.marionageh.bakingapp.R;

import java.lang.reflect.Type;
import java.util.List;

public class IngridentsAdapter extends RecyclerView.Adapter<IngridentsAdapter.ViewHolder> {
    int counter = 0;
    List<Ingredients> ingredients;
    private IngridentsAdapter.ListItemClickIngrients listItemClick;

    public IngridentsAdapter(List<Ingredients> ingredients, IngridentsAdapter.ListItemClickIngrients listItemClick) {
        this.ingredients = ingredients;
        this.listItemClick = listItemClick;
    }

    @NonNull
    @Override
    public IngridentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingridents_list_item, parent, false);
        IngridentsAdapter.ViewHolder viewHolder = new IngridentsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngridentsAdapter.ViewHolder holder, int position) {
        //For Text First TextView
        if (position == 0)
            return;
        holder.PutIngrients(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredients == null ? 0 :
                ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title_txt;


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        public ViewHolder(View itemView) {
            super(itemView);
            title_txt = itemView.findViewById(R.id.txt_ingrident);
            //For Style First text View
            if (counter == 0) {
                title_txt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                title_txt.setTypeface(null, Typeface.BOLD);
                title_txt.setText("Ingridients");
                counter++;
            }
            itemView.setOnClickListener(this);
        }

        public void PutIngrients(Ingredients ingredient) {
            // For Text
            StringBuilder builder = new StringBuilder();
            builder.append("-");
            builder.append(" ");
            builder.append(ingredient.getIngredient());
            builder.append(" ");
            builder.append("(");
            //   builder.append(" ");
            builder.append(ingredient.getQuantity());
            builder.append(" ");
            builder.append(ingredient.getMeasure());
            builder.append(")");


            title_txt.setText(builder.toString());

        }

        @Override
        public void onClick(View v) {
            listItemClick.OnItemClickIN(getAdapterPosition());
        }
    }

    public void Swapadapter(List<Ingredients> IngredientsList) {
        if (IngredientsList != null) {

            ingredients = IngredientsList;
            notifyDataSetChanged();

        }
    }

    public interface ListItemClickIngrients {
        void OnItemClickIN(int Postion);
    }
}
