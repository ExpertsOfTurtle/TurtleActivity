package com.turtle.activity.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frc.appleframework.exception.AppleException;
import com.turtle.activity.common.ActivityType;
import com.turtle.activity.common.IErrorCode;
import com.turtle.activity.dao.ActivityDao;
import com.turtle.activity.entity.Activity;

import net.sf.json.JSONObject;

@Service
public class NormalActivityService {
	private static Logger logger = LoggerFactory.getLogger(NormalActivityService.class);
	
	@Autowired
	private ActivityDao activityDao = null;
	
	public List<Activity> queryActivities(String username, String type, String fromDate, String toDate) throws AppleException {
		List<Activity> list = activityDao.queryActivities(username, type, fromDate, toDate);
		return list;
	}

	public ActivityDao getActivityDao() {
		return activityDao;
	}
	public void setActivityDao(ActivityDao activityDao) {
		this.activityDao = activityDao;
	}

	
}
