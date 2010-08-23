package com.example.android.recipe023;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Recipe023 extends Activity {

    private static final String TAG = "Recipe023";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        String json = "";
        json += "{\"recipe\": {";
        json += "  \"people\": [";
        json += "    {\"id\": \"1\", \"name\": \"gabu\"},";
        json += "    {\"id\": \"2\", \"name\": \"hoge\"},";
        json += "    {\"id\": \"3\", \"name\": \"fuga\"}";
        json += "]}}";

        try {
            JSONObject jObject = new JSONObject(json);
            JSONObject recipe = jObject.getJSONObject("recipe");
            JSONArray people = recipe.getJSONArray("people");
            for (int i=0; i<people.length(); i++) {
                JSONObject person = people.getJSONObject(i);
                int id = person.getInt("id");
                String name = person.getString("name");
                Log.d(TAG, "id=" + id + ", name=" + name);
            }
        } catch (JSONException e) {
            // パースエラーなど
            e.printStackTrace();
        }
    }
}