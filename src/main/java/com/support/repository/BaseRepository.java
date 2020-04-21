package com.support.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @InterfaceName BaseRepository
 * @Author 吴俊淇
 * @Date 2020/4/5 12:30
 * @Version 1.0
 **/


@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>{

    //sql原生查询
    List<Map<String, Object>> listBySQL(String sql);
}