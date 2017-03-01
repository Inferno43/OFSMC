package com.ofs.ofmc.home;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ofs.ofmc.R;
import com.ofs.ofmc.model.Employee;
import com.ofs.ofmc.model.Holidays;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by saravana.subramanian on 2/24/17.
 */

public class HolidayAdapter  extends RecyclerView.Adapter<HolidayAdapter.ViewHolder> {

    private ArrayList<Holidays> holidays;
    private Context context;

    StorageReference storageReference;
    public HolidayAdapter(Context context, ArrayList<Holidays> holidays) {
        this.holidays = holidays;
        this.context = context;
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public CardView mItemContainer;
        public TextView date;
        public TextView day;
        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);

            mItemContainer = (CardView) itemView.findViewById(R.id.item_container);
            date = (TextView) itemView.findViewById(R.id.date);
            day = (TextView) itemView.findViewById(R.id.day);
            description = (TextView) itemView.findViewById(R.id.description);
        }
    }

    @Override
    public HolidayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_holiday, parent, false);
        return new HolidayAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final HolidayAdapter.ViewHolder holder, final int position) {

        if(null != holidays){
            holder.date.setText(holidays.get(position).getHolidayDate());
            holder.day.setText(holidays.get(position).getHolidayDay());

            holder.description.setText(holidays.get(position).isTentative()?
                    holidays.get(position).getHolidayDescription()+"*":holidays.get(position).getHolidayDescription());

        }else{
            //
        }


    }

    @Override
    public int getItemCount() {
        if(null != holidays)
            return holidays.size();
        else
            return 0;

    }
}
