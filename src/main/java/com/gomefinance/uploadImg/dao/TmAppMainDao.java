package com.gomefinance.uploadImg.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gomefinance.uploadImg.domain.TmAppMain;
import com.gomefinance.uploadImg.mapper.TmAppMainMapper;


@Repository
public class TmAppMainDao {
    @Autowired
    private TmAppMainMapper tmAppMainMapper;

    public TmAppMain getAppMainByAppNo(String appNo){
        return tmAppMainMapper.getAppMainByAppNo(appNo);
    }
    
    public int getAppMainCount(String createTime){
        return tmAppMainMapper.getAppMainCount(createTime);
    }
    public List<TmAppMain> getTmAppMain(String createTime,int begin,int end){
        return tmAppMainMapper.getTmAppMain(createTime,begin,end);
    }
}
