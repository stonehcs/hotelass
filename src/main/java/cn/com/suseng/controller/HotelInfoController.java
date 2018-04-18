package cn.com.suseng.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Base64.Decoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.com.suseng.pojo.LatLng;
import cn.com.suseng.pojo.Marker;
import cn.com.suseng.pojo.roomstatus;
import cn.com.suseng.pojo.wxMarker;
import cn.com.suseng.pojo.wxrooms;
import cn.com.suseng.service.HotelInfoService;
import cn.com.suseng.service.IsMemberHttpService;
import cn.com.suseng.service.OrderService;
import cn.com.suseng.utils.SysMapper;
import cn.com.suseng.utils.SysResult;
import cn.com.suseng.utils.Distance;

@Controller
public class HotelInfoController {
	@Autowired
	private HotelInfoService hotelInfoService;
	@Autowired
	private IsMemberHttpService isMember;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	//根据前台提供的经纬度  查询附近酒店marker的信息www.suseng.com.cn/hotelmarkers
	@RequestMapping(value="/hotelmarkers",method=RequestMethod.GET)
	@ResponseBody
	public  SysResult queryHotelMarkers(Double latitude,Double longitude){
		
		List<wxMarker> markers = hotelInfoService.queryHotelMarkers(latitude,longitude);
		return SysResult.ok(markers);
		
	}
	
	//查询酒店房间信息    www.suseng.com.cn/hotelinfo？hotelid=***
	@RequestMapping(value="/hotelroominfo",method=RequestMethod.GET)
	@ResponseBody
	public SysResult queryRoomsInfo(String hotelid){
		List<wxrooms> rooms = hotelInfoService.queryRoomsInfo(hotelid);
		return SysResult.ok(rooms);
	}
	
	
	//查询两点间的距离
	@RequestMapping(value="/distance",method=RequestMethod.POST)
	@ResponseBody
	public  SysResult getDidstance(@RequestBody String json) throws JsonProcessingException, IOException{
		JsonNode tree = MAPPER.readTree(json);
		JsonNode startnode = tree.get("start");
		JsonNode endnode = tree.get("end");
		LatLng start = MAPPER.readValue(startnode.toString(), LatLng.class);
		LatLng end = MAPPER.readValue(endnode.toString(), LatLng.class);
		
		double distance = Distance.getDistance(start, end);
		long dis = Math.round(distance);
		return SysResult.ok(dis);
	}
	
	//查询酒店房间状态  用作预定www.suseng.com.cn/roomstatus
	@RequestMapping(value="/roomstatus",method=RequestMethod.GET)
	@ResponseBody
	public  SysResult getRoomStatus(String hotelid,String roomname){
		
		//realname需要做url解码
	
		String name=null;
		try {
			name = new String(roomname.getBytes("iso8859-1"), "utf-8").trim();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("name==="+name);
		List<roomstatus> roomstatus = hotelInfoService.getRoomStatus(hotelid,name);
		return SysResult.ok(roomstatus);
	}
	
	//查询用户输入的电话密码  核对是否是品牌酒店的会员www.suseng.com.cn/ismember  userName password
	//先不做这个操作  有点问题
	@RequestMapping(value="/ismember",method=RequestMethod.GET)
	@ResponseBody
	public SysResult ismembership(String userName,String password) throws Exception{
		Double dc = isMember.getmenberinfo(userName, password);
		return SysResult.ok(dc);
	}
	
	//这是测试git的性能和使用的
	
	@RequestMapping(value="/ismembers",method=RequestMethod.GET)
	@ResponseBody
	public SysResult ismembershipsornot(String userName,String password) throws Exception{
		Double dc = isMember.getmenberinfo(userName, password);
		return SysResult.ok(dc);
	}
}
