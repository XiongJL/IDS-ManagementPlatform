package com.neu.rule.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.neu.rule.beans.CountCategoryModel;
import com.neu.rule.beans.CountProtocolModel;
import com.neu.rule.beans.CountRiskModel;
import com.neu.rule.beans.CountWeekModel;
import com.neu.rule.beans.IdsFindModel;
import com.neu.rule.beans.IdsRule;

//带有多个查询条件的话则需要继承JpaSpecificationExecutor接口
public interface RuleDao extends JpaRepository<IdsRule, Integer>, JpaSpecificationExecutor<IdsRule> {
	

	/*
	 * 模糊查询 此处用原生mySQL语句一直无法传递rule 参数
	 * 
	 * 只查询规则信息
	 */
	@Query(value = "SELECT new com.neu.rule.beans.IdsFindModel(r.nvid,r.ids_flag,r.rule1,r.rule2,r.rule3,r.rule4"
			+ ",r.rule5,r.rule6,r.rule7,r.rule8,r.rule9) FROM IdsRule r WHERE CONCAT(IFNULL(r.rule1,''),"
			+ "IFNULL(r.rule2,''),IFNULL(r.rule3,''),IFNULL(r.rule4,''),IFNULL(r.rule5,''),"
			+ "IFNULL(r.rule6,''),IFNULL(r.rule7,''),IFNULL(r.rule8,''),IFNULL(r.rule9,''))"
			+ "LIKE %:rule% and r.ids_flag=:ids_flag")
	Page<IdsFindModel> findByRule(@Param("rule") String rule, @Param("ids_flag") int ids_flag, Pageable pageable);

	// ,@Param("ids_flag")String ids_flag
	/* 查询全部信息 */
	@Query(value = "SELECT r from IdsRule r  WHERE CONCAT(IFNULL(rule1,''),"
			+ "IFNULL(r.rule2,''),IFNULL(r.rule3,''),IFNULL(r.rule4,''),IFNULL(r.rule5,''),"
			+ "IFNULL(r.rule6,''),IFNULL(r.rule7,''),IFNULL(r.rule8,''),IFNULL(r.rule9,''))" + "LIKE %?1%")
	Page<IdsRule> findByRule2(String rule, Pageable pageable);

	/* 查询所有flag类型的数据 */
	@Query(value = "SELECT new com.neu.rule.beans.IdsFindModel(r.nvid,r.ids_flag,r.rule1,r.rule2,r.rule3,r.rule4"
			+ ",r.rule5,r.rule6,r.rule7,r.rule8,r.rule9) FROM IdsRule r WHERE CONCAT(IFNULL(r.rule1,''),"
			+ "IFNULL(r.rule2,''),IFNULL(r.rule3,''),IFNULL(r.rule4,''),IFNULL(r.rule5,''),"
			+ "IFNULL(r.rule6,''),IFNULL(r.rule7,''),IFNULL(r.rule8,''),IFNULL(r.rule9,''))" + "LIKE %?1%")
	Page<IdsFindModel> findByRule3(String rule, Pageable pageable);

	/* 查询ids_flag = 1 and =2 的数据 */
	@Query(value = "SELECT new com.neu.rule.beans.IdsFindModel(r.nvid,r.ids_flag,r.rule1,r.rule2,r.rule3,r.rule4"
			+ ",r.rule5,r.rule6,r.rule7,r.rule8,r.rule9) FROM IdsRule r WHERE CONCAT(IFNULL(r.rule1,''),"
			+ "IFNULL(r.rule2,''),IFNULL(r.rule3,''),IFNULL(r.rule4,''),IFNULL(r.rule5,''),"
			+ "IFNULL(r.rule6,''),IFNULL(r.rule7,''),IFNULL(r.rule8,''),IFNULL(r.rule9,''))"
			+ "LIKE %:rule% and (r.ids_flag=:ids_flag or r.ids_flag=:flag)")
	Page<IdsFindModel> findByRule4(@Param("rule") String rule, @Param("ids_flag") int ids_flag, @Param("flag") int flag,
			Pageable pageable);

	/* 全文检索 ----目前是多字段模糊查询, 未来推荐使用Lucnen,或者ElasticSearch */
	@Query(value = "SELECT * FROM ids_rule WHERE CONCAT(IFNULL(rule1,''),IFNULL(rule2,''),IFNULL(rule3,''),"
			+ "IFNULL(rule4,''),IFNULL(rule5,''),IFNULL(rule6,''),IFNULL(rule7,''),IFNULL(rule8,''),IFNULL(rule9,''),"
			+ "IFNULL(cve_index,''),IFNULL(bugtraq_index,''),IFNULL(cnvd_index,''),IFNULL(another_index,''),"
			+ "IFNULL(cn_name,''),IFNULL(english_name,''),IFNULL(vul_date,''),IFNULL(publish_date,''),"
			+ "IFNULL(location,''),IFNULL(attack_target,''),IFNULL(category,''),IFNULL(risk,''),IFNULL(vul_status,''),"
			+ "IFNULL(en_des,''),IFNULL(cn_des,''),IFNULL(ni_test,''),IFNULL(patchen,''),IFNULL(patch,''),IFNULL(protocol,''),"
			+ "IFNULL(target_system,''),IFNULL(mome,'')) " + "LIKE %:rule%", nativeQuery = true)
	Page<IdsRule> findByFullSearch(@Param("rule") String rule, Pageable pageable);

	/*
	 * 查询 最近日期 的20条漏洞 select publish_date,cn_name,english_name from ids_rule
	 * GROUP BY publish_date DESC LIMIT 50 hql 语句 的排序是order by nativeQuery=true
	 * 设置为true时,可以使用原生SQL查询,但注意写出要查询的表中所有字段， 不管你是否需要该字段，全写,否则会报列找不到的错
	 */
	@Query(value = "select * from ids_rule   GROUP BY nvid DESC LIMIT 20", nativeQuery = true)
	List<IdsRule> findByPublish_date();
	//只显示ids_lfag 为 2 且在某个时间段的漏洞
	@Query(value="select * from ids_rule where publish_date >= :publish1 and publish_date <= :publish2 and ids_flag=2",nativeQuery=true)
	Page<IdsRule> findBetweenPublish_date(@Param("publish1")Date publish1,@Param("publish2")Date publish2,Pageable pageable);
	
	
	// /*
	// * 查询近9年的,每年的规则量
	// * sql 查询只能获取近20年的所有漏洞
	// */
	// @Query(value = "select *from ids_rule where
	// year(publish_date)>=YEAR(NOW())-19 ORDER BY publish_date DESC",
	// nativeQuery = true)
	// List<IdsRule> findByCountPublish_date();
	// 通过Nvid查询
	@Query("select r from IdsRule r where r.nvid=:nvid")
	IdsRule findByNvid(@Param("nvid") int nvid);

	// 查询今年的漏洞
	@Query(value = "select *from ids_rule where year(publish_date)=YEAR(NOW())", nativeQuery = true)
	List<IdsRule> findByNow();

	// 查询最后一条记录
	@Query(value = "select * from ids_rule  ORDER BY nvid DESC limit 1 ", nativeQuery = true)
	IdsRule findLastNvid();

	// 统计协议类型的计数
	@Query(value = "SELECT new com.neu.rule.beans.CountProtocolModel(r.protocol,count(r.protocol)) from IdsRule r GROUP BY r.protocol")
	List<CountProtocolModel> countByProtocol();

	// 统计漏洞原因或类型的计数
	@Query(value = "SELECT new com.neu.rule.beans.CountCategoryModel(r.category,count(r.category)) from IdsRule r GROUP BY r.category")
	List<CountCategoryModel> countByCategory();

	// 统计风险等级的计数
	@Query(value = "SELECT new com.neu.rule.beans.CountRiskModel(r.risk,count(r.risk)) from IdsRule r GROUP BY r.risk ORDER BY count(r.risk) desc")
	List<CountRiskModel> countByRisk();

	// 今日新增漏洞数量 ,以后可以专门用模型接受来优化
	@Query(value = "select * from ids_rule where to_days(publish_date) = to_days(now())", nativeQuery = true)
	List<IdsRule> todayTotal();

	// 查询最近8周的规则量
	// SELECT week(publish_date),COUNT(week(publish_date)) FROM ids_rule WHERE
	// DATEDIFF(publish_date,NOW())<=0 AND DATEDIFF(publish_date,NOW())>-56
	// GROUP
	// BY week(publish_date)
	@Query(value = "SELECT COUNT(week(publish_date)) "
			+ "FROM ids_rule WHERE DATEDIFF(publish_date,NOW())<=0 AND DATEDIFF(publish_date,NOW())>-56 "
			+ "GROUP BY week(publish_date)", nativeQuery = true)
	long[] countByWeek();
	
	//查找时间段内失效的nvid号
	@Query(value="select nvid from ids_rule where publish_date >= :publish1 and publish_date <= :publish2 and ids_flag=0",nativeQuery=true)
	long[] findNvidByPublish_date(@Param("publish1")Date publish1,@Param("publish2")Date publish2);
	
	//查找已发布的漏洞
	@Query(value="select nvid from ids_rule where ids_flag=1",nativeQuery=true)
	long[] countByIdsFlag();
	
	//查找已发布的漏洞的规则
	@Query(value="select * from ids_rule where ids_flag=1",nativeQuery=true)
	List<IdsRule> countByRules();
}
