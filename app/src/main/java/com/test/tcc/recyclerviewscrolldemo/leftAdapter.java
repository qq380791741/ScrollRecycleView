package com.test.tcc.recyclerviewscrolldemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class leftAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<TestBean> testBeans;
    private String title;
    LeftOnlickListener leftOnlickListener;
    public leftAdapter(Context context, List<TestBean> testBeans,String title) {
        this.context = context;
        this.testBeans = testBeans;
        this.title = title;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LeftHolder(LayoutInflater.from(context).inflate(R.layout.item_left,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int pos) {
        LeftHolder holder = (LeftHolder) viewHolder;
        if (title.equals(testBeans.get(pos).getTitleName())) {
            holder.leftText.setText(testBeans.get(pos).getTitleName()+"变色");
        }else {
            holder.leftText.setText(testBeans.get(pos).getTitleName());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!testBeans.get(pos).equals(title)&&leftOnlickListener!=null){
                    leftOnlickListener.leftOnlickListener(testBeans.get(pos).getTitleName());
                }
            }
        });
    }


    public void setTitle(String title){
        this.title = title;
        notifyDataSetChanged();
    }
    public String getTitle(String title){
        return title;
    }

    public int getTitlePosition(){
        int position = 0;
        for (int i =0 ; i<testBeans.size() ;i++){
            if (title.equals(testBeans.get(i).getTitleName())){
                return position;
            }
            position++;
        }

        return 0;
    }

    interface LeftOnlickListener{
        void leftOnlickListener(String title);
    }

    public void setLeftOnclickListener(LeftOnlickListener leftOnclickListener){
        this.leftOnlickListener = leftOnclickListener;
    }


    @Override
    public int getItemCount() {
        if (testBeans!=null){
            return testBeans.size();
        }
        return 0;
    }

    class LeftHolder extends RecyclerView.ViewHolder{
        TextView leftText;
        public LeftHolder(@NonNull View itemView) {
            super(itemView);
            leftText = itemView.findViewById(R.id.left_text);
        }
    }
}
