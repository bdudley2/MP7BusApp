package edu.illinois.cs.cs125.lab12;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Main class for our UI design lab.
 */
public final class MainActivity extends AppCompatActivity {
    /** Default logging tag for messages from the main activity. */
    private static final String TAG = "Lab12:Main";

    /** Request queue for our API requests. */
    private static RequestQueue requestQueue;

    /**
     * Run when this activity comes to the foreground.
     *
     * @param savedInstanceState unused
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up the queue for our API requests
        requestQueue = Volley.newRequestQueue(this);

        setContentView(R.layout.activity_main);

        final Button getDestination = findViewById(R.id.getDestination);
        getDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View w) {
                Log.d(TAG, "getDestination button clicked");
                startGetNewsCall();
            }
        });

//        final TextView textView = findViewById(R.id.textView);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View w) {
//                startGetNewsCall();
//            }
//        });

    }

    /*protected void finishProcessWeather(final String jsonResult) {
        TextView textView = findViewById(R.id.textView);
        textView.setText(response.toString());
    }*/

    /**
     * Run when this activity is no longer visible.
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Make a call to the weather API.
     */
    void startGetNewsCall() {
        try {
            //String stopID = "PKLN:1";
            String url = "https://developer.cumtd.com/api/v2.2/json/getnews"
                    + "?key=" + "790554d3895c41299cfe47ec9e9659a4";
            //+ "?stop_id=" + stopID;
            Log.d(TAG, url);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try {

//                                Log.d(TAG, response.toString(2));
                                JSONObject news = response.getJSONArray("news").getJSONObject(0);
                                String title = news.get("title").toString();
                                String news_body = news.get("body").toString();
                                Log.d(TAG, news_body);
                                Log.d(TAG, response.get("time").toString());
                                TextView textView = findViewById(R.id.textView);
                                textView.setText(news_body);

                            } catch (JSONException ignored) { Log.e(TAG, ignored.toString());}
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {

                    Log.e(TAG, error.toString());
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //    /**
//     * Make a call to the Lat Lon API.
//     */
//    void startGetLocationCall() {
//        try {
//            //String stopID = "PKLN:1";
//            String url = "https://www.googleapis.com/geolocation/v1/geolocate"
//                    + "?key=" + "AIzaSyAUhduQ2NSTUK3vdIKZBHA83JHl9T4UIyk";
//            //+ "?stop_id=" + stopID;
//            Log.d(TAG, url);
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                    Request.Method.GET,
//                    url,
//                    null,
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(final JSONObject response) {
//                            try {
//                                Log.d(TAG, response.toString(2));
//                                JSONObject location = response.getJSONObject("location");
//                                String lat = location.getJSONObject("lat").toString();
//                                String lng = location.getJSONObject("lng").toString();
//                                //Log.d(TAG, response.getJSONObject("time").toString());
//                                //TextView textView = findViewById(R.id.textView);
//                                //textView.setText();
//                            } catch (JSONException ignored) { }
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(final VolleyError error) {
//                            Log.e(TAG, error.toString());
//                        }
//                    });
//            requestQueue.add(jsonObjectRequest);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    /**
//     * Make a call to the Lat Lon API.
//     */
//    void startCurrentLocationCall() {
//        try {
//            //String stopID = "PKLN:1";
//            String url = "https://www.googleapis.com/geolocation/v1/geolocate"
//                    + "?key=" + "AIzaSyAUhduQ2NSTUK3vdIKZBHA83JHl9T4UIyk";
//            //+ "?stop_id=" + stopID;
//            Log.d(TAG, url);
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                    Request.Method.GET,
//                    url,
//                    null,
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(final JSONObject response) {
//                            try {
//                                Log.d(TAG, response.toString(2));
//                                JSONObject location = response.getJSONObject("location");
//                                String lat = location.getJSONObject("lat").toString();
//                                String lng = location.getJSONObject("lng").toString();
//                                //Log.d(TAG, response.getJSONObject("time").toString());
//                                //TextView textView = findViewById(R.id.textView);
//                                //textView.setText();
//                            } catch (JSONException ignored) { }
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(final VolleyError error) {
//                            Log.e(TAG, error.toString());
//                        }
//                    });
//            requestQueue.add(jsonObjectRequest);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
