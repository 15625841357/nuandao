package com.support.repository;

import com.support.pojo.trends;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @InterfaceName trendsRepository
 * @Author 吴俊淇
 * @Date 2020/3/28 0:25
 * @Version 1.0
 **/
public interface trendsRepository extends CrudRepository<trends, Integer> {
    List<trends> findByUserIdOrderByDateDesc(Integer userId);

    @Query(value = "SELECT * from trends ORDER BY date desc",nativeQuery = true)
    List<trends> findAllByDateDesc();

    int deleteByUserIdAndTrendsId(Integer userId,Integer trendsId);
}
