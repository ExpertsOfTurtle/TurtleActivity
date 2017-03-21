package com.turtle.activity.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.frc.appleframework.exception.AppleException;
import com.turtle.activity.dao.ActivityDao;
import com.turtle.activity.entity.Activity;

import net.sf.json.JSONObject;

public abstract class AbstractActivityService {
	
	@Autowired
	protected ActivityDao activityDao = null;
	
	public abstract String getResult(Activity activity) throws AppleException ;
	public abstract int addActivity(String username, JSONObject param) throws AppleException ;
}
