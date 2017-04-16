package com.turtle.activity.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frc.appleframework.exception.AppleException;
import com.frc.appleframework.util.DateUtil;
import com.frc.appleframework.util.StringUtil;
import com.turtle.activity.common.ActivityType;
import com.turtle.activity.common.IErrorCode;
import com.turtle.activity.entity.Activity;
import com.turtle.activity.entity.ActivityExample;
import com.turtle.activity.entity.ActivityExample.Criteria;

@Service("ActivityDao")
public class ActivityDao {
	private static Logger logger = LoggerFactory.getLogger(ActivityDao.class);
	
	@Autowired
	protected SqlSessionFactory sessionFactory = null;
	
	public int addActivity(String username, String type, String result, String description) throws AppleException {
		if (!validateType(type)) {
			String log = String.format("addActivity - [%s] not exist", type);
			throw new AppleException(IErrorCode.TYPE_NOT_EXIST, log);
		}
		
		SqlSession session = sessionFactory.openSession();
		
		ActivityMapper mapper = session.getMapper(ActivityMapper.class);
		
		Activity activity = new Activity();
		activity.setUsername(username);
		activity.setType(type);
		activity.setResult(result);
		activity.setDescription(description);
		activity.setDatetime(DateUtil.getDateTime("yyyyMMdd"));
		
		mapper.insert(activity);
		
		int id = activity.getIdactivity();
		
		return id;
	}
	
	
	
	public List<Activity> queryActivities(String username, String type, String fromDate, String toDate) throws AppleException {
		if (!StringUtil.isEmpty(type) && 
			!validateType(type)) {
			String log = String.format("addActivity - [%s] not exist", type);
			throw new AppleException(IErrorCode.TYPE_NOT_EXIST, log);
		}
		if (!StringUtil.isEmpty(fromDate) &&
			!StringUtil.isEmpty(toDate) &&
			!validateDate(fromDate, toDate)) {
			String log = String.format("addActivity - date param [%s,%s] error", fromDate, toDate);
			throw new AppleException(IErrorCode.PARAM_ERROR, log);
		}
		
		List<Activity> list = null;
		
		SqlSession session = sessionFactory.openSession();		
		ActivityMapper mapper = session.getMapper(ActivityMapper.class);
		ActivityExample example = new ActivityExample();
		
		Criteria criteria = example.createCriteria();
		if (!StringUtil.isEmpty(username)) {
			criteria.andUsernameEqualTo(username);
		}
		if (!StringUtil.isEmpty(type)) {
			criteria.andTypeEqualTo(type);	
		}
		if (!StringUtil.isEmpty(fromDate) && !StringUtil.isEmpty(toDate)) {
			criteria.andDatetimeBetween(fromDate, toDate);
		}
		example.setOrderByClause("idactivity desc");
		list = mapper.selectByExample(example);
		
		logger.debug("queryActivity. RowCount={}", list.size());
		return list;
	}
	
	private boolean validateDate(String fromDate, String toDate) {
		Date d1 = DateUtil.parse(fromDate);
		Date d2 = DateUtil.parse(toDate);
		if (d1 == null || d2 == null) {
			return false;
		}
		if (d1.getTime() > d2.getTime()) {
			return false;
		}
		return true;
	}
	private boolean validateType(String type) {
		for (ActivityType at : ActivityType.values()) {
			if (type.equals(at.name())) {
				return true;
			}
		}
		logger.warn("Validation fail! type:{}", type);
		return false;
	}
}
