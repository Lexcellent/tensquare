package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LabelService {

	@Autowired
	private LabelDao labelDao;

	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询所有数据
	 * 
	 * @return
	 */
	public List<Label> findAll() {

		return labelDao.findAll();
	}

	/**
	 * 根据id 查询
	 * 
	 * @param id
	 * @return
	 */
	public Label findById(String id) {
		return labelDao.findById(id).get();
	}

	/**
	 * 保存（添加）数据
	 * 
	 * @param label
	 */
	public void save(Label label) {
		label.setId(idWorker.nextId() + "");
		labelDao.save(label);
	}

	/**
	 * 更新数据
	 * 
	 * @param label
	 */
	public void update(Label label) {
		labelDao.save(label);
	}

	/**
	 * 删除数据
	 * 
	 * @param id
	 */
	public void deleteById(String id) {
		labelDao.deleteById(id);
	}

	/**
	 * 查询数据
	 * 
	 * @param label
	 * @return
	 */
	public List<Label> findSearch(Label label) {
		return labelDao.findAll(new Specification<Label>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			/**
			 *
			 * @param root  根对象，也就是要把条件封装到哪个对象中。 where 类名 = label.getid
			 * @param query 封装的都是查询的关键字，比如 group by order by 等
			 * @param cb    用来封装条件对象的，如果直接返回null,表示不需要任何条件
			 * @return
			 */
			@Override
			public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// new 一个 list 集合，来存放所有的条件
				List<Predicate> list = new ArrayList<>();

				// 按照姓名查询
				if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
					Predicate predicate = cb.like(root.get("labelname").as(String.class),
							"%" + label.getLabelname() + "%");// where labelname like "%小明%"
					list.add(predicate);
				}

				// 按照状态查询
				if (label.getState() != null && !"".equals(label.getState())) {
					Predicate predicate = cb.equal(root.get("state").as(String.class), label.getState());// status = "1"
					list.add(predicate);
				}

				// new 一个数组作为最终返回的条件
				Predicate[] parr = new Predicate[list.size()];
				// 把 list 直接转成数组
				parr = list.toArray(parr);
				return cb.and(parr); // where labelname like "%小明%" and status = "1"
			}
		});

	}

	/**
	 * 查询数据 + 分页
	 * 
	 * @param label
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Label> pageQuery(Label label, int page, int size) {

		// 封装一个分页对象
		Pageable pageable = PageRequest.of(page - 1, size);
		return labelDao.findAll(new Specification<Label>() {
			/**
			 *
			 * @param root  根对象，也就是要把条件封装到哪个对象中。 where 类名 = label.getid
			 * @param query 封装的都是查询的关键字，比如 group by order by 等
			 * @param cb    用来封装条件对象的，如果直接返回null,表示不需要任何条件
			 * @return
			 */
			@Override
			public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// new 一个 list 集合，来存放所有的条件
				List<Predicate> list = new ArrayList<>();

				// 按照姓名查询
				if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
					Predicate predicate = cb.like(root.get("labelname").as(String.class),
							"%" + label.getLabelname() + "%");// where labelname like "%小明%"
					list.add(predicate);
				}

				// 按照状态查询
				if (label.getState() != null && !"".equals(label.getState())) {
					Predicate predicate = cb.equal(root.get("state").as(String.class), label.getState());// status = "1"
					list.add(predicate);
				}

				// new 一个数组作为最终返回的条件
				Predicate[] parr = new Predicate[list.size()];
				// 把 list 直接转成数组
				parr = list.toArray(parr);
				return cb.and(parr); // where labelname like "%小明%" and status = "1"
			}
		}, pageable);
	}
}
