package com.yunkahui.datacubeper.login.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/3.
 */

public class LoginLogic {


    public void login(Context context, String phone, String password, SimpleCallBack<BaseBean> callBack){

        Map<String,String> params=RequestUtils.newParams(context)
                .addParams("user_mobile",phone)
                .addParams("user_password",password)
                .create();
        HttpManager.getInstance().create(ApiService.class).login(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);

    }


}
