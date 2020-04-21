package com.support.repository;

import com.support.pojo.signIn;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @InterfaceName signInRepository
 * @Author 吴俊淇
 * @Date 2020/3/27 14:52
 * @Version 1.0
 **/
public interface signInRepository extends CrudRepository<signIn, Integer> {
    signIn findByUserId(Integer userId);

    @Modifying
    @Query(value = "update sign_in set text=:#{#s.text}, integral=:#{#s.integral} where user_id=:#{#s.userId}", nativeQuery = true)
    int updateTextByuserId(@Param("s") signIn s);
}
