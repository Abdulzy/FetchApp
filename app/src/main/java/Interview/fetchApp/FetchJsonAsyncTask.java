package Interview.fetchApp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchJsonAsyncTask extends AsyncTask<Void, Void, List<Item>> {
    private final String jsonUrl;
    private final DataFetchedListener listener;

    public FetchJsonAsyncTask(String jsonUrl, DataFetchedListener listener) {
        this.jsonUrl = jsonUrl;
        this.listener = listener;
    }

    @Override
    protected List<Item> doInBackground(Void... voids) {
        try {
            URL url = new URL(jsonUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                bufferedReader.close();

                return parseJsonData(response.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Item> itemList) {
        if (listener != null) {
            listener.onDataFetched(itemList);
        }
    }

    private List<Item> parseJsonData(String jsonData) {
        List<Item> itemList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int listId = jsonObject.optInt("listId", -1);
                String name = jsonObject.optString("name", null);
                if (listId != -1 && name != null) {
                    itemList.add(new Item(listId, name));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return itemList;
    }

    public interface DataFetchedListener {
        void onDataFetched(List<Item> itemList);
    }
}
