package com.test.tcc.recyclerviewscrolldemo;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

public class RightAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<TestBean> testBeans;
    private int TITLE = 0;
    private int ITEM = 1;
    private Map<Integer, Boolean> checkMap = new HashMap<>();

    public RightAdapter(Context context, List<TestBean> testBeans) {
        this.context = context;
        this.testBeans = testBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RightHolder(LayoutInflater.from(context).inflate(R.layout.item_right, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int pos) {
        final RightHolder holder = (RightHolder) viewHolder;
        int itemViewType = getItemViewType(pos);
        if (itemViewType == ITEM) {

            holder.rightText.setText(getText(pos));

            if (checkMap.get(pos) == null || !checkMap.get(pos)) {
                holder.checkBox.setChecked(false);
            } else if (checkMap.get(pos)) {
                holder.checkBox.setChecked(true);
            }


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkMap.get(pos) == null || !checkMap.get(pos)) {
                        checkMap.put(pos, true);
                        holder.checkBox.setChecked(true);
                    } else if (checkMap.get(pos)) {
                        checkMap.put(pos, false);
                        holder.checkBox.setChecked(false);
                    }
                }
            });
        } else if (itemViewType == TITLE) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFC0202"));
            holder.rightText.setText(" 这是标题 :" + getText(pos));
            holder.checkBox.setVisibility(View.GONE);
            holder.checkBox.setEnabled(false);
        }

    }


    //获取内容
    public String getText(int pos) {

        int count = 0;
        for (int i = 0; i < testBeans.size(); i++) {
            if (pos == count) {
                return testBeans.get(i).getTitleName();
            }
            count++;
            for (int j = 0; j < testBeans.get(i).getChildData().size(); j++) {
                if (pos == count) {
                    return testBeans.get(i).getChildData().get(j);
                }
                count++;
            }
        }

        return "无";

    }

    //获取所有选中的item
    public String getSelectItemContent() {
        List<String> chooseDatas = new ArrayList<>();
        Iterator<Integer> iterator = checkMap.keySet().iterator();
        while (iterator.hasNext()) {
            Integer key = iterator.next();
            if (checkMap.get(key)) {
                int count = 0;
                for (int i = 0; i < testBeans.size(); i++) {
                    if (count == key) {
                        chooseDatas.add(testBeans.get(i).getTitleName());
                    }
                    count++;
                    for (int j = 0; j < testBeans.get(i).getChildData().size(); j++) {
                        if (count == key) {
                            chooseDatas.add(testBeans.get(i).getChildData().get(j));
                        }
                        count++;
                    }
                }
            }
        }
        return new Gson().toJson(chooseDatas);
    }

    //判断是标题还是内容
    @Override
    public int getItemViewType(int position) {
        int count = 0;
        for (int i = 0; i < testBeans.size(); i++) {
            if (position == count) {
                return TITLE;
            }
            count++;
            for (int j = 0; j < testBeans.get(i).getChildData().size(); j++) {
                count++;
            }
        }
        return ITEM;
    }

    //根据位置获取title
    public String getTitle(int firstVisiblePosition) {
        int count = 0;
        for (int i = 0; i < testBeans.size(); i++) {
            if (firstVisiblePosition == count) {
                return testBeans.get(i).getTitleName();
            }
            count++;
            for (int j = 0; j < testBeans.get(i).getChildData().size(); j++) {


                if (count == firstVisiblePosition) {
                    return testBeans.get(i).getTitleName();
                }
                count++;
            }
        }

        return "无";
    }

    //title所在位置
    public int getTilePostion(String title) {
        int count = 0;
        for (int i = 0; i < testBeans.size(); i++) {
            if (testBeans.get(i).getTitleName().equals(title)) {
                return count;
            }
            count++;
            for (int j = 0; j < testBeans.get(i).getChildData().size(); j++) {
                count++;
            }
        }

        return 0;
    }

    //多少个item
    @Override
    public int getItemCount() {
        if (testBeans != null) {
            int count = 0;
            for (int i = 0; i < testBeans.size(); i++) {
                count++;
                for (int j = 0; j < testBeans.get(i).getChildData().size(); j++) {
                    count++;
                }
            }
            return count;
        }
        return 0;
    }

    /*interface CheckOncklickListener{
        void
    }*/

    class RightHolder extends RecyclerView.ViewHolder {
        TextView rightText;
        CheckBox checkBox;

        public RightHolder(@NonNull View itemView) {
            super(itemView);
            rightText = itemView.findViewById(R.id.right_text);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }

}


