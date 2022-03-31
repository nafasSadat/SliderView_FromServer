package com.acpitzone.sliderview_fromserver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SliderView sliderView;
    List<Data_Model> slider_images;
    Slider_Adpater slider_adpater;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slider_images=new ArrayList<>();

        sliderView=findViewById(R.id.slider_view);
        getData_from_DB();
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();



    }

    private void getData_from_DB() {

        String uRL = "https://xcusemeteacher.com/ChartAnalysis/get_slider_images.php";

        StringRequest request = new StringRequest(Request.Method.POST, uRL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("error")) {
                    try {
                        JSONArray array = new JSONArray(response);

                        for (int i = 0; i < array.length(); i++)
                        {

                            JSONObject object = array.getJSONObject(i);

                            int id = object.getInt("id");
                            String image = object.getString("photo").trim();
                            String imgurl="https://xcusemeteacher.com/"+image;

                            Log.d("adv_image", imgurl);


                            Data_Model data = new Data_Model( imgurl);
                            slider_images.add(data);


                        }


                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this,response+""+e,Toast.LENGTH_LONG).show();
                        Log.d("ErrorH", response+""+e);
                    }

                    Log.d("sizeoflist", String.valueOf(slider_images.size()));
                    slider_adpater=new Slider_Adpater(MainActivity.this,slider_images);
                    sliderView.setSliderAdapter(slider_adpater);

                }else {
                    Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        BackgroundTask.getmInstance(MainActivity.this).addToRequestQueue(request);

    }





}