package cn.com.suseng.utils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.mysql.fabric.xmlrpc.base.Array;

import cn.com.suseng.pojo.Marker;
import cn.com.suseng.pojo.rooms;
import cn.com.suseng.pojo.wxMarker;
import cn.com.suseng.pojo.wxrooms;

public class FormateChangeHotel {
	public static wxrooms changeRoomInfo(rooms rs){
		
		wxrooms room = new wxrooms();
		room.setHotelid(rs.getHotelid());
		room.setBedtype(rs.getBedtype());
		room.setPrice(rs.getPrice());
		room.setRoomarea(rs.getRoomarea());
		String[] paths = rs.getRoomimg().split(";");
		LinkedList<String> imglist=new LinkedList<String>();
		for (String string : paths) {
			imglist.add(string);
		}
		room.setRoomimg(imglist);
		room.setWindow(rs.getWindow());
		room.setRoomname(rs.getRoomname());
		
		return room;
		
	}

	public static wxMarker changeMarkInfo(Marker marker) {
		wxMarker wxmk = new wxMarker();
		wxmk.setHotelid(marker.getHotelid());
		wxmk.setHoteladd(marker.getHoteladd());
		String[] paths = marker.getHotelimg().split(";");
		LinkedList<String> imglist = new LinkedList<String>();
		for (String string : paths) {
			imglist.add(string);
		}
		wxmk.setHotelimg(imglist);
		wxmk.setHotelname(marker.getHotelname());
		wxmk.setHoteltel(marker.getHoteltel());
		wxmk.setId(marker.getId());
		wxmk.setLatitude(marker.getLatitude());
		wxmk.setLongitude(marker.getLongitude());
		return null;
	}
}
