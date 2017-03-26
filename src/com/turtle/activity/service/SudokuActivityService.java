package com.turtle.activity.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.frc.appleframework.exception.AppleException;
import com.frc.appleframework.util.StringUtil;
import com.turtle.activity.common.ActivityType;
import com.turtle.activity.common.IErrorCode;
import com.turtle.activity.entity.Activity;

import net.sf.json.JSONObject;

@Service
public class SudokuActivityService extends AbstractActivityService {
	private static Logger logger = LoggerFactory.getLogger(SudokuActivityService.class);
	
	@Override
	public String getResult(Activity activity)  throws AppleException {
		String result = activity.getResult();
		JSONObject json = JSONObject.fromObject(result);
		
		String type = json.getString("type");
		int second = json.getInt("result");
		String time = formatSecond(second);
		String rs = String.format("数独-%s, 用时", type, time);
		
		return rs;
	}

	@Override
	public int addActivity(String username, JSONObject param) throws AppleException {
		String type = ActivityType.SUDOKU.name();
		/*
		String id = param.getString("id");
		if (!StringUtil.isEmpty(id)) {
			List<Activity> list = activityDao.queryActivities(username, type, "", "");
			boolean flag = false;
			for (Activity act : list) {
				String result = act.getResult();
				if(StringUtil.isEmpty(result)) {
					continue;
				}
				JSONObject obj = JSONObject.fromObject(result);
				if (obj == null) {
					continue;
				}
				Object objId = obj.get("id");
				if (objId != null && id.equals(objId)) {
					flag = true;
					break;
				}
			}
			if (flag) {
				logger.info("曾经插入过了 id={}", id);
				return 0;
			}
		}*/
		
		int rt = this.activityDao.addActivity(username, type, param.toString(), getDescription(username, param));
		return rt;
	}
	
	protected String getDescription(String username, JSONObject param) {
		int second = param.getInt("result");
		String time = formatSecond(second);
		
		String str = String.format("玩数独(%s),用时%s",
				param.get("type"), time);
		return str;
	}
	
	public JSONObject createParam(String type, int second, String id) throws AppleException {
		JSONObject json = new JSONObject();
		json.put("type", type);
		json.put("result", second);
		if (!StringUtil.isEmpty(id)) {
			json.put("id", id);
		}
		return json;
	}
	
	protected String formatSecond(int second) {
		String rs = "";
		if (second >= 3600) {
			rs += String.format(" %d小时", second/3600);
			second %= 3600;
		}
		if (second >= 60) {
			rs += String.format(" %d分钟", second / 60);
			second %= 60;
		}
		if (second > 0) {
			rs += String.format(" %d秒", second);
		}
		return rs.trim();
	}

}
