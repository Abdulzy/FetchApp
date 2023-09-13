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

/**
 * AsyncTask for making an HTTP GET request and fetching data from a JSON URL.
 */
public class FetchJsonAsyncTask extends AsyncTask<Void, Void, List<Item>> {
    private final String jsonUrl;
    private final DataFetchedListener listener;

    /**
     * Constructs a FetchJsonAsyncTask.
     *
     * @param jsonUrl  The URL of the JSON data.
     * @param listener The listener for data fetched events.
     */
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

    /**
     * @param jsonData data that was read from JSON link
     * @return List of all items in json
     */
    private List<Item> parseJsonData(String jsonData) {
        List<Item> itemList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int listId = jsonObject.optInt("listId",-1);
                int id = jsonObject.optInt("id",-1);
                String name = jsonObject.optString("name",null);
                if (listId != -1 && name != null && !name.equals("null") && !name.trim().isEmpty() ) {
                    itemList.add(new Item(listId,id, name));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return itemList;
    }

    /**
     * Listener interface for data fetched events.
     */
    public interface DataFetchedListener {
        /**
         * Called when data has been fetched and processed.
         *
         * @param itemList The list of fetched items.
         */
        void onDataFetched(List<Item> itemList);
    }
}
