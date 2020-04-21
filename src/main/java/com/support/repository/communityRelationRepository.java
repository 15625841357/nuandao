package com.support.repository;

import com.support.pojo.communityRelation;
import io.swagger.models.auth.In;
import org.springframework.data.repository.CrudRepository;

/**
 * @InterfaceName communityRelationRepository
 * @Author 吴俊淇
 * @Date 2020/3/25 18:46
 * @Version 1.0
 **/

public interface communityRelationRepository extends CrudRepository<communityRelation, Integer> {
    communityRelation findByUserId(Integer userId);

    communityRelation findBycommunityIdAndUserId(Integer communityId, Integer userId);
}
