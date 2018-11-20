package com.localvideo.dev.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.localvideo.dev.util.Page;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Service
public class VideoHelpService {
	
	@Resource
	MongoTemplate mongoTemplate;
	
	private static String Http="http://192.168.0.113:8082/load";
	
	private static String Https="http://192.168.0.113:8082/ower";
	
	
	/**
	 * 本地分页取数据
	 * @param page
	 * @return
	 */
	public List<DBObject> findByPage(Page page,String value) {
		DBObject dbObject = new BasicDBObject();
		//dbObject.put("teacher", "Mr.wang");
		DBObject query = new BasicDBObject();
		if(!StringUtils.isEmpty(value)) {
			Pattern pattern = Pattern.compile("^.*" + value +".*$", Pattern.CASE_INSENSITIVE);
			query.put("name", pattern);
		}
		
		DBObject sortDBObject=new BasicDBObject();  
		   sortDBObject.put("_id",1);
		DBCursor result = mongoTemplate.getCollection("ower_video")
		    .find(query)
		    .sort(sortDBObject)
		    .skip(page.getPageSize()*(page.getPageNow()-1)).limit(page.getPageSize());
		page.setTotalCount(result.count());
		//System.out.println(result.count());
		//System.out.println(result.toArray());
		List<DBObject> videoList=result.toArray();
		for (DBObject db : videoList) {
			if(db.get("videoUrl")!=null &&db.get("videoUrl")!="" ) {
				String video=db.get("videoUrl").toString().replaceAll("\\\\", "/");
				String videoUrl=Https+video.substring(video.indexOf("/", 4));
				db.put("videoUrl", videoUrl);
			}
			if(db.get("img")!=null &&db.get("img")!="" ) {
				String image=db.get("img").toString().replaceAll("\\\\", "/");
				String imageUrl=Https+image.substring(image.indexOf("/", 4));
				db.put("img", imageUrl);
				db.put("baseImg", "http://192.168.0.113:8082/video/static/img/baseimg.jpg");
			}
			
		}
		return videoList;
	}
	/**
	 * 98网络分页取数据
	 * @param page
	 * @return
	 */
	public List<DBObject> findNetByPage(Page page,String value) {
		//DBObject dbObject = new BasicDBObject();
		//dbObject.put("teacher", "Mr.wang");
		DBObject query = new BasicDBObject();
		if(!StringUtils.isEmpty(value)) {
			Pattern pattern = Pattern.compile("^.*" + value +".*$", Pattern.CASE_INSENSITIVE);
			query.put("name", pattern);
		}
		
		DBObject sortDBObject=new BasicDBObject();  
		   sortDBObject.put("_id",1);
		DBCursor result = mongoTemplate.getCollection("nets_video")
		    .find(query)
		    .sort(sortDBObject)
		    .skip(page.getPageSize()*(page.getPageNow()-1)).limit(page.getPageSize());
		page.setTotalCount(result.count());
		List<DBObject> videoList=result.toArray();
		return videoList;
	}
	
	/**
	 * 根据集合查询所有数据
	 * @return
	 */
	public List<DBObject> findall() {
		DBCursor result = mongoTemplate.getCollection("video_collection").find();
		List<DBObject> videoList=result.toArray();
		for (DBObject db : videoList) {
			String video=db.get("videoUrl").toString().replaceAll("\\\\", "/");
			String videoUrl=Http+video.substring(video.indexOf("/", 4));
			db.put("videoUrl", videoUrl);
			
			String image=db.get("img").toString().replaceAll("\\\\", "/");
			String imageUrl=Http+image.substring(image.indexOf("/", 4));
			db.put("img", imageUrl);
			
		}
		return videoList;
	}
	
	public Long getCollectionSize() {
		long result = mongoTemplate.getCollection("video_collection").getCount();
		return result;
	}
	
	
	public List<DBObject> list(Page page) {
		DBObject dbObject = new BasicDBObject();
		//dbObject.put("teacher", "Mr.wang");
		DBObject fieldObject = new BasicDBObject();
		fieldObject.put("_id", false);
		//fieldObject.put("_class", false);
		DBObject sortDBObject=new BasicDBObject();  
		   sortDBObject.put("classId",1);
		DBCursor result = mongoTemplate.getCollection("video_collection")
		    .find(dbObject, fieldObject)
		    .sort(sortDBObject)
		    .skip(page.getPageSize()*(page.getPageNow()-1)).limit(page.getPageSize());
		System.out.println(result.count());
		System.out.println(result.toArray());
		List<DBObject> videoList=result.toArray();
		return videoList;
		
	}
	
}
