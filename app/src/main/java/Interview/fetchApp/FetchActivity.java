package Interview.fetchApp;

import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Interview.fetchApp.databinding.ActivityFetchBinding;

public class FetchActivity extends AppCompatActivity implements FetchJsonAsyncTask.DataFetchedListener{
    private RecyclerView recyclerView;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fetch);

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