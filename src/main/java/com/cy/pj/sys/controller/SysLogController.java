package com.cy.pj.sys.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

//@ResponseBody
//@Controller
@RestController //==@Controller+@ReponseBody
@RequestMapping("/log/")
public class SysLogController {
	@Autowired
	private SysLogService sysLogService;
	private static Logger log = LoggerFactory.getLogger(SysLogController.class);

	/**
	 * 基于客户端提交的记录id,执行删除业务,并反馈结果
	 * @param ids
	 * @return
	 */
	@RequestMapping("doDeleteObjects")
	//@ResponseBody
	public JsonResult doDeleteObjects(Integer...ids) {
		sysLogService.deleteObjects(ids);
		return new JsonResult("delete ok");
	}
	/**
	 * 基于条件查询当前页要呈现的记录,并将记录封装到JsonResult,目的时
	 * 服务端返回的客户端的信息一个状态.客户端可以基于此状态进行响应数据的处理.
	 * @param username 用户名,与客户端传递参数名相同
	 * @param pageCurrent 当前页码值
	 * @return 封装了业务结果的一个状态对象.
	 */
	@RequestMapping("doFindPageObjects")
	//@ResponseBody
	public JsonResult doFindPageObjects(String username,
			Integer pageCurrent) {
		//JsonResult r=new JsonResult();
		//r.setData(sysLogService.findPageObjects(username, pageCurrent));
		PageObject<SysLog> pageObject=
	    sysLogService.findPageObjects(username, pageCurrent);
		
		return new JsonResult(pageObject);//result.data.records
	}
	@RequestMapping("saveLog")
	//@ResponseBody
	public void saveLog(HttpServletRequest request) throws IOException {
		//对get请求串解码，防止中文乱码
		String qs = URLDecoder.decode(request.getQueryString(), "utf-8");

		//请求串中各指标以&符号分割
		String []  attrs = qs.split("\\&");
		//StringBuffer buf = new StringBuffer();
		Map map = new HashMap<String,String>();
		for(String attr : attrs){
			String[] a = attr.split("=");
			map.put(a[0],a[1]);
		}
		sysLogService.saveLog(map);
	}
}






