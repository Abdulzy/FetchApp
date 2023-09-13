package Interview.fetchApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Adapter for binding data to the RecyclerView and filtering/sorting the data.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context context;
    private List<Item> itemList;

    /**
     * Constructs an ItemAdapter.
     *
     * @param context  The context of the application.
     * @param itemList The list of items to be displayed.
     */
    public ItemAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
        filterAndSortData();
    }
    
    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.nameTextView.setText(item.getName() );
        String listI = "" + item.getListId();
        holder.listIdTextView.setText(listI );
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView listIdTextView;

        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.listName);
            listIdTextView = itemView.findViewById(R.id.listId);
        }
    }

    /**
     * Filters out items with blank or null names and sorts the data.
     */
    private void filterAndSortData() {
        // Filter out items with blank or null names
        itemList.removeIf(item -> item.getName() == null || item.getName().trim().isEmpty());

        // Sort the items by listId and then by name
        Collections.sort(itemList, new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2) {
                int listIdComparison = Integer.compare(item1.getListId(), item2.getListId());
                if (listIdComparison != 0) {
                    return listIdComparison;
                }
                return Integer.compare(item1.getId(), item2.getId());
            }
        });
    }
}
