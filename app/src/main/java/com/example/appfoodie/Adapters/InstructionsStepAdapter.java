package com.example.appfoodie.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfoodie.Models.Step;
import com.example.appfoodie.R;

import java.util.List;



public class InstructionsStepAdapter extends  RecyclerView.Adapter<InstructionsStepViewHolder> {

    Context context;
    List<Step> list;

    public InstructionsStepAdapter(Context context, List<Step> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InstructionsStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionsStepViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instructions_steps, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsStepViewHolder holder, int position) {

        holder.textView_instructions_steps_number.setText(String.valueOf(list.get(position).number));
        holder.textView_instructions_steps_title.setText(list.get(position).step);

        holder.recycler_instructions_ingredients.setHasFixedSize(true);
        holder.recycler_instructions_ingredients.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        InstructionsIngredientsAdapter instructionsIngredientsAdapter = new InstructionsIngredientsAdapter(context, list.get(position).ingredients);
        holder.recycler_instructions_ingredients.setAdapter(instructionsIngredientsAdapter);
        holder.recycler_instructions_equipments.setHasFixedSize(true);
        holder.recycler_instructions_equipments.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        InstructionsEquipmentsAdapter instructionsEquipmentsAdapter = new InstructionsEquipmentsAdapter(context, list.get(position).equipment);
        holder.recycler_instructions_equipments.setAdapter(instructionsEquipmentsAdapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
class InstructionsStepViewHolder extends RecyclerView.ViewHolder {

    TextView textView_instructions_steps_number,textView_instructions_steps_title;
    RecyclerView recycler_instructions_equipments,recycler_instructions_ingredients;

    public InstructionsStepViewHolder(@NonNull View itemView) {
        super(itemView);

        recycler_instructions_equipments = itemView.findViewById(R.id.recycler_instructions_equipments);
        recycler_instructions_ingredients = itemView.findViewById(R.id.recycler_instructions_ingredients);
        textView_instructions_steps_number = itemView.findViewById(R.id.textView_instructions_steps_number);
        textView_instructions_steps_title = itemView.findViewById(R.id.textView_instructions_steps_title);

    }
}