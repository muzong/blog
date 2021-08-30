package com.blog.dao.mongo;

import com.blog.model.entity.SqlLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

/**
 * SqlLogDAO
 *
 * @author zzx
 * @date 2021.5.29 16:14
 */
@Component
public class SqlLogDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建对象
     */
    public void saveSqlLog(SqlLog log) {
        mongoTemplate.save(log);
    }


    /**
     * 查询在此时间之后的记录
     * @return
     */
    public List<SqlLog> findNewestLog(Date left, Date right, int size) {
        Criteria condition = new Criteria();
        if (!ObjectUtils.isEmpty(left) || !ObjectUtils.isEmpty(right)) {
            condition = condition.and("date");
            if (!ObjectUtils.isEmpty(left)) {
                condition.gt(left);
            }
            if (!ObjectUtils.isEmpty(right)) {
                condition.lte(right);
            }
        }

        Query query = new Query(condition).limit(size)
                .with(Sort.by(Sort.Order.desc("date")));
        return mongoTemplate.find(query, SqlLog.class);
    }


}


