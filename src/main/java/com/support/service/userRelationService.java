package com.support.service;

import com.support.pojo.userRelation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @InterfaceName userRelationService
 * @Author 吴俊淇
 * @Date 2020/3/26 21:46
 * @Version 1.0
 **/
public interface userRelationService {
    List<userRelation> findByConcern(Integer concern);

    List<userRelation> findByConcerned(Integer concerned);

    userRelation findByConcernAndConcerned(Integer concern, Integer concerned);

    int deleteByConcernAndConcerned(Integer concern, Integer concerned);

    userRelation save(userRelation userRelation);
}
