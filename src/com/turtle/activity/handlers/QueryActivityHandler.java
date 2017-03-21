package com.turtle.activity.handlers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frc.appleframework.beans.IRequest;
import com.frc.appleframework.exception.AppleException;
import com.frc.appleframework.hanlders.AbstractHandler;
import com.turtle.activity.beans.QueryActivityRequest;
import com.turtle.activity.beans.SudokuActivityRequest;
import com.turtle.activity.entity.Activity;
import com.turtle.activity.service.NormalActivityService;
import com.turtle.activity.service.SudokuActivityService;

import net.sf.json.JSONObject;

@Service("QueryActivityHandler")
public class QueryActivityHandler extends AbstractHandler {
	private static Logger logger = LoggerFactory.getLogger(QueryActivityHandler.class);
			
	@Autowired
	protected NormalActivityService normalActivityService = null;
	
	@Override
	public void process(IRequest request) throws AppleException {
		QueryActivityRequest req = (QueryActivityRequest) request;
		
		logger.debug("QueryActivityHandler request:" + JSONObject.fromObject(req).toString());
		
		String username = req.getUsername();
		String type = req.getType();
		String fromDate = req.getFromDate();
		String toDate = req.getToDate();
		
		List<Activity> list = normalActivityService.queryActivities(username, type, fromDate, toDate);
		
		putRequestData("activityList", list);
	}

}
