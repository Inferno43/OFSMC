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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by saravana.subramanian on 2/22/17.
 */

public class DirectoryAdapter extends RecyclerView.Adapter<DirectoryAdapter.ViewHolder> {

    private ArrayList<Employee> employees;
    private DirectoryView.ClickListener clickListener;
    private Context context;

    StorageReference storageReference;
    public DirectoryAdapter(Context context, ArrayList<Employee> employees, DirectoryView.ClickListener clickListener) {
        this.employees = employees;
        this.context = context;
        this.clickListener = clickListener;
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public CardView mItemContainer;
        public TextView empName;
        public TextView empId;
        public TextView empEmail;
        public TextView empDepartment;
        public TextView empLocation;
        public TextView empExtension;
        public ImageView empImage;

        public ViewHolder(View itemView) {
            super(itemView);

            mItemContainer = (CardView) itemView.findViewById(R.id.item_container);
            empName = (TextView) itemView.findViewById(R.id.emp_Name);
            empId = (TextView) itemView.findViewById(R.id.emp_Id);
            empEmail = (TextView) itemView.findViewById(R.id.emp_Email);
            empDepartment = (TextView) itemView.findViewById(R.id.emp_Department);
            empLocation = (TextView) itemView.findViewById(R.id.emp_Location);
            empExtension = (TextView) itemView.findViewById(R.id.emp_Extension);
            empImage = (ImageView) itemView.findViewById(R.id.emp_Image);
        }
    }

    @Override
    public DirectoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_profile, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final DirectoryAdapter.ViewHolder holder, final int position) {

        if(null != employees){
            holder.empName.setText(employees.get(position).getEmployeeName());
            holder.empId.setText(employees.get(position).getEmployeeId());
            holder.empEmail.setText(employees.get(position).getEmployeeEmail());
            holder.empDepartment.setText(employees.get(position).getEmployeeDepartment());
            holder.empLocation.setText(employees.get(position).getEmployeePhase());
            holder.empExtension.setText(employees.get(position).getEmployeeExtension());
            String sx = employees.get(position).getEmployeeImage()+employees.get(position).getUserId();
            storageReference.child(employees.get(position).getEmployeeImage()+employees.get(position).getUserId()+".jpg")
                    .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.with(context).load(uri.toString())
                            .placeholder(R.mipmap.ic_launcher)
                            .resize(100,100).into(holder.empImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            Animation fadeOut = new AlphaAnimation(0, 1);
                            fadeOut.setInterpolator(new AccelerateInterpolator());
                            fadeOut.setDuration(500);
                            holder.empImage.startAnimation(fadeOut);

                        }

                        @Override
                        public void onError() {

                        }
                    });
                }
            });

            holder.mItemContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClicked(employees.get(position).getUserId());
                }
            });
        }else{
            //
        }


    }

    @Override
    public int getItemCount() {
        if(null != employees)
            return employees.size();
        else
            return 0;

    }
}
