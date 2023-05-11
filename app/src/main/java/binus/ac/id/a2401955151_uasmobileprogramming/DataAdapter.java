package binus.ac.id.a2401955151_uasmobileprogramming;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    private List<Data> dataList;
    private Context context;

    public DataAdapter(Context context, List<Data> datas){
        this.context = context;
        dataList = datas;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fruit, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        Data data = dataList.get(position);
        holder.id.setText("Fruit ID : " +data.getId());
        holder.name.setText("Fruit Name : " +data.getName());
        holder.genus.setText("Fruit Genus : " +data.getGenus());
        holder.family.setText("Fruit Family : " +data.getFamily());
        holder.order.setText("Fruit Order : " +data.getOrder());
        holder.carbohydrates.setText("Carbohydrates : " +data.getNutritions().getCarbohydrates());
        holder.protein.setText("Protein : " +data.getNutritions().getProtein());
        holder.fat.setText("Fat : " +data.getNutritions().getFat());
        holder.calories.setText("Calories : " +data.getNutritions().getCalories());
        holder.sugar.setText("Sugar : " +data.getNutritions().getSugar());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder{
        TextView id, name, genus, family, order, carbohydrates, protein, fat, calories, sugar;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.fruitID);
            name = itemView.findViewById(R.id.name);
            genus = itemView.findViewById(R.id.genus);
            family = itemView.findViewById(R.id.family);
            order = itemView.findViewById(R.id.order);
            carbohydrates = itemView.findViewById(R.id.carbohydrates);
            protein = itemView.findViewById(R.id.protein);
            fat = itemView.findViewById(R.id.fat);
            calories = itemView.findViewById(R.id.calories);
            sugar = itemView.findViewById(R.id.sugar);
        }
    }
}
