package com.yunkahui.datacubeper.home.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.bean.CardSelectorBean;
import com.yunkahui.datacubeper.common.view.CardSelector;
import com.yunkahui.datacubeper.home.ui.RechargeActivity;

import java.util.List;

public class RechargeAdapter extends BaseQuickAdapter<CardSelectorBean, BaseViewHolder> {

    private Context mContext;

    public RechargeAdapter(Context context, int layoutResId, List data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CardSelectorBean item) {
        CardSelector selector = helper.getView(R.id.card_selector);
        selector.setText(item.getBankCardName()+item.getBankCardNum());
        selector.setChecked(item.isChecked());
    }

}
