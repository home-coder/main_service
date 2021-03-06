package com.zccl.ruiqianqi.domain.repository;

import com.zccl.ruiqianqi.domain.model.httpreq.BoYanUp;
import com.zccl.ruiqianqi.domain.model.httpreq.CustomQaUp;

import rx.Observable;

/**
 * Created by ruiqianqi on 2016/7/18 0018.
 */
public interface IHttpReqRepository {
    Observable<String> queryBoYanRx(BoYanUp boYanUp);
    Observable<String> queryCustomQARx(CustomQaUp customQaUp);
}
