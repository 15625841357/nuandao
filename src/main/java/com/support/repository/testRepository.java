package com.support.repository;

import com.support.pojo.test;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @InterfaceName testRepository
 * @Author 吴俊淇
 * @Date 2020/4/8 22:01
 * @Version 1.0
 **/
//@Async
public interface testRepository extends JpaRepository<test, Integer> {
}
