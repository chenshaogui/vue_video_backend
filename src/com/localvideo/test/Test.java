package com.localvideo.test;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Order order=new Order();
		order.setId(11);
		order.setOrderName("dsd");
		Order order1=new Order();
		order1.setId(11);
		order1.setOrderName("dsd");
		List<Order> list=new ArrayList<>();
		list.add(order);
		list.add(order1);
		System.out.println(list.toString());
		System.out.println(JSONObject.toJSON(list).toString());*/
		String name="F:\\360Downloads\\SXT_60分钟零基础体验项目开发_1_java开发环境安装和配置.avi";
		//String uu=name.replaceAll("\\\\", "/");
		//uu=uu.replaceAll("\\\\", "/");
		//int a=uu.indexOf("/", 4);
		//uu=uu.substring(a);
		String HttpHead="http://127.0.0.1:8082/load";
		name=name.replaceAll("\\\\", "/");
		String Url=HttpHead+name.substring(name.indexOf("/", 4));
		System.out.println(Url);
		
	}

}
