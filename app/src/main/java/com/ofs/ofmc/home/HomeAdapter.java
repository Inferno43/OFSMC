package com.ofs.ofmc.home;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ofs.ofmc.R;
import com.ofs.ofmc.model.Employee;

import java.util.ArrayList;

/**
 * Created by saravana.subramanian on 2/22/17.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private ArrayList<Employee> employees;
    private HomeView.ClickListener clickListener;

    public HomeAdapter(ArrayList<Employee> employees, HomeView.ClickListener clickListener) {
        this.employees = employees;
        this.clickListener = clickListener;
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
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_profile, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HomeAdapter.ViewHolder holder, final int position) {

        if(null != employees){
            holder.empName.setText(employees.get(position).getEmployeeName());
            holder.empId.setText(employees.get(position).getEmployeeId());
            holder.empEmail.setText(employees.get(position).getEmployeeEmail());
            holder.empDepartment.setText(employees.get(position).getEmployeeDepartment());
            holder.empLocation.setText(employees.get(position).getEmployeePhase());
            holder.empExtension.setText(employees.get(position).getEmployeeExtension());
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
