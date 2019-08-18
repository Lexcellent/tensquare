package com.tensquare.recruit.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import util.IdWorker;

import com.tensquare.recruit.dao.EnterpriseDao;
import com.tensquare.recruit.pojo.Enterprise;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class EnterpriseService {

	@Autowired
	private EnterpriseDao EnterpriseDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Enterprise> findAll() {
		return EnterpriseDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Enterprise> findSearch(Map whereMap, int page, int size) {
		Specification<Enterprise> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return EnterpriseDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Enterprise> findSearch(Map whereMap) {
		Specification<Enterprise> specification = createSpecification(whereMap);
		return EnterpriseDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Enterprise findById(String id) {
		return EnterpriseDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param Enterprise
	 */
	public void add(Enterprise Enterprise) {
		Enterprise.setId( idWorker.nextId()+"" );
		EnterpriseDao.save(Enterprise);
	}

	/**
	 * 修改
	 * @param Enterprise
	 */
	public void update(Enterprise Enterprise) {
		EnterpriseDao.save(Enterprise);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		EnterpriseDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Enterprise> createSpecification(Map searchMap) {

		return new Specification<Enterprise>() {

			@Override
			public Predicate toPredicate(Root<Enterprise> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // 编号
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 活动名称
                if (searchMap.get("name")!=null && !"".equals(searchMap.get("name"))) {
                	predicateList.add(cb.like(root.get("name").as(String.class), "%"+(String)searchMap.get("name")+"%"));
                }
                // 大会简介
                if (searchMap.get("summary")!=null && !"".equals(searchMap.get("summary"))) {
                	predicateList.add(cb.like(root.get("summary").as(String.class), "%"+(String)searchMap.get("summary")+"%"));
                }
                // 开始时间
                if (searchMap.get("address")!=null && !"".equals(searchMap.get("address"))) {
                	predicateList.add(cb.like(root.get("address").as(String.class), "%"+(String)searchMap.get("address")+"%"));
                }
                // 详细说明
                if (searchMap.get("labels")!=null && !"".equals(searchMap.get("labels"))) {
                	predicateList.add(cb.like(root.get("labels").as(String.class), "%"+(String)searchMap.get("labels")+"%"));
                }
                // 主办方
                if (searchMap.get("coordinate")!=null && !"".equals(searchMap.get("coordinate"))) {
                	predicateList.add(cb.like(root.get("coordinate").as(String.class), "%"+(String)searchMap.get("coordinate")+"%"));
                }
                // 活动图片
                if (searchMap.get("ishot")!=null && !"".equals(searchMap.get("ishot"))) {
                	predicateList.add(cb.like(root.get("ishot").as(String.class), "%"+(String)searchMap.get("ishot")+"%"));
                }
                // 开始时间
                if (searchMap.get("logo")!=null && !"".equals(searchMap.get("logo"))) {
                	predicateList.add(cb.like(root.get("logo").as(String.class), "%"+(String)searchMap.get("logo")+"%"));
                }
                // 是否可见
                if (searchMap.get("url")!=null && !"".equals(searchMap.get("url"))) {
                	predicateList.add(cb.like(root.get("url").as(String.class), "%"+(String)searchMap.get("url")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}
