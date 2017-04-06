package com.turtle.activity.sudoku.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frc.appleframework.util.IOUtil;
import com.frc.appleframework.util.StringUtil;
import com.turtle.activity.common.ActivityType;
import com.turtle.activity.dao.ActivityDao;
import com.turtle.activity.entity.Activity;
import com.turtle.activity.service.SudokuActivityService;

import net.sf.json.JSONObject;

@Service
public class GetSudokuRecordDao {
	public static Map<String, String>USER_CONFIG = new HashMap<>();
	static {
		USER_CONFIG.put("Worldfinal", "摧毁宇宙");
		USER_CONFIG.put("Could", "Could1991");
	}
	
	@Autowired
	protected ActivityDao activityDao = null;
	
	@Autowired
	private SudokuActivityService sudokuActivityService = null;
	
	@Test
	public void parseHTML() {
		String html = IOUtil.readTxtFile("e:\\test\\sudoku.txt");
		Document doc = Jsoup.parse(html);
		Elements es = doc.select(".feed_title");
		for (int i = 0; i < es.size(); i++) {
			Element e = es.get(i);
			Element firstChild = e.children().first();				
			List<Node> list = e.childNodes();
			SudokuRecord record = new SudokuRecord();
			for (Node node : list) {
				parseTimeAndLevel(node, record);
				parseId(node, record);
			}
			System.out.println(record.id + ":" + record.level + "," + record.time);
		}
	}
	public void sendHttp(String username) throws Exception, IOException {
		String name = USER_CONFIG.get(username);
		if (StringUtil.isEmpty(name)) {
			return;
		}
		String url = "http://www.oubk.com/" + name;
		HttpGet get = new HttpGet(url);
		get.addHeader("Content-Type","text/html;charset=UTF-8");
		
		HttpClient http = new DefaultHttpClient();
		HttpResponse response = http.execute(get);

		List<Activity> actList = activityDao.queryActivities(username, ActivityType.SUDOKU.name(), "", "");
		Map<String, Activity> map = new HashMap<>();
		for (Activity act : actList) {
			String result = act.getResult();
			if(StringUtil.isEmpty(result)) {
				continue;
			}
			JSONObject obj = JSONObject.fromObject(result);
			if (obj == null) {
				continue;
			}
			Object objId = obj.get("id");
			if (objId != null) {
				map.put((String)objId, act);
			}
		}
		
		// (3) 处理响应结果
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();

			// (4) 从输入流读取网页字符串内容
			System.out.println(entity.getContentType());
			System.out.println(entity.getContentEncoding());
			System.out.println(entity.getContentLength());
			
			String html = getResponseText(entity);
			Document doc = Jsoup.parse(html);
			Elements es = doc.select(".feed_title");
			for (int i = 0; i < es.size(); i++) {
				Element e = es.get(i);		
				String txt = e.html();
				if (!txt.contains("PK")) {//只统计PK的，每日数独不计算在内
					continue;
				}
				List<Node> list = e.childNodes();
				SudokuRecord record = new SudokuRecord();
				for (Node node : list) {
					parseTimeAndLevel(node, record);
					parseId(node, record);
				}
				if (!StringUtil.isEmpty(record.getId()) &&
					!StringUtil.isEmpty(record.getLevel()) && 
					record.getTime() > 0 && 
					map.get(record.getId()) == null) {
					JSONObject param = sudokuActivityService.createParam(record.getLevel(), record.getTime(), record.getId());
					sudokuActivityService.addActivity(username, param);
				}
//				System.out.println(record.id + ":" + record.level + "," + record.time);
			}
			
		}
	}
	protected void parseId(Node node, SudokuRecord record) {
		String nodeName = node.nodeName();
		if (!"a".equals(nodeName)) {
			return;
		}
		String txt = node.attr("href");
		Pattern pattern  = Pattern.compile("/replay/(\\d*)");
		Matcher matcher = pattern.matcher(txt);
		if (matcher.find()) {
			String id = matcher.group(1);
			record.setId(id);
		}
		
	}
	protected void parseTimeAndLevel(Node node, SudokuRecord record) {
		String nodeName = node.nodeName();
		if (!"#text".equals(nodeName)) {
			return;
		}
		String txt = node.outerHtml();
		Pattern pattern  = Pattern.compile("(\\d{1,3})\\'(\\d{1,2})\\'\\'[^（）]*（(.*)）");
		Matcher matcher = pattern.matcher(txt);
		if (matcher.find()) {
			String minute = matcher.group(1);
			String second = matcher.group(2);
			String level = matcher.group(3);
			int m = Integer.parseInt(minute);
			int s = Integer.parseInt(second);
			s += 60 * m;
			record.setLevel(level);
			record.setTime(s);
		}
	}
	@Test
	public void test() {
		try {
			sendHttp("Worldfinal");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getResponseText(HttpEntity entity) throws Exception {
		StringBuffer sb = new StringBuffer();
		InputStream in = null;
		try {
			in = entity.getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
//				System.out.println(line);
				sb.append(line);
			}

		} finally {
			// 记得关闭输入流
			if (in != null)
				in.close();
		}
		return sb.toString();
	}
}
