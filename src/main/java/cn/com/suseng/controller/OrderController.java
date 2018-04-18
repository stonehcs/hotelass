package cn.com.suseng.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.com.suseng.pojo.orders;
import cn.com.suseng.service.OrderService;
import cn.com.suseng.utils.SysResult;

@Controller
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	//插入订单
	@RequestMapping(value="/insertorder",method=RequestMethod.GET)
	@ResponseBody
	public SysResult insertOrder(@RequestBody String json) throws JsonParseException, JsonMappingException, IOException{
		orders order = MAPPER.readValue(json, orders.class);
		orderService.insertOrder(order);
		return SysResult.ok();
	}
	//https://www.suseng.com.cn/selectorder  参数是userid 获取的用户头像地址   包括查询时全部查询出来的 暂时
	@RequestMapping(value="/selectorder",method=RequestMethod.GET)
	@ResponseBody
	public SysResult selectOrders(String userid){
		
		//暂时是全部查询出来
		List<orders> order = orderService.queryorders(userid);
		return SysResult.ok(order);
	}
}
