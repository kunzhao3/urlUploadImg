package com.gomefinance.uploadImg.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gomefinance.uploadImg.common.datasource.DatabaseContextHolder;
import com.gomefinance.uploadImg.common.datasource.DatabaseType;
import com.gomefinance.uploadImg.dao.TmAppMainDao;
import com.gomefinance.uploadImg.domain.TmAppMain;
import com.gomefinance.uploadImg.service.TmAppMainService;

@Service
public class TmAppMainServiceImple implements TmAppMainService {
	@Autowired
	TmAppMainDao tmAppMainDao;

	@Override
	public TmAppMain getAppMain(String appNo) {
        DatabaseContextHolder.setDatabaseType(DatabaseType.jiedb);
        TmAppMain tmAppMain = tmAppMainDao.getAppMainByAppNo(appNo);
        return tmAppMain;
	}

	@Override
	public int getAppMainCount(String createTime) {
		return tmAppMainDao.getAppMainCount(createTime);
	}

	@Override
	public List<TmAppMain> getTmAppMain(String createTime, int begin,
			int end) {
		return tmAppMainDao.getTmAppMain(createTime, begin, end);
	}
}
