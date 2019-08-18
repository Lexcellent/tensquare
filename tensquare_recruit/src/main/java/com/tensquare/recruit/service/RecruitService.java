package com.tensquare.recruit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tensquare.recruit.dao.RecruitDao;
import com.tensquare.recruit.pojo.Recruit;

import util.IdWorker;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class RecruitService {

	@Autowired
	private RecruitDao gatheringDao;

	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * 
	 * @return
	 */
	public List<Recruit> findAll() {
		return gatheringDao.findAll();
	}

	/**
	 * 条件查询+分页
	 * 
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Recruit> findSearch(Map whereMap, int page, int size) {
		Specification<Recruit> specification = createSpecification(whereMap);
		PageRequest pageRequest = PageRequest.of(page - 1, size);
		return gatheringDao.findAll(specification, pageRequest);
	}

	/**
	 * 条件查询
	 * 
	 * @param whereMap
	 * @return
	 */
	public List<Recruit> findSearch(Map whereMap) {
		Specification<Recruit> specification = createSpecification(whereMap);
		return gatheringDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * 
	 * @param id
	 * @return
	 */
	public Recruit findById(String id) {
		return gatheringDao.findById(id).get();
	}

	/**
	 * 增加
	 * 
	 * @param gathering
	 */
	public void add(Recruit gathering) {
		gathering.setId(idWorker.nextId() + "");
		gatheringDao.save(gathering);
	}

	/**
	 * 修改
	 * 
	 * @param gathering
	 */
	public void update(Recruit gathering) {
		gatheringDao.save(gathering);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void deleteById(String id) {
		gatheringDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * 
	 * @param searchMap
	 * @return
	 */
	private Specification<Recruit> createSpecification(Map searchMap) {

		return new Specification<Recruit>() {

			@Override
			public Predicate toPredicate(Root<Recruit> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				// 编号
				if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
					predicateList
							.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
				}
				// 活动名称
				if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
					predicateList.add(
							cb.like(root.get("name").as(String.class), "%" + (String) searchMap.get("name") + "%"));
				}
				// 大会简介
				if (searchMap.get("summary") != null && !"".equals(searchMap.get("summary"))) {
					predicateList.add(cb.like(root.get("summary").as(String.class),
							"%" + (String) searchMap.get("summary") + "%"));
				}
				// 详细说明
				if (searchMap.get("detail") != null && !"".equals(searchMap.get("detail"))) {
					predicateList.add(
							cb.like(root.get("detail").as(String.class), "%" + (String) searchMap.get("detail") + "%"));
				}
				// 主办方
				if (searchMap.get("sponsor") != null && !"".equals(searchMap.get("sponsor"))) {
					predicateList.add(cb.like(root.get("sponsor").as(String.class),
							"%" + (String) searchMap.get("sponsor") + "%"));
				}
				// 活动图片
				if (searchMap.get("image") != null && !"".equals(searchMap.get("image"))) {
					predicateList.add(
							cb.like(root.get("image").as(String.class), "%" + (String) searchMap.get("image") + "%"));
				}
				// 举办地点
				if (searchMap.get("address") != null && !"".equals(searchMap.get("address"))) {
					predicateList.add(cb.like(root.get("address").as(String.class),
							"%" + (String) searchMap.get("address") + "%"));
				}
				// 是否可见
				if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
					predicateList.add(
							cb.like(root.get("state").as(String.class), "%" + (String) searchMap.get("state") + "%"));
				}
				// 城市
				if (searchMap.get("city") != null && !"".equals(searchMap.get("city"))) {
					predicateList.add(
							cb.like(root.get("city").as(String.class), "%" + (String) searchMap.get("city") + "%"));
				}

				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}