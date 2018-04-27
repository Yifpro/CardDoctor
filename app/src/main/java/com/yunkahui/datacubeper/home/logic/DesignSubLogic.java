package com.yunkahui.datacubeper.home.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BaseBeanList;
import com.yunkahui.datacubeper.common.bean.SmartPlanSub;
import com.yunkahui.datacubeper.common.bean.TodayOperationSub;
import com.yunkahui.datacubeper.common.utils.CustomConverterFactory;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by YD1 on 2018/4/12
 */
public class DesignSubLogic {

    public void requestTodayOperation(Context context, String isPos, int pageSize, int pageNum, SimpleCallBack<BaseBean<TodayOperationSub>> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("is_pos", isPos)
                .addParams("page", String.valueOf(pageSize))
                .addParams("num", String.valueOf(pageNum))
                .create();
        HttpManager.getInstance().create(ApiService.class).loadTodayOperation(params)
                .compose(HttpManager.<BaseBean<TodayOperationSub>>applySchedulers()).subscribe(callBack);

    }

    public void requestSmartPlan(Context context, String isPos, int pageSize, int pageNum, SimpleCallBack<BaseBean<List<SmartPlanSub>>> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("is_pos", isPos)
                .addParams("pageSize", String.valueOf(pageSize))
                .addParams("pageNum", String.valueOf(pageNum))
                .create();
        HttpManager.getInstance().create(ApiService.class).loadSmartPlan(params)
                .compose(HttpManager.<BaseBean<List<SmartPlanSub>>>applySchedulers()).subscribe(callBack);
    }
}
