package com.support.controller;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.support.pojo.*;
import com.support.repository.userRelationRepository;
import com.support.service.*;
import com.support.test.testService;
import com.support.utils.Http;
import com.support.utils.RedisUtil;
import com.support.utils.WeChatUtils;
import com.support.utils.iflytekUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName userController
 * @Author 吴俊淇
 * @Date 2020/3/21 14:18
 * @Version 1.0
 **/
@RestController
@RequestMapping("/user")
@Transactional
@Slf4j
@Api(value = "用户信息查询", tags = {"用户信息的controller"})
public class userController {
    @Autowired
    private userRelationRepository userRelationRepository;
    @Autowired
    private testService testService;
    @Autowired
    private com.support.test.TaskTest TaskTest;
    @Autowired
    private community_applicationService community_applicationService;
    @Autowired
    private communityService communityService;
    @Autowired
    private photoService photoService;
    @Autowired
    private commentService commentService;
    @Autowired
    private trendsService trendsService;
    @Autowired
    private likeCountService likeCountService;
    @Autowired
    private signInService signInService;
    @Autowired
    private userRelationService userRelationService;
    @Autowired
    private userService userService;
    @Autowired
    private friendTokenService friendTokenService;
    @Autowired
    private community_serviceService community_serviceService;
    @Autowired
    private communityRelationService communityRelationService;
    @Resource
    private RedisUtil redisUtil;
    //    private Gson gson = new Gson();
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private Gson egson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation().create();

    /**
     * 判断老人是否有关联社区，如果关联多个社区怎么办
     *
     * @param code
     * @return 注册兼登录, 缓存用户id，返回随机命名登录态
     */
    @ApiOperation(value = "用户注册", notes = "用户注册")
    @ApiImplicitParam(name = "code", value = "code", dataType = "string", paramType = "query")
    @PostMapping("/register_login")
    public Object register_login(@RequestParam("code") String code, HttpServletResponse resp) throws Exception {
        final long ExpireTime = 604800L;
        //在此，如果user有一个是null，那么数据库的数据将会被修改为null，故要修改或者更新某个数据的话得要自己写一个方法！！！
        //如果想要用save的话，就必须要依据主键
        String body = WeChatUtils.getWXMessage(code);
        if (body.equals("fail") || body.contains("errcode")) {
            resp.setStatus(500);
            return body;
        }
        String Openid = gson.fromJson(body, WeChat.class).getOpenid();
        user user = userService.findByOpenId(Openid);
        int i;
        if (StringUtils.isEmpty(user)) {//null 没有该用户
            user u = new user();
            u.setOpenId(Openid);
            u.setPhoto("1");
            u.setRole("ROLE_USER");
            i = userService.save(u).getId();//保存之后，获取id
            user = userService.findById(i).get();//获取该用户信息
        } else {//have 有该用户
            i = user.getId();
        }
        //将登录态缓存
        String s = RandomStringUtils.randomAlphanumeric(9).toLowerCase();
        long j = redisUtil.incr("encryption:Increase", 1);
        redisUtil.set("encryption:" + s + "-" + j, i, ExpireTime);
        new Http(resp).setEncryption(resp, s, j);


        List<Object> objects = new ArrayList<>();
        objects.add(user);
        communityRelation communityRelation = communityRelationService.findByUserId(i);
        if (StringUtils.isEmpty(communityRelation)) {//没有有,则表示没关联社区
            objects.add(ImmutableMap.of("communityId", ""));
        } else {//有,则表示已关联社区
            objects.add(ImmutableMap.of("communityId", communityRelation.getCommunityId()));//有值
        }
        return egson.toJson(objects);
    }

    /**
     * @return 返回登录态
     */
    @PostMapping("/check")
    public String check() {
        return gson.toJson(ImmutableMap.of("login", "true"));
    }


    @PostMapping("/relation")
    @ApiOperation(value = "用户A关联用户B", notes = "用户A关联用户B")
    public String relation(@RequestParam("concern") Integer concern, @RequestParam("concerned") Integer concerned, @RequestParam("RelationName") String RelationName) {
        System.out.println(concern);
        System.out.println(concerned);
        if (userService.findById(concerned).isPresent()) {//表示有这个用户
            return Optional.ofNullable(userRelationService.findByConcernAndConcerned(concern, concerned)).map(u -> {
                //有，则代表存在，故不能继续添加好友，原因是已经为好友
                System.out.println(u);
                return "already";
            }).orElseGet(() -> {
                //没有，则代表不存在，故可以添加好友
                friendToken f = friendTokenService.findByConcernAndConcerned(concern, concerned);
                if (StringUtils.isEmpty(f)) {//null
                    redisUtil.set("userRelation:userRelation-" + concern + "-" + concerned, new userRelation(concern, concerned, RelationName));//写进缓存
                    friendTokenService.save(new friendToken(concern, concerned, 1));//将concern和concernid写进去表
                } else {
                    Integer i = f.getBFollow();
                    if (i >= 1) {
                        i++;//代表第N次添加
                    }
                    friendTokenService.save(new friendToken(concern, concerned, i));//将concern和concernid写进去表
                }
                return "success";
            });
        } else {//没有该用户
            return "false";
        }

    }

    @PostMapping("/checkrelation")
    @ApiOperation(value = "检查有没有人加自己", notes = "检查有没有人加自己")
    public String checkrelation(@RequestParam("concerned") Integer concerned) {
        return Optional.ofNullable(friendTokenService.findByConcerned(concerned)).map(f -> {  //有值  list集合
            List<Object> objects = new ArrayList<>();
            f.forEach(i -> {
                Map<String, Object> map = ImmutableMap.of("bFollw", i.getBFollow(), "user", userService.findById(i.getConcern()).get());
                objects.add(map);
            });
            return egson.toJson(objects);
        }).orElse(null);
    }


    /**
     * B用户是否同意
     *
     * @return 缺陷是一旦有完整的参数，则通过这个接口就可以实现添加好友功能，但是一旦点击之后就没这个缓存，故有人想要破解的话，需要触发上面的接口
     */
    @PostMapping("/agree")
    @ApiOperation(value = "B用户是否同意（同意传bFollow，不同意不用传）", notes = "B用户是否同意（同意传bFollow，不同意不用传）")
    public String agree(Integer bFollow, @RequestParam("concern") Integer concern, @RequestParam("concerned") Integer concerned) {
        return Optional.ofNullable(bFollow).map(f -> { //bFollow有值
            String s = "userRelation:userRelation-" + concern + "-" + concerned;
            userRelation userRelation = (userRelation) redisUtil.get(s);
            userRelationService.save(userRelation);
            redisUtil.del(s);//删除delete
            friendTokenService.deleteByConcernAndConcerned(concern, concerned);
            return "success";
        }).orElseGet(() -> {   //bFollow为Null
            friendTokenService.deleteByConcernAndConcerned(concern, concerned);
            return "delete-success";
        });
    }

    @PostMapping("/removeRelation")
    @ApiOperation(value = "取消关联(1-success)", notes = "取消关联(2-fail)")
    public int removeRelation(Integer concern, Integer concerned) {
        return userRelationService.deleteByConcernAndConcerned(concern, concerned);
    }

    @PostMapping("/concern")
    @ApiOperation(value = "他关注了谁", notes = "他关注了谁")
    public String concern(@RequestParam("userId") Integer userId) {
        List<userRelation> userRelations = userRelationService.findByConcern(userId);
        List<Object> objects = new ArrayList<>();
        userRelations.forEach(i -> {
            Map<String, Object> map = ImmutableMap.of("relationName", i.getRelationName(), "user", userService.findByIdNoOpenidAndRoleAndSecretKey(i.getConcerned()));
            objects.add(map);
        });
        return gson.toJson(objects);
    }

    @PostMapping("/concerned")
    @ApiOperation(value = "谁关注了他", notes = "谁关注了他")
    public String concerned(@RequestParam("userId") Integer userId) {
        List<userRelation> userRelations = userRelationService.findByConcerned(userId);
        List<Object> objects = new ArrayList<>();
        userRelations.forEach(i -> {
            Map<String, Object> map = ImmutableMap.of("relationName", i.getRelationName(), "user", userService.findByIdNoOpenidAndRoleAndSecretKey(i.getConcern()));
            objects.add(map);
        });
        return gson.toJson(objects);
    }

    @PostMapping("/checkCommunity_applicationRelation")
    @ApiOperation(value = "用户查看有没有社区关联自己", notes = "用户查看有没有社区关联自己")
    public String checkCommunity_applicationRelation(@RequestParam("userId") Integer userId) {
        return Optional.ofNullable(community_applicationService.findByUserId(userId)).map(cs -> {  //list有值
            List<Object> objects = new ArrayList<>();
            cs.forEach(c -> {
                Map<String, Object> map = ImmutableMap.of("bFollw", c.getBFollow(), "community", communityService.findById(c.getCommunityId()).get());
                objects.add(map);
            });
            return egson.toJson(objects);
        }).orElse(null);
    }

    @PostMapping("/agreeAddCommunity")
    @ApiOperation(value = "用户同意社区关联", notes = "用户同意社区关联")
    public String agreeAddCommunity(Integer bFollow, @RequestParam("userId") Integer userId, @RequestParam("communityId") Integer communityId) {
        return Optional.ofNullable(bFollow).map(f -> { //bFollow有值
            String s = "communityRelation:communityRelation-" + communityId + "-" + userId;
            communityRelation communityRelation = (communityRelation) redisUtil.get(s);
            communityRelationService.save(communityRelation);
            redisUtil.del(s);//删除delete
            community_applicationService.deleteByUserIdAndCommunityId(userId, communityId);//删除
            return "success";
        }).orElseGet(() -> {   //bFollow为Null
            community_applicationService.deleteByUserIdAndCommunityId(userId, communityId);//删除
            return "delete-success";
        });
    }

    @PostMapping("/cancelAssociation")
    @ApiOperation(value = "取消关联社区", notes = "取消关联社区")
    public String cancelAssociation(@RequestParam("userId") Integer userId) {
        if (StringUtils.isEmpty(communityRelationService.findByUserId(userId))) {
            return "fail";
        } else {
            communityRelationService.deleteByUserId(userId);
            return "success";
        }
    }

    /**
     * @param user
     * @return 通过id来填写信息
     * 需要前端限定长度 不然报错,
     * 要问前端是不是全部修改还是修改一部分,然后写对应的sql语句
     */
    @PostMapping("/update")
    @ApiOperation(value = "用户更新信息", notes = "用户更新信息")
    public void update(@RequestBody user user) {
        //时刻注意着主键 防止多个数据被修改
        userService.updateById(user);
    }

    @PostMapping("/updateLongitudeAndLatitudeByUserId")
    @ApiOperation(value = "用户更新经纬度", notes = "用户更新经纬度")
    public void updateLongitudeAndLatitudeByUserId(@RequestParam("longitude") String longitude, @RequestParam("latitude") String latitude, @RequestParam("userId") Integer userId) {
        userService.updateLongitudeAndLatitudeByUserId(longitude, latitude, userId);
        Map<String, Object> map = new HashMap<>();
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        redisUtil.lSet("longitudeAndLatitude1:uesrId" + userId, map);
//        if (redisUtil.lGetListSize("longitudeAndLatitude1:uesrId" + userId) == 1) {
//            redisUtil.expire("longitudeAndLatitude1:uesrId" + userId, 60 * 60 * 24 * 7);
//        }
    }

    @PostMapping("/findTrail")
    @ApiOperation(value = "用户查看轨迹", notes = "用户查看轨迹")
    public Object findTrail(@RequestParam("userId") Integer userId) {
        log.info(String.valueOf(redisUtil.getExpire("longitudeAndLatitude1:uesrId" + userId)));
        return redisUtil.lGet("longitudeAndLatitude1:uesrId" + userId, 0, -1);
    }

    @PostMapping("/community_service")
    @ApiOperation(value = "用户添加服务", notes = "用户添加服务")
    public community_service communityService(@RequestBody community_service community_service) {
        //在此，如果user有一个是null，那么数据库的数据将会被修改为null，故要修改或者更新某个数据的话得要自己写一个方法！！！
        //如果想要用save的话，就必须要依据主键
        return community_serviceService.save(community_service);
    }

    /**
     * 查看下status是传过来的还是我自己写的
     *
     * @param userId
     * @param number
     * @param page
     * @return
     */
    @PostMapping("/findAllServicePageByUserId")
    @ApiOperation(value = "用户分页查看服务", notes = "用户分页查看服务")
    public String findAllServicePageByUserId(@RequestParam("userId") Integer userId, @RequestParam("number") Integer number, @RequestParam("page") Integer page) {
        return gson.toJson(community_serviceService.findAllPageByUserId(userId, number, page));
    }

    @PostMapping("/InformationStatistics")
    @ApiOperation(value = "用户服务信息统计通过type", notes = "用户服务信息统计通过type")
    public String InformationStatistics(@RequestParam("userId") Integer userId) {
        Integer HuLi = community_serviceService.findByUserIdAndHuLi(userId);
        Integer BaoJie = community_serviceService.findByUserIdAndBaoJie(userId);
        Integer WeiXiu = community_serviceService.findByUserIdAndWeiXiu(userId);
        Integer PeiLiao = community_serviceService.findByUserIdAndPeiLiao(userId);
        Map<String, Integer> map = new HashMap<>();
        if (!StringUtils.isEmpty(HuLi)) {
            map.put("HuLi", HuLi);
        }
        if (!StringUtils.isEmpty(BaoJie)) {
            map.put("BaoJie", BaoJie);
        }
        if (!StringUtils.isEmpty(WeiXiu)) {
            map.put("WeiXiu", WeiXiu);
        }
        if (!StringUtils.isEmpty(PeiLiao)) {
            map.put("PeiLiao", PeiLiao);
        }
        return gson.toJson(map);
    }


    @PostMapping("/signIn")
    @ApiOperation(value = "签到接口", notes = "签到接口")
    public String signIn(@RequestBody signIn signIn) {
        signIn s = signInService.findByUserId(signIn.getUserId());
        if (StringUtils.isEmpty(s)) {//首次签到
            signIn.setIntegral(1);
            signInService.save(signIn);
        } else {//非首次签到
            s.setIntegral(s.getIntegral() + 1);
            s.setText(s.getText() + "," + signIn.getText());
            signInService.updateTextByuserId(s);
        }
        return "success";
    }


    @ApiOperation(value = "返回用户签到数", notes = "返回用户签到数据")
    @PostMapping("/allSignIn")
    public String allSignIn(@RequestParam("userId") Integer userId) {
        return gson.toJson(signInService.findByUserId(userId));
    }


    @PostMapping("/likeCount")
    @ApiOperation(value = "点赞接口", notes = "点赞接口")
    public void likeCount(likeCount likeCount) {
        likeCountService.save(likeCount);
    }


    @PostMapping("/unlikeCount")
    @ApiOperation(value = "取消点赞接口", notes = "取消点赞接口")
    public void unlikeCount(@RequestParam("userId") Integer userId) {
        likeCountService.deleteByUserId(userId);
    }


    @PostMapping("/publishTrends")
    @ApiOperation(value = "发表动态", notes = "发表动态")
    public String publishTrends(@RequestBody trends trends) {
        System.out.println("动态" + trends);
        trendsService.save(trends);
        return "1";
    }

    @PostMapping("/findAllTrendByUserId")
    @ApiOperation(value = "查询该用户的所有动态（包括评论点赞）", notes = "查询该用户的所有动态（包括评论点赞）")
    public String findAllTrendByUserId(@RequestParam("userId") Integer userId) {
        user user = userService.findById(userId).get();
        List<Object> objects = new ArrayList<>();
        List<trends> trends = trendsService.findByUserIdOrderByDateDesc(userId);
        trends.forEach(t -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", user.getNickName());
            map.put("photo", user.getPhoto());
            map.put("trendsId", t.getTrendsId());
            map.put("content", t.getContent());
            map.put("time", t.getDate());
            map.put("images", t.getImages());
            map.put("comment", commentService.findNameAndPhotoAndContentByTrendsId(t.getTrendsId()));
            Integer likeCounts = likeCountService.findCountByTrendsId(t.getTrendsId());
            if (likeCounts > 0) {
                map.put("likeNumber", likeCounts);
                map.put("isLike", true);
            } else {
                map.put("likeNumber", likeCounts);
                map.put("isLike", false);
            }
            objects.add(map);
        });
        return gson.toJson(objects);
    }

    @PostMapping("/findAllTrend")
    @ApiOperation(value = "查询动态表所有数据（包括评论点赞）", notes = "查询动态表所有数据（包括评论点赞）")
    public String findAllTrend() {
        List<Object> objects = new ArrayList<>();
        trendsService.findAllByDateDesc().forEach(t -> {
            Map<String, Object> map = new HashMap<>();
            user user = userService.findById(t.getUserId()).get();
            map.put("name", user.getNickName());
            map.put("photo", user.getPhoto());
            map.put("trendsId", t.getTrendsId());
            map.put("content", t.getContent());
            map.put("time", t.getDate());
            map.put("images", t.getImages());
            map.put("comment", commentService.findNameAndPhotoAndContentByTrendsId(t.getTrendsId()));
            Integer likeCounts = likeCountService.findCountByTrendsId(t.getTrendsId());
            if (likeCounts > 0) {
                map.put("likeNumber", likeCounts);
                map.put("isLike", true);
            } else {
                map.put("likeNumber", likeCounts);
                map.put("isLike", false);
            }
            objects.add(map);
        });
        return gson.toJson(objects);
    }

    @CrossOrigin(origins = "http://localhost/community")
    @DeleteMapping("/deleteTrendByUserId/{userId}_{trendsId}")
    @ApiOperation(value = "删除该用户的动态", notes = "删除该用户的动态")
    public int deleteTrendByUserId(@PathVariable("userId") Integer userId, @PathVariable("trendsId") Integer trendsId) {
        return trendsService.deleteByUserIdAndTrendsId(userId, trendsId);
    }

    /**
     * 发表评论
     *
     * @param comment
     * @return
     */
    @PostMapping("/publishComment")
    @ApiOperation(value = "发表评论", notes = "发表评论")
    public String publishComment(@RequestBody comment comment) {
        commentService.save(comment);
        return "1";
    }

    @GetMapping("/defaultPhoto")
    @ApiOperation(value = "9张可选头像", notes = "9张可选头像")
    public String defaultPhoto() {
        return gson.toJson(photoService.photo());
    }

    @PostMapping("/choosePhoto")
    @ApiOperation(value = "选择头像", notes = "选择头像")
    public void choosePhoto(@RequestParam("photo") String photo, @RequestParam("UserId") Integer UserId) {
        System.out.println(photo);
        System.out.println(UserId);
        userService.updatePhotoByUserId(photo, UserId);
    }

    @PostMapping("/emergency")
    @ApiOperation(value = "应急接口", notes = "应急接口")
    public String emergency(@RequestParam("userId") Integer userId) {
        new WeChatUtils(userService, userRelationService).tuiSong(userId);
        return "success";
    }

    @PostMapping("/dialect")
    @ApiOperation(value = "方言", notes = "方言")
    public String dialect(@RequestParam("pcm") MultipartFile pcm) throws IOException, InterruptedException {
        String fileName = pcm.getOriginalFilename();
        assert fileName != null;
        new iflytekUtils((FileInputStream) pcm.getInputStream());
        Thread.sleep(2000);
        return iflytekUtils.getS();
//        return "1";
    }

    //    @Async
    @GetMapping("/test")
    @ApiOperation(value = "测试", notes = "带name参数")
    public Object test(Integer id) {
//        //适合于两次查询数据库的适合
//        //customer相当于查询之后的数据
//        return userService.findById(2).map(u -> {
//            System.out.println(u);
//            return u;
//        }).orElseThrow(() -> {
////            new RuntimeException("没有该人的数据~~~") ;
//            return new UserException("没有该人的数据~~~");
//        });

//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start("任务1");
//        List<Object> objects = new ArrayList<>();
//        for (int i = 0; i < 1000; i++) {
////            Future<Object> o = testService.findById(i);
////            log.info(String.valueOf(o.get()));
////            objects.add(o.get());
//            Object o = testService.find(i);
//            log.info(String.valueOf(i));
//            objects.add(o);
//        }
////        log.info(String.valueOf(objects));
//        stopWatch.stop();
//        System.out.println(stopWatch.prettyPrint());
//        return "主线程结束";
        return userService.findByIdNoOpenidAndRoleAndSecretKey(id);
    }
}
