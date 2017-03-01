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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ofs.ofmc.R;
import com.ofs.ofmc.model.DashboardModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by saravana.subramanian on 2/28/17.
 */

public class PagerAdapter extends BaseAdapter {

    private ArrayList<DashboardModel> dashboardModels;
    private Context context;
    ViewHolder holder = null;

    StorageReference storageReference;

    public PagerAdapter(Context context, ArrayList<DashboardModel> dashboardModels) {
        this.dashboardModels = dashboardModels;
        this.context = context;
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public class ViewHolder  {

        CardView mItemContainer;
        ImageView imageView;
        TextView title;
        TextView description;

        public ViewHolder(View itemView) {
            mItemContainer = (CardView) itemView.findViewById(R.id.item_container);
            title = (TextView) itemView.findViewById(R.id.title);
            imageView =(ImageView)itemView.findViewById(R.id.imgView);
            description = (TextView)itemView.findViewById(R.id.description);
        }
    }

    @Override
    public int getCount() {
        return dashboardModels.size();
    }

    @Override
    public Object getItem(int position) {
        return dashboardModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_view_stub, parent, false);
             holder = new ViewHolder(convertView);
        }

        if (null != dashboardModels) {
            holder.title.setText(dashboardModels.get(position).getTitle());
            storageReference.child("images/Mau4n7aCLtTfK9Ly2vWG9S1KBcD2"+".jpg")
                    .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.with(context).load(uri.toString())
                            .placeholder(R.mipmap.ic_launcher)
                            .resize(100, 100).into(holder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            Animation fadeOut = new AlphaAnimation(0, 1);
                            fadeOut.setInterpolator(new AccelerateInterpolator());
                            fadeOut.setDuration(500);
                            holder.imageView.startAnimation(fadeOut);

                        }

                        @Override
                        public void onError() {

                        }
                    });
                }});
            holder.description.setText(dashboardModels.get(position).getDescription()) ;

        } else {
            //
        }

        return convertView;
    }

}
