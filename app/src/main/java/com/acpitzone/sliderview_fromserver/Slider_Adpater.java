package com.acpitzone.sliderview_fromserver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;
import java.util.concurrent.locks.LockSupport;

public class Slider_Adpater extends SliderViewAdapter<Slider_Adpater.mySliderViewHolder>{

    Context context;
    List<Data_Model> image_list;

    public Slider_Adpater(Context context,List<Data_Model> images){
        this.context=context;
        this.image_list=images;
    }


    @Override
    public mySliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.slider_item,parent,false);
        return  new mySliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(mySliderViewHolder viewHolder, int position) {
        Data_Model data=image_list.get(position);

        Glide.with(context)
                .load(image_list.get(position).getImage_url())
                .apply(new RequestOptions().override(225, 225))
                .centerCrop()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.image_view);

    }

    @Override
    public int getCount() {
        return image_list.size();
    }

    public class mySliderViewHolder extends SliderViewAdapter.ViewHolder {

        ImageView image_view;
        RelativeLayout slidercontainer;

        public mySliderViewHolder(View itemView) {
            super(itemView);

            image_view=itemView.findViewById(R.id.image_view);
            //slidercontainer=itemView.findViewById(R.id.slider_container);
        }
    }
}
