package com.localvideo.dev.service;

import java.rmi.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.localvideo.dev.util.MongoDBUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoException;


public class MongoDBService {
	
	private String dbName;
	private String collName;
	private DB db;
	private static Mongo mongo;
    private static MongoClient client;
	
	@Resource
	MongoTemplate mongoTemplate;
	//有参构造方法，指定数据库名与集合名
    public MongoDBService(String dbName, String collName) {
        this.dbName = dbName;
        this.collName = collName;
        try {
            db = getDb();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    //无参构造方法，返回配置文件配置的数据库对象引用，如果配置文件中没有设置则返回默认数据库对象引用
    public MongoDBService() {
        getDb();
    }
    /*
     * 获取数据库对象，3种情况（优先级从高到低）：
<span style="white-space: pre"> </span> *1、构造方法指定2、配置文件指定3、默认数据库
<span style="white-space: pre"> </span> *（情况2、3在MongoDButil中设置）
     */
    @SuppressWarnings("deprecation")
	public DB getDb() {
        if (this.db == null) {
            if (this.dbName == null) {
            	this.db=mongoTemplate.getDb();
                //this.db = MongoDBUtil.getDB();
            } else {
            	MongoClientOptions.Builder build = new MongoClientOptions.Builder();        
                build.connectionsPerHost(50);   //与目标数据库能够建立的最大connection数量为50
                build.threadsAllowedToBlockForConnectionMultiplier(50); //如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待
                /*
                 * 一个线程访问数据库的时候，在成功获取到一个可用数据库连接之前的最长等待时间为2分钟
                 * 这里比较危险，如果超过maxWaitTime都没有获取到这个连接的话，该线程就会抛出Exception
                 * 故这里设置的maxWaitTime应该足够大，以免由于排队线程过多造成的数据库访问失败
                 */
                build.maxWaitTime(1000*60*2);
                build.connectTimeout(1000*60*1);    //与数据库建立连接的timeout设置为1分钟

                MongoClientOptions myOptions = build.build();
                try {
                    //数据库连接实例
                    client = new MongoClient("127.0.0.1", myOptions);
                    this.db=client.getDB(dbName);
                } catch (MongoException e){
                    e.printStackTrace();
                }
            	
                //this.db = MongoDBUtil.getDBByName(this.dbName);
            }
        }
        return this.db;
    }
     
    /*
     * 获取集合对象，3种情况（优先级从高到低）：
         *1、构造方法指定2、配置文件指定3、默认数据库
     *（情况2、3在MongoDButil中设置）
     */
    public DBCollection getCollection(String collName) {
        /*if(this.collName != null){
            return db.getCollection(this.collName);
        }
        else {
        	this.collName=collName;
            return mongoTemplate.getCollection(collName);
        }*/
        this.collName=collName;
        return mongoTemplate.getCollection(collName);
        
    }
    public DBObject map2Obj(Map<String, Object> map) {
        DBObject obj = new BasicDBObject();
        if (map.containsKey("class") && map.get("class") instanceof Class)
            map.remove("class");
        obj.putAll(map);
        return obj;
    }
    //插入数据
    public void insert(DBObject obj) {
        getCollection(this.collName).insert(obj);
    }
    //插入多条数据
    public void insertBatch(List<DBObject> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        List<DBObject> listDB = new ArrayList<DBObject>();
        for (int i = 0; i < list.size(); i++) {
            listDB.add(list.get(i));
        }
        getCollection(this.collName).insert(listDB);
    }
    //删除数据
    public void delete(DBObject obj) {
        getCollection(this.collName).remove(obj);
    }
    //删除多条数据
    public void deleteBatch(List<DBObject> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            getCollection(this.collName).remove(list.get(i));
        }
    }
    //获取集合中的数据数量
    public long getCollectionCount() {
        return getCollection(this.collName).getCount();
    }
    //查找符合条件的数据数量
    public long getCount(DBObject obj) {
        if (obj != null)
            return getCollection(this.collName).getCount(obj);
        return getCollectionCount();
    }
    //查找符合条件的数据
    public List<DBObject> find(DBObject obj) {
        DBCursor cur = getCollection(this.collName).find(obj);
        return DBCursor2list(cur);
    }
     
    //查找符合条件的数据并排序
    public List<DBObject> find(DBObject query, DBObject sort) {
        DBCursor cur;
        if (query != null) {
            cur = getCollection(this.collName).find(query);
        } else {
            cur = getCollection(this.collName).find();
        }
        if (sort != null) {
            cur.sort(sort);
        }
        return DBCursor2list(cur);
    }
 
    //查找符合条件的数据并排序，规定数据个数
    public List<DBObject> find(DBObject query, DBObject sort, int start,
            int limit) {
        DBCursor cur;
        if (query != null) {
            cur = getCollection(this.collName).find(query);
        } else {
            cur = getCollection(this.collName).find();
        }
        if (sort != null) {
            cur.sort(sort);
        }
        if (start == 0) {
            cur.batchSize(limit);
        } else {
            cur.skip(start).limit(limit);
        }
        return DBCursor2list(cur);
    }
     
    //将DBCursor转化为list<dbobject>
    private List<DBObject> DBCursor2list(DBCursor cur) {
        List<DBObject> list = new ArrayList<DBObject>();
        if (cur != null) {
            list = cur.toArray();
        }
        return list;
    }
 
    //更新数据
    public void update(DBObject setFields, DBObject whereFields) {
        getCollection(this.collName).updateMulti(whereFields, setFields);
    }
    //查询集合中所有数据
    public List<DBObject> findAll() {
        DBCursor cur = getCollection(this.collName).find();
        List<DBObject> list = new ArrayList<DBObject>();
        if (cur != null) {
            list = cur.toArray();
        }
        return list;
    }
 
    //由ID获取数据
//    public DBObject getById(String id) {
//        DBObject obj = new BasicDBObject();
//        obj.put("_id", new ObjectId(id));
//        DBObject result = getCollection().findOne(obj);
//        return result;
//    }
 
    public String getDbName() {
        return dbName;
    }
 
    public void setDbName(String dbName) {
        this.dbName = dbName;
        this.db = MongoDBUtil.getDBByName(this.dbName);
    }
 
    public String getCollName() {
        return collName;
    }
 
    public void setCollName(String collName) {
        this.collName = collName;
    }
    public void printListDBObj(List<DBObject> list) {
        // TODO Auto-generated method stub
        for(DBObject dbObject: list){
            System.out.println(dbObject);
        }
    }
 

}
