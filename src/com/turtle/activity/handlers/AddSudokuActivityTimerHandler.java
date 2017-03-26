package com.turtle.activity.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frc.appleframework.beans.AppleRequest;
import com.frc.appleframework.beans.IRequest;
import com.frc.appleframework.exception.AppleException;
import com.frc.appleframework.hanlders.AbstractHandler;
import com.turtle.activity.beans.SudokuActivityRequest;
import com.turtle.activity.service.SudokuActivityService;
import com.turtle.activity.sudoku.dao.GetSudokuRecordDao;

import net.sf.json.JSONObject;

@Service("AddSudokuActivityTimerHandler")
public class AddSudokuActivityTimerHandler extends AbstractHandler {
	private static Logger logger = LoggerFactory.getLogger(AddSudokuActivityTimerHandler.class);
			
	@Autowired
	protected GetSudokuRecordDao getSudokuRecordDao = null;
	
	@Override
	public void process(IRequest request) throws AppleException {
		AppleRequest req = (AppleRequest) request;
		
		logger.debug("AddSudokuActivityTimerHandler request:" + JSONObject.fromObject(req).toString());
		
		try {
			getSudokuRecordDao.sendHttp("Worldfinal");
			getSudokuRecordDao.sendHttp("Could");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.debug("Done");
		putRequestData("result", "OK");
	}

}
