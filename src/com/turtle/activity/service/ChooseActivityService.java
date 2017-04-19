package com.turtle.activity.service;

import java.util.ArrayList;
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
public class ChooseActivityService extends AbstractActivityService {
	private static Logger logger = LoggerFactory.getLogger(ChooseActivityService.class);
	
	@Override
	public String getResult(Activity activity)  throws AppleException {
		String result = activity.getResult();
		JSONObject json = JSONObject.fromObject(result);
		
		String rs = json.getString("result");
		rs += " from " + json.getString("options");
		
		return rs;
	}

	@Override
	public int addActivity(String username, JSONObject param) throws AppleException {
		String type = ActivityType.CHOOSE.name();
		int rt = this.activityDao.addActivity(username, type, param.toString(), getDescription(username, param));
		return rt;
	}
	public List<Activity> queryActivity(String username, String sdate,String edate, int groupid ) throws AppleException {
		String type = ActivityType.CHOOSE.name();
		List<Activity> rt = this.activityDao.queryActivities(username, type, sdate, edate);
		List<Activity> res=new ArrayList<Activity>();
		for(int i=0;i<rt.size();i++){
			String tmpres=rt.get(i).getResult();
			JSONObject json=JSONObject.fromObject(tmpres);
			if(groupid==json.getInt("id")){
				res.add(rt.get(i));
			}
		}
		return res;
	}
	
	protected String getDescription(String username, JSONObject param) {
		String str = String.format("%s������%s", username, param.get("result"));
		return str;
	}
	
	public JSONObject createParam(String groupName, List<String>optionsList, List<Integer>propList, int idx) throws AppleException {
		if (optionsList == null || propList == null) {
			logger.warn("createParam ��������ȷ");
			throw new AppleException(IErrorCode.PARAM_ERROR, "��������ȷ���б�Ϊ��");
		} else if (optionsList.size() != propList.size()) {
			logger.warn("createParam List��С��һ��");
			throw new AppleException(IErrorCode.PARAM_ERROR, "��������ȷ���б��С��һ��");
		} else if (idx >= optionsList.size()) {
			logger.warn("createParam index���");
			throw new AppleException(IErrorCode.PARAM_ERROR, "��������ȷ��index���");
		}
		
		JSONObject json = new JSONObject();
		json.put("groupName", groupName);
		
		StringBuffer sb = new StringBuffer();
		sb.append(optionsList.get(idx));
		json.put("result", sb.toString());
		
		sb = new StringBuffer();
		for (int i = 0; i < optionsList.size(); i++) {
			if (i > 0) {
				sb.append(",");
			}
			String option = optionsList.get(i);
			int prop = propList.get(i);
			String str = String.format("%s(%d) ", option, prop);
			sb.append(str);
		}
		json.put("options", sb.toString());
		return json;
	}

}
