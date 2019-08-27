package com.example.demo.foody.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.foody.R;
import com.example.demo.foody.model.ThucDon;

import java.util.List;

public class ThucDonAdapter extends RecyclerView.Adapter<ThucDonAdapter.ViewHolderThucDon> {

    Context context;
    List<ThucDon> thucDonList;

    public ThucDonAdapter(Context context, List<ThucDon> thucDonList) {
        this.context = context;
        this.thucDonList = thucDonList;
    }

    @NonNull
    @Override
    public ViewHolderThucDon onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thuc_don, parent, false);
        return new ViewHolderThucDon(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderThucDon holder, int position) {
        ThucDon thucDon = thucDonList.get(position);
        holder.txtThucDon.setText(thucDon.getTenthucdon());
        Log.d("kiemtraTD", thucDon.getMathucdon());
        holder.recyclerViewMonAn.setLayoutManager(new LinearLayoutManager(context));
        MonAnAdapter adapterMonAn = new MonAnAdapter(context, thucDon.getMonAnList());
        Log.d("kiemtrTDMA", thucDon.getMonAnList() + "");
        holder.recyclerViewMonAn.setAdapter(adapterMonAn);
        adapterMonAn.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return thucDonList.size();
    }

    public class ViewHolderThucDon extends RecyclerView.ViewHolder {
        TextView txtThucDon;
        RecyclerView recyclerViewMonAn;

        public ViewHolderThucDon(@NonNull View itemView) {
            super(itemView);

            txtThucDon = itemView.findViewById(R.id.txtTenThucDon);
            recyclerViewMonAn = itemView.findViewById(R.id.recyclerMonAn);
        }
    }
}
