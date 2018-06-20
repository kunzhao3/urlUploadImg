package com.gomefinance.uploadImg.service;

import java.util.List;

import com.gomefinance.uploadImg.domain.TmAppMain;

public interface TmAppMainService {
    public TmAppMain getAppMain(String appNo);
    public int getAppMainCount(String createTime);
    public  List<TmAppMain> getTmAppMain(String createTime,int begin,int end);
}
