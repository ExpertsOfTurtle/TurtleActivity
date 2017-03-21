package com.turtle.activity.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.frc.appleframework.exception.AppleException;
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
		String rs = String.format("����-%s, ��ʱ", type, time);
		
		return rs;
	}

	@Override
	public int addActivity(String username, JSONObject param) throws AppleException {
		String type = ActivityType.SUDOKU.name();
		int rt = this.activityDao.addActivity(username, type, param.toString(), getDescription(username, param));
		return rt;
	}
	
	protected String getDescription(String username, JSONObject param) {
		int second = param.getInt("result");
		String time = formatSecond(second);
		
		String str = String.format("������(%s),��ʱ%s",
				param.get("type"), time);
		return str;
	}
	
	public JSONObject createParam(String type, int second) throws AppleException {
		JSONObject json = new JSONObject();
		json.put("type", type);
		json.put("result", second);
		return json;
	}
	
	protected String formatSecond(int second) {
		String rs = "";
		if (second >= 3600) {
			rs += String.format(" %dСʱ", second/3600);
			second %= 3600;
		}
		if (second >= 60) {
			rs += String.format(" %d����", second / 60);
			second %= 60;
		}
		if (second > 0) {
			rs += String.format(" %d��", second);
		}
		return rs.trim();
	}

}
