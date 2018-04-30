package edu.illinois.cs.cs125.lab12;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.text.method.ScrollingMovementMethod;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.SimpleFormatter;


/**
 * Main class for our UI design lab.
 */
public final class MainActivity extends AppCompatActivity {
    /** Default logging tag for messages from the main activity. */
    private static final String TAG = "Lab12:Main";

    private String GOOG_KEY = "AIzaSyAUhduQ2NSTUK3vdIKZBHA83JHl9T4UIyk";
    private String API_KEY = "10599ca8753240b88d657156fb02d4d0";

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
                TextView textView = findViewById(R.id.textView);
                textView.setText("");
                TextView textView2 = findViewById(R.id.textView2);
                textView2.setText("");
                TextView textView3 = findViewById(R.id.textView3);
                textView3.setText("");
            }
        });

        final Button currentLocation = findViewById(R.id.currentLocation);
        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View w) {
                Log.d(TAG, "currentLocation button clicked");
                startGetLocationCall();
                TextView textView = findViewById(R.id.textView);
                textView.setText("");
                TextView textView2 = findViewById(R.id.textView2);
                textView2.setText("");
                TextView textView3 = findViewById(R.id.textView3);
                textView3.setText("");
            }
        });

        final Button leaveNow = findViewById(R.id.leaveNow);
        leaveNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View w) {
                Log.d(TAG, "leaveNow button clicked");
                startGetNowCall();
                TextView textView = findViewById(R.id.textView);
                textView.setText("");
                TextView textView2 = findViewById(R.id.textView2);
                textView2.setText("");
                TextView textView3 = findViewById(R.id.textView3);
                textView3.setText("");
            }
        });
    }

    /**
     * Run when this activity is no longer visible.
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Make a call to the news API.
     */
    void startGetNewsCall() {
        try {
            //String stopID = "PKLN:1";
            String url = "https://developer.cumtd.com/api/v2.2/json/getnews"
                    + "?key=" + API_KEY;
            //+ "?stop_id=" + stopID;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try {
                                JSONObject news = response.getJSONArray("news").getJSONObject(0);

                                String title = news.get("title").toString();
                                String news_body = news.get("body").toString();
                                TextView textView = findViewById(R.id.textView);
                                textView.setText(title);
                                TextView textView2 = findViewById(R.id.textView2);
                                textView.setMovementMethod(new ScrollingMovementMethod());
                                textView2.setText(news_body);
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
    /**
     * Make a call to the Current Location API.
     */
    void startGetLocationCall() {
        try {
            //String stopID = "PKLN:1";
            String url = "https://www.googleapis.com/geolocation/v1/geolocate"
                    + "?key=" + GOOG_KEY;
            //+ "?stop_id=" + stopID;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try {
                                JSONObject location = response.getJSONObject("location");
                                String lat = location.get("lat").toString();
                                String lng = location.get("lng").toString();




                                String url = "https://developer.cumtd.com/api/v2.2/json/getstopsbylatlon"
                                        + "?lat=" + "40.1125" + "&lon=" + "-88.2269" + "&key=" + API_KEY;
//                                        + "?lat=" + lat + "&lon=" + lng + "&key=" + API_KEY;
                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                        Request.Method.GET,
                                        url,
                                        null,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(final JSONObject response) {
                                                try {
                                                    JSONObject stops = response.getJSONArray("stops").getJSONObject(0);
                                                    String stop_name = stops.get("stop_name").toString();
                                                    String stop_id = stops.get("stop_id").toString();
                                                    //Log.d(TAG, response.getJSONObject("time").toString());
                                                    TextView textView = findViewById(R.id.textView);
                                                    textView.setText("Nearest stop to your location:\t\t" + stop_name + "\n");


                                                    String url = "https://developer.cumtd.com/api/v2.2/json/getstoptimesbystop"
                                                            + "?stop_id=" + stop_id + "&key=" + API_KEY;
                                                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                                            Request.Method.GET,
                                                            url,
                                                            null,
                                                            new Response.Listener<JSONObject>() {
                                                                @Override
                                                                public void onResponse(final JSONObject response) {
                                                                    try {
                                                                        JSONArray stop_times = response.getJSONArray("stop_times");
                                                                        String _cached = "";
                                                                        for (int i = 0; i < stop_times.length(); i++) {
                                                                            JSONObject stop_time = stop_times.getJSONObject(i);
                                                                            String route_id = stop_time.getJSONObject("trip").get("route_id").toString();
                                                                            String arrive_time = stop_time.get("arrival_time").toString();
                                                                            TextView textView2 = findViewById(R.id.textView2);
                                                                            textView2.setMovementMethod(new ScrollingMovementMethod());
//                                                                            TextView textView3 = findViewById(R.id.textView3);
//                                                                            textView3.setMovementMethod(new ScrollingMovementMethod());
                                                                            if (route_id.equals(_cached)) continue;
                                                                            textView2.append("Bus: " + route_id + "\t\t" + "Arrive time: " + arrive_time + "\n");
//                                                                            textView3.append("Arrive time: " + arrive_time + "\n");
                                                                            _cached = route_id;
                                                                        }
                                                                    } catch (JSONException ignored) { Log.e(TAG, ignored.toString()); }
                                                                }
                                                            },
                                                            new Response.ErrorListener() {
                                                                @Override
                                                                public void onErrorResponse(final VolleyError error) {
                                                                    Log.e(TAG, error.toString());
                                                                }
                                                            });
                                                    requestQueue.add(jsonObjectRequest);






                                                } catch (JSONException ignored) { Log.e(TAG, ignored.toString()); }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(final VolleyError error) {
                                                Log.e(TAG, error.toString());
                                            }
                                        });
                                requestQueue.add(jsonObjectRequest);


                            } catch (JSONException ignored) { Log.e(TAG, ignored.toString());}
                        }
                    },
                    new Response.ErrorListener() {
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


    /**
     * Make a call to the Leave Now API.
     */
    void startGetNowCall() {
        try {
            //String stopID = "PKLN:1";
            String url = "https://www.googleapis.com/geolocation/v1/geolocate"
                    + "?key=" + GOOG_KEY;
            //+ "?stop_id=" + stopID;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try {
                                JSONObject location = response.getJSONObject("location");
                                String lat = location.get("lat").toString();
                                String lng = location.get("lng").toString();
                                String url = "https://developer.cumtd.com/api/v2.2/json/getstopsbylatlon"
                                        + "?lat=" + "40.1125" + "&lon=" + "-88.2269" + "&key=" + API_KEY;
//                                        + "?lat=" + lat + "&lon=" + lng + "&key=" + API_KEY;
                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                        Request.Method.GET,
                                        url,
                                        null,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(final JSONObject response) {
                                                try {
                                                    JSONArray stops = response.getJSONArray("stops");
                                                    TextView textView = findViewById(R.id.textView);
                                                    // "time":"2012-01-24T16:18:44-06:00",
                                                    String current_time = response.get("time").toString();
                                                    String[] curr_time_arr = current_time.split("T")[1].split("-")[0].split(":");
                                                    int current_time_int = Integer.parseInt(curr_time_arr[0] + curr_time_arr[1] + curr_time_arr[2]);
                                                    textView.setText("Current Time:\t\t" + current_time + "\n" + "Top 10 nearest stops:");
                                                    final int cur_time_int = current_time_int;
                                                    for (int i = 0; i < 10; i++) {
                                                        JSONObject stop = stops.getJSONObject(i);
                                                        String stop_name = stop.get("stop_name").toString();
                                                        String stop_id = stop.get("stop_id").toString();
                                                        TextView textView2 = findViewById(R.id.textView2);
                                                        textView2.setMovementMethod(new ScrollingMovementMethod());
                                                        textView2.append(stop_name + "\n\n");
                                                        String url = "https://developer.cumtd.com/api/v2.2/json/getstoptimesbystop"
                                                                + "?stop_id=" + stop_id + "&key=" + API_KEY;
                                                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                                                Request.Method.GET,
                                                                url,
                                                                null,
                                                                new Response.Listener<JSONObject>() {
                                                                    @Override
                                                                    public void onResponse(final JSONObject response) {
                                                                        try {
                                                                            JSONArray stop_times = response.getJSONArray("stop_times");
                                                                            for (int j = 0; j < stop_times.length(); j++) {
                                                                                JSONObject stop_time = stop_times.getJSONObject(j);
                                                                                String route_id = stop_time.getJSONObject("trip").get("route_id").toString();
                                                                                String arrive_time = stop_time.get("arrival_time").toString();
                                                                                String[] arrive_time_arr = arrive_time.trim().split(":");
                                                                                arrive_time_arr[0] = Integer.toString(Integer.parseInt(arrive_time_arr[0]) % 24);
                                                                                int sch_time_int = Integer.parseInt( arrive_time_arr[0] + arrive_time_arr[1] + arrive_time_arr[2]);
                                                                                if (sch_time_int < cur_time_int) {
                                                                                    continue;
                                                                                }
                                                                                TextView textView3 = findViewById(R.id.textView3);
                                                                                textView3.setMovementMethod(new ScrollingMovementMethod());
                                                                                textView3.append(route_id + "\t\t" + arrive_time + "\n\n");
                                                                                break;
                                                                            }
                                                                        } catch (JSONException ignored) {
                                                                            Log.e(TAG, ignored.toString());
                                                                        }
                                                                    }
                                                                },
                                                                new Response.ErrorListener() {
                                                                    @Override
                                                                    public void onErrorResponse(final VolleyError error) {
                                                                        Log.e(TAG, error.toString());
                                                                    }
                                                                });
                                                        requestQueue.add(jsonObjectRequest);
                                                    }
                                                } catch (JSONException ignored) { Log.e(TAG, ignored.toString()); }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(final VolleyError error) {
                                                Log.e(TAG, error.toString());
                                            }
                                        });
                                requestQueue.add(jsonObjectRequest);

                            } catch (JSONException ignored) { Log.e(TAG, ignored.toString());}
                        }
                    },
                    new Response.ErrorListener() {
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
}
