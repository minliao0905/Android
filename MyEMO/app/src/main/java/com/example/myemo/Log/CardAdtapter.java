package com.example.myemo.Log;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myemo.Log.view.Emocard;
import com.example.myemo.R;
import com.example.myemo.ui.main.LogActivity;

public class CardAdtapter  extends RecyclerView.Adapter<CardAdtapter.VH>{

    private int count=13;
    public MyItemClickListener mItemClickListener;
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loglist_item,parent,false);
        View view = new Button(parent.getContext());
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdtapter.VH holder, int position) {
//      holder.itemView.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//              Log.v("hodelr","当前点击 "+holder.getAdapterPosition());
//          }
//      });
    }


    @Override
    public int getItemCount() {
        return count;
    }

    public  class VH extends RecyclerView.ViewHolder{
        public final Button btn;
        public VH(View v) {
            super(v);
            btn = (Button) v;
        }
    }

    // recycleview  item的 监听
    /**
     * 创建一个回调接口
     */
    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * 在activity里面adapter就是调用的这个方法,将点击事件监听传递过来,并赋值给全局的监听
     *
     * @param myItemClickListener
     */
    public void setItemClickListener(MyItemClickListener myItemClickListener) {
        this.mItemClickListener = myItemClickListener;
    }

}

