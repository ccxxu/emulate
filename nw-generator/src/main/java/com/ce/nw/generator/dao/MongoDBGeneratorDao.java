package com.ce.nw.generator.dao;

import com.ce.nw.generator.factory.MongoDBCollectionFactory;
import com.ce.nw.generator.adaptor.MongoTableInfoAdaptor;
import com.ce.nw.generator.config.MongoCondition;
import com.ce.nw.generator.config.MongoManager;
import com.ce.nw.generator.entity.mongo.MongoDefinition;

import com.ce.nw.generator.utils.MongoScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: gxz gongxuanzhang@foxmail.com
 **/
@Repository
@Conditional(MongoCondition.class)
public class MongoDBGeneratorDao implements GeneratorDao {

    @Autowired
    private MongoDBCollectionFactory mongoDBCollectionFactory;

    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> map) {
        List<String> collectionNames = MongoDBCollectionFactory.getCollectionNames(map);
        return (List) MongoTableInfoAdaptor.tableInfo(collectionNames);
    }

    @Override
    public Map<String, String> queryTable(String tableName) {
        Map<String, String> result = new HashMap<>(4 * 4 / 3 + 1);
        result.put("engine", "");
        result.put("createTime", "");
        result.put("tableComment", "mongoDB " + tableName);
        result.put("tableName", tableName);
        return result;

    }

    @Override
    public List<Map<String, String>> queryColumns(String tableName) {
        MongoDefinition mongoDefinition = MongoManager.getInfo(tableName);
        if (mongoDefinition == null) {
            System.out.println(tableName);
            MongoScanner mongoScanner = new MongoScanner(mongoDBCollectionFactory.getCollection(tableName));
            mongoDefinition = mongoScanner.getProduct();
        }
        return MongoTableInfoAdaptor.columnInfo(mongoDefinition);
    }


}
