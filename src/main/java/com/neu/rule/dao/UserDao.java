package com.neu.rule.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.neu.rule.beans.User;
/*。第一个泛型是写实体类的类型，这里是Person；第二个泛型是主键的类型，这里是Integer。*/
public interface UserDao extends JpaRepository<User, Integer>{
	//hql 语句查询 from后的是 类名,而不是表名字
	//这里通过列名查找,可以不写查询语句,只要命名规范
//	@Query("select u from User u where u.user= ?1")
	User findByUser(String userName);
}
