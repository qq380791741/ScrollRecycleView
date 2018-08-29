package com.test.tcc.recyclerviewscrolldemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.left)
    RecyclerView left;
    @Bind(R.id.right)
    RecyclerView right;
    String[] datas = new String[]{"零时", "酒水", "牛奶", "威士忌", "白兰地", "二锅头", "江小白", "茅台", "啊福", "小老虎", "打老虎", "太湖",
            "阿峰", "下次", "小吃", "衣服", "首饰", "鞋子", "运动", "休闲", "奢侈品", "龙套"};
    @Bind(R.id.submit_choose)
    Button submitChoose;
    @Bind(R.id.right_title)
    TextView rightTitle;
    private List<TestBean> testBeans = new ArrayList<>();
    private int firstVisiblePosition = 0;
    private int lastVisiblePosition = 0;
    private RightAdapter rightAdapter;
    //记录title的位置
    private Map<Integer, String> titlePoss = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initRecylerView();
    }

    private void initRecylerView() {
        final leftAdapter leftAdapter = new leftAdapter(this, testBeans, testBeans.get(0).getTitleName());
        rightAdapter = new RightAdapter(this, testBeans);
        final AdvertiseLinearLayoutManager lmng = new AdvertiseLinearLayoutManager(this);
        final LinearLayoutManager leftLLM = new LinearLayoutManager(this);
        left.setLayoutManager(new LinearLayoutManager(this));
        right.setLayoutManager(lmng);

        left.setAdapter(leftAdapter);
        right.setAdapter(rightAdapter);

        right.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) right.getLayoutManager();
                LinearLayoutManager leftLayoutManager = (LinearLayoutManager) left.getLayoutManager();
                //可见第一个item位置
                firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
                lastVisiblePosition = leftLayoutManager.findLastVisibleItemPosition();
                String title = rightAdapter.getTitle(firstVisiblePosition);
                rightTitle.setText("这里是标题:"+title);
                leftAdapter.setTitle(title);
                int leftTitlePosition = leftAdapter.getTitlePosition();
                left.smoothScrollToPosition(leftTitlePosition);
            }
        });

        leftAdapter.setLeftOnclickListener(new leftAdapter.LeftOnlickListener() {
            @Override
            public void leftOnlickListener(String title) {
                int firstItem = lmng.findFirstVisibleItemPosition();
                int lastItem = lmng.findLastVisibleItemPosition();
                int tilePostion = rightAdapter.getTilePostion(title);
                if (tilePostion < firstItem) {
                    right.smoothScrollToPosition(tilePostion);
                } else if (tilePostion <= lastItem) {
                    int movePostion = tilePostion - firstItem;
                    if (movePostion >= 0 && movePostion < right.getChildCount()) {
                        int top = right.getChildAt(movePostion).getTop();
                        right.smoothScrollBy(0, top);
                    }
                } else {
                    right.smoothScrollToPosition(tilePostion);
                }
                // lmng.scrollToPositionWithOffset(tilePostion,0);

            }
        });


    }

    private void initData() {
        for (int i = 0; i < datas.length; i++) {
            TestBean testBean = new TestBean();
            testBean.setTitleName(datas[i]);
            List<String> list = new ArrayList<>();
            for (int j = 0; j < 6; j++) {
                list.add(datas[i] + j);
            }
            testBean.setChildData(list);
            testBeans.add(testBean);
        }

        int count = 0;
        for (int i = 0; i < testBeans.size(); i++) {
            titlePoss.put(count, testBeans.get(i).getTitleName());
            count++;
            for (int j = 0; j < testBeans.get(i).getChildData().size(); j++) {
                count++;
            }
        }

    }


    @OnClick(R.id.submit_choose)
    public void onViewClicked() {
        if (rightAdapter != null) {
            String chooseContent = rightAdapter.getSelectItemContent();
            MyToast.showToastLong(this, chooseContent);
        }
    }
}
