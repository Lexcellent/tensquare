package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;
    /**
     * 查询所有数据
     * @return
     */
    public List<Spit> findAll(){
        return spitDao.findAll();
    }

    /**
     * 根据 id 查询数据
     * @param id
     * @return
     */
    public Spit findById(String id){
        return spitDao.findById(id).get();
    }

    /**
     * 保存数据
     * @param spit
     */
    public void save(Spit spit){
        spit.set_id(idWorker.nextId()+"");
        spit.setPublishtime(new Date());//发布日
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1"); //状态
        //如果当前添加的吐槽，有父节点，那么父节点的吐槽回复数要加一
        if(spit.getParentid()!=null && !"".equals(spit.getParentid())){
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }
        spitDao.save(spit);
    }

    /**
     * 修改数据
     * @param spit
     */
    public void update(Spit spit){
        spitDao.save(spit);
    }

    /**
     * 根据 id 号删除数据
     * @param id
     */
    public void deleteById(String id){
        spitDao.deleteById(id);
    }

    /**
     * 分页查询
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    public Page<Spit> findByParentid(String parentid,int page,int size){
        Pageable pageable = PageRequest.of(page-1,size);
        return spitDao.findByParentid(parentid,pageable);
    }

    /**
     * 吐槽点赞
     * @param spitId
     */
    public void thumbup(String spitId) {

        //方式一：效率有问题
        /*Spit spit = spitDao.findById(spitId).get();
        spit.setThumbup((spit.getThumbup()==null ? 0 : spit.getThumbup())+1);
        spitDao.save(spit);*/

        //方式二：使用 MongoDB 命令实现
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is("1"));
        Update update = new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }
}
