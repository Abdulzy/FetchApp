package Interview.fetchApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity implements FetchJsonAsyncTask.DataFetchedListener {
    private RecyclerView recyclerView;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String jsonUrl = "https://fetch-hiring.s3.amazonaws.com/hiring.json";
        new FetchJsonAsyncTask(jsonUrl, this).execute();
    }

    @Override
    public void onDataFetched(List<Item> itemList) {
        adapter = new ItemAdapter(this, itemList);
        recyclerView.setAdapter(adapter);
    }
}