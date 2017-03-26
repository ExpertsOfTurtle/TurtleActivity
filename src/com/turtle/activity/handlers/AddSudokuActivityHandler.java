package com.turtle.activity.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frc.appleframework.beans.IRequest;
import com.frc.appleframework.exception.AppleException;
import com.frc.appleframework.hanlders.AbstractHandler;
import com.turtle.activity.beans.SudokuActivityRequest;
import com.turtle.activity.service.SudokuActivityService;

import net.sf.json.JSONObject;

@Service("AddSudokuActivityHandler")
public class AddSudokuActivityHandler extends AbstractHandler {
	private static Logger logger = LoggerFactory.getLogger(AddSudokuActivityHandler.class);
			
	@Autowired
	protected SudokuActivityService sudokuActivityService = null;
	
	@Override
	public void process(IRequest request) throws AppleException {
		SudokuActivityRequest req = (SudokuActivityRequest) request;
		
		logger.debug("AddSudokuActivityHandler request:" + JSONObject.fromObject(req).toString());
		
		String username = req.getUsername();
		String type = req.getType();
		int second = req.getSecond();
		JSONObject param = sudokuActivityService.createParam(type, second, "");
		int rt = sudokuActivityService.addActivity(username, param);
		
		putRequestData("result", rt);
	}

}
