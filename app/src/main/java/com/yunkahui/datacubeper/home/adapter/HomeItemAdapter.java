package com.yunkahui.datacubeper.home.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.HomeItem;
import java.util.List;

/**
 * Created by pc1994 on 2018/3/23
 */
public class HomeItemAdapter extends BaseQuickAdapter<HomeItem, BaseViewHolder> {

    public HomeItemAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeItem item) {
        helper.setBackgroundRes(R.id.image, item.getIcon());
        helper.setText(R.id.title, item.getTitle());
    }

}
