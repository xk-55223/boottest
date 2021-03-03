package com.keith.test.boottest;

import com.alibaba.fastjson.JSON;
import com.keith.test.boottest.entity.ShopProductCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * mongoDb测试类
 *
 * @author keith
 * @since 2021-02-02
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MongoDbTest {
    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void mongoTest() {
        ShopProductCollection shopProductCollection = new ShopProductCollection();
        shopProductCollection.setId("cn123456");
        shopProductCollection.setCreateTime(System.currentTimeMillis());
        shopProductCollection.setPageSize(10);
        shopProductCollection.setShopId("123456");
        shopProductCollection.setTotalPage(10);
        shopProductCollection.setTotalRow(10);
        shopProductCollection.setUpdateTime(System.currentTimeMillis());
        mongoTemplate.save(shopProductCollection);
        Query query = new Query(Criteria.where("_id").is("cn123456"));
        ShopProductCollection one = mongoTemplate.findOne(query, ShopProductCollection.class);
        System.out.println(JSON.toJSONString(one));
    }
}
