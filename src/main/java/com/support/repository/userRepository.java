package com.support.repository;

import com.support.pojo.user;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Map;

/**
 * @InterfaceName userRepository
 * @Author 吴俊淇
 * @Date 2020/3/21 14:10
 * @Version 1.0
 **/
//, JpaSpecificationExecutor<user>
public interface userRepository extends CrudRepository<user, Integer> {

    @Modifying
    @Query(value = "update user_info set name=:#{#u.name}, age=:#{#u.age},address=:#{#u.address},sex=:#{#u.sex},phone_number=:#{#u.phoneNumber}," +
            "nick_name=:#{#u.nickName} where id=:#{#u.id}", nativeQuery = true)
    int updateById(@Param("u") user u);

    user findByOpenId(String openid);

    @Query(value = "select id,name,age,address,sex,phone_number,longitude,latitude,nick_name,photo,last_login from user_info where id=?1", nativeQuery = true)
    Map<String, Object> findByIdNoOpenidAndRoleAndSecretKey(Integer userId);

    @Modifying
    @Query(value = "update user_info set photo=?1 where id=?2", nativeQuery = true)
    int updatePhotoByUserId(String photo, Integer userId);

    @Modifying
    @Query(value = "update user_info set longitude=?1, latitude=?2 where id=?3", nativeQuery = true)
    void updateLongitudeAndLatitudeByUserId(String longitude, String latitude, Integer userId);

    @Modifying
    @Query(value = "update user_info set last_login=:#{#u.lastLogin} where id=:#{#u.id}", nativeQuery = true)
    int updateLastLogin(@Param("u") user u);
}
