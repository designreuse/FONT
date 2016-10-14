package com.acms.domain;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class DataTablesHelper {

	
	//private List<Item> aoData;
	private String aoData;
	
	public DataTablesHelper(String aoData) {
		super();
		this.aoData = aoData;
	}

	
	public Parameter init(){
		Parameter e = new Parameter();
		JSONArray aoDataArr = JSONArray.parseArray(aoData);
		if(aoDataArr!=null){
			for(int i=0;i<aoDataArr.size();i++) //从传递参数里面选出待用的参数 
			{ 
				JSONObject obj=(JSONObject)aoDataArr.get(i); 
				String name = (String)obj.get("name"); 
				Object value = obj.get("value"); 
				if(name.equals("sEcho")){ 
					 e.setsEcho((int)value);
				} 
				if(name.equals("iDisplayStart")) { 
					e.setiDisplayStart((int)value);
				} 
				if(name.equals("iDisplayLength")) { 
					e.setiDisplayLength((int)value);
				} 
				if(name.startsWith("iSortCol_")){ 
					
					e.setiSortCol((int)value);//排序列数 
				} 
				if(name.startsWith("sSortDir_")){ 
					e.setsSortDir((String)value);//排序的方向 "desc" 或者 "asc". 
				} 

			} 
		}

		/*for(int i=0;i<aoData.size();i++) //从传递参数里面选出待用的参数 
		{ 
			Item item = aoData.get(i);
			if("sEcho".equals(item.getName())){ 
				 e.setsEcho((int)item.getValue());
			} 
			if("iDisplayStart".equals(item.getName())) { 
				e.setiDisplayStart((int)item.getValue());
			} 
			if("iDisplayLength".equals(item.getName())) { 
				e.setiDisplayLength((int)item.getValue());
			} 
			
			if(item.getName().startsWith("iSortCol_")){ 
				
				e.setiSortCol((int)item.getValue());//排序列数 
			} 
			if(item.getName().startsWith("sSortDir_")){ 
				e.setsSortDir((String)item.getValue());//排序的方向 "desc" 或者 "asc". 
			} 
		}*/
		return e;
	}
	
	public class Parameter{
		private int sEcho;
		private int iDisplayStart;
		private int iDisplayLength;
		
		
		//private List<HashMap<String,String>> sorts;
		private int iSortCol;
		private String sSortDir;
		public int getsEcho() {
			return sEcho;
		}
		public void setsEcho(int sEcho) {
			this.sEcho = sEcho;
		}
		public int getiDisplayStart() {
			return iDisplayStart;
		}
		public void setiDisplayStart(int iDisplayStart) {
			this.iDisplayStart = iDisplayStart;
		}
		public int getiDisplayLength() {
			return iDisplayLength;
		}
		public void setiDisplayLength(int iDisplayLength) {
			this.iDisplayLength = iDisplayLength;
		}
		
		public int getiSortCol() {
			return iSortCol;
		}
		public void setiSortCol(int iSortCol) {
			this.iSortCol = iSortCol;
		}
		public String getsSortDir() {
			return sSortDir;
		}
		public void setsSortDir(String sSortDir) {
			this.sSortDir = sSortDir;
		}
		
		
	}
}

class Item{
	private String name;
	private Object value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	
}
