package com.yunkahui.datacubeper.home.logic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.ArrayMap;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.CardDoctorApplication;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.TradeRecordDetail;
import com.yunkahui.datacubeper.common.bean.TradeRecordSummary;
import com.yunkahui.datacubeper.common.bean.WithdrawRecord;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfitIncomeLogic {

    JSONArray jsonArrayData;

    //POS分润
    public void getPosFenRunData(Context context, int pageSize, int pageNum, String type, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("pageSize", String.valueOf(pageSize))
                .addParams("pageNum", String.valueOf(pageNum))
                .addParams("type", type)
                .create();
        HttpManager.getInstance().create(ApiService.class).loadPosFenRunData(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }


    //******** 查询分润收入 ********
    public void getProfitIncome(Context context, int pageSize, int pageNum, String checkType, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("pageSize", String.valueOf(pageSize))
                .addParams("pageNum", String.valueOf(pageNum))
                .addParams("check_type", checkType)
                .create();
        HttpManager.getInstance().create(ApiService.class).loadTradeDetail(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    //统计收入/支出
    public void loadStatisticalMoney(Context context, String year, String month,
                                     String accountType, String statistType, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("y_month", year + "-" + month)
                .addParams("account_type", accountType)
                .addParams("static_type", statistType)
                .create();
        HttpManager.getInstance().create(ApiService.class).loadStatisticalMoney(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    public List<MultiItemEntity> parsingJSONForProfitIncome(BaseBean baseBean) {
        List<MultiItemEntity> list = null;
        try {
            list = new ArrayList<>();
            JSONObject object = baseBean.getJsonObject();
            JSONObject respData = object.optJSONObject("respData");
            JSONArray jsonArray = respData.optJSONArray("list");

            if (jsonArrayData == null) {
                jsonArrayData = new JSONArray(jsonArray.toString());
            } else {
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonArrayData.put(jsonArray.optJSONObject(i));
                }
            }

            TradeRecordDetail item;
            TradeRecordSummary summary = new TradeRecordSummary();
            TradeRecordDetail lastItem = null;
            for (int i = 0; i < jsonArrayData.length(); i++) {
                JSONObject j = new JSONObject(jsonArrayData.opt(i).toString());
                item = new TradeRecordDetail();
                item.setTimeStamp(j.optLong("create_time"));
                item.setTradeType(j.optString("trade_type"));
                item.setTime(com.yunkahui.datacubeper.common.utils.TimeUtils.format("MM-dd hh:mm", j.optLong("create_time")));
                item.setMoney(j.optString("change_amount") + "");
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
                if (i == jsonArrayData.length() - 1) {
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
            double money = Double.parseDouble(detail.getMoney());
            if (money > 0) {
                back += Double.parseDouble(detail.getMoney());
            } else {
                pay += Double.parseDouble(detail.getMoney());
            }
        }
        DecimalFormat df = new java.text.DecimalFormat("0.00");
        summary.setTime(com.yunkahui.datacubeper.common.utils.TimeUtils.format("yyyy年MM月", summary.getSubItem(0).getTimeStamp()));
        summary.setMessage(String.format(CardDoctorApplication.getContext().getString(R.string.pay_back_format), String.valueOf(df.format(back)), String.valueOf(df.format(pay))));
        summary.setYear(TimeUtils.format("yyyy", summary.getSubItem(0).getTimeStamp()));
        summary.setMonth(TimeUtils.format("MM", summary.getSubItem(0).getTimeStamp()));
    }
}
