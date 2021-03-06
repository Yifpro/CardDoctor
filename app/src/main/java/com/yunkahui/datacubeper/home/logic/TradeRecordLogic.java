package com.yunkahui.datacubeper.home.logic;

import android.annotation.SuppressLint;
import android.content.Context;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.CardDoctorApplication;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.RechargeRecord;
import com.yunkahui.datacubeper.common.bean.TradeRecordDetail;
import com.yunkahui.datacubeper.common.bean.TradeRecordSummary;
import com.yunkahui.datacubeper.common.bean.WithdrawRecord;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TradeRecordLogic {

    //******** 获取充值记录 ********
    public void getRechargeRecord(Context context, String type, int pageSize, int pageNum, SimpleCallBack<BaseBean<RechargeRecord>> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("recharge_type", type)
                .addParams("pageSize", String.valueOf(pageSize))
                .addParams("pageNum", String.valueOf(pageNum))
                .create();
        HttpManager.getInstance().create(ApiService.class).loadRechargeOrder(params)
                .compose(HttpManager.<BaseBean<RechargeRecord>>applySchedulers()).subscribe(callBack);

    }

    //******** 获取提现记录 ********
    public void getWithdrawRecord(Context context, String type, int pageSize, int pageNum, SimpleCallBack<BaseBean<WithdrawRecord>> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("with_draw_type", type)
                .addParams("pageSize", String.valueOf(pageSize))
                .addParams("pageNum", String.valueOf(pageNum))
                .create();
        HttpManager.getInstance().create(ApiService.class).loadWithdrawOrderRecord(params)
                .compose(HttpManager.<BaseBean<WithdrawRecord>>applySchedulers()).subscribe(callBack);

    }

    //******** 获取交易明细 ********
    public void getTradeDetail(Context context, int pageSize, int pageNum, String checkType, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("pageSize", String.valueOf(pageSize))
                .addParams("pageNum", String.valueOf(pageNum))
                .addParams("check_type", checkType)
                .create();
        HttpManager.getInstance().create(ApiService.class).loadTradeDetail(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);

    }

    JSONArray mJSONArrayData;

    public List<MultiItemEntity> parsingJSONForTradeDetail(BaseBean baseBean) {
        List<MultiItemEntity> list = null;
        try {
            list = new ArrayList<>();
            JSONObject object = baseBean.getJsonObject();
            JSONObject respData = object.optJSONObject("respData");
            JSONArray jsonArray = respData.optJSONArray("list");
            TradeRecordDetail item;
            TradeRecordSummary summary = new TradeRecordSummary();
            TradeRecordDetail lastItem = null;

            if (mJSONArrayData == null) {
                mJSONArrayData = new JSONArray(jsonArray.toString());
            } else {
                for (int i = 0; i < jsonArray.length(); i++) {
                    mJSONArrayData.put(jsonArray.optJSONObject(i));
                }
            }

            for (int i = 0; i < mJSONArrayData.length(); i++) {
                JSONObject j = new JSONObject(mJSONArrayData.opt(i).toString());
                item = new TradeRecordDetail();
                item.setTimeStamp(j.optLong("create_time"));
                item.setTradeType(j.optString("trade_type"));
                item.setTime(com.yunkahui.datacubeper.common.utils.TimeUtils.format("MM-dd hh:mm", j.optLong("create_time")));
                item.setMoney(j.optString("change_amount"));
                item.setTitle(j.optString("trade_type_desc"));
                if (lastItem != null) {
                    if (item.getTime().startsWith("0") && lastItem.getTime().startsWith("0") &&
                            Integer.parseInt(lastItem.getTime().substring(1, 2)) > Integer.parseInt(item.getTime().substring(1, 2))) {
                        summaryInfo(summary);
                        list.add(summary);
                        summary = new TradeRecordSummary();
                        summary.addSubItem(item);
                    } else if (!item.getTime().startsWith("0") && lastItem.getTime().startsWith("0")) {
                        summaryInfo(summary);
                        list.add(summary);
                        summary = new TradeRecordSummary();
                        summary.addSubItem(item);
                    } else {
                        summary.addSubItem(item);
                    }
                } else {
                    summary.addSubItem(item);
                }
                if (i == mJSONArrayData.length() - 1) {
                    summaryInfo(summary);
                    list.add(summary);
                } else {
                    lastItem = item;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    //******** 统计月份信息并设置给数据体 ********
    @SuppressLint("StringFormatMatches")
    private void summaryInfo(TradeRecordSummary summary) {
        double pay = 0, back = 0;
        for (TradeRecordDetail detail : summary.getSubItems()) {
            if (Double.parseDouble(detail.getMoney()) >= 0) {
                back += Double.parseDouble(detail.getMoney());
            } else {
                pay += Double.parseDouble(detail.getMoney());
            }
        }
        DecimalFormat df = new java.text.DecimalFormat("0.00");
        summary.setTime(com.yunkahui.datacubeper.common.utils.TimeUtils.format("yyyy年MM月", summary.getSubItem(0).getTimeStamp()));
        summary.setMessage(String.format(CardDoctorApplication.getContext().getString(R.string.pay_back_format), String.valueOf(df.format(back)), String.valueOf(df.format(pay))));
        summary.setYear(TimeUtils.format("yyyy",summary.getSubItem(0).getTimeStamp()));
        summary.setMonth(TimeUtils.format("MM",summary.getSubItem(0).getTimeStamp()));
    }
}
