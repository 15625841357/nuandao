package com.support;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.support.pojo.communityRelation;
import com.support.repository.userRelationRepository;
import com.support.repository.userRepository;
import com.support.service.*;
import com.support.test.TaskTest;
import com.support.test.testService;
import com.support.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest
@CacheConfig(cacheNames = "photo:")
@RunWith(SpringRunner.class)
@Slf4j
public class SupportApplicationTests {
    @Autowired
    private community_applicationService community_applicationService;
    @Autowired
    private community_serviceService community_serviceService;
    @Autowired
    private userRelationService userRelationService;
    @Autowired
    private userService userService;
    @Autowired
    private community_userService community_userService;
    @Autowired
    private com.support.repository.communityRepository communityRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private communityService communityService;
    @Autowired
    private communityRelationService communityRelationService;
    @Autowired
    private userRelationRepository userRelationRepository;
    @Autowired
    private com.support.test.testService testService;
    @Autowired
    private com.support.test.TaskTest TaskTest;
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
    private friendTokenService friendTokenService;
    @Resource
    private RedisUtil redisUtil;
    //    private Gson gson = new Gson();
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private Gson egson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation().create();

    @Test
    public void c() {
//        Integer id = 404;
//        userService.findById(id).ifPresent(user -> {
//            System.out.println(user);
//            java.util.Date date = TimeConversionUtil.StringTransferToDate(String.valueOf(user.getLastLogin()), "yyyy-MM-dd HH:mm:ss");
//            String time = AgeUtlis.longtime(date);
//            if ("3".equals(time)) {
//                ExecutorService executor = Executors.newFixedThreadPool(1);
//                executor.execute(() -> {
//                    try {
//                        new WeChatUtils(userService, userRelationService).tuiSong(404);
//                        String name = userService.findById(404).get().getName();
//                        communityRelation c = communityRelationService.findByUserId(404);
//                        String email = communityService.findById(c.getCommunityId()).get().getEmail();
//                        String body = name + "可能摔倒,或者紧急需要帮忙";
//                        MailUtil.SendMail(email, name + "突发意外", body);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                });
//                executor.shutdown();
//            }
//        });
//        System.out.println("吴俊淇");
    }

    @Ignore
    @Test
    public void B() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("任务1");

        Future<String> s1 = TaskTest.register();
//        Future<String> s2 = TaskTest.register();
        try {
            log.info("测试：" + s1.get());
//            log.info(s2.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        stopWatch.stop();
        System.out.println("ckjefiausaasfasfasf");
        System.out.println(stopWatch.prettyPrint());
    }

//    @Ignore
//    @Test
//    public void A() throws ExecutionException, InterruptedException, TimeoutException {
////        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//        Runnable runnableTask = () -> {
//            try {
//                TimeUnit.MILLISECONDS.sleep(300);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        };
//
//        Callable<String> callableTask = () -> {
//            TimeUnit.MILLISECONDS.sleep(1000);
//            return "Task's execution1";
//        };
//        Callable<String> callableTask1 = () -> {
//            TimeUnit.MILLISECONDS.sleep(2000);
//            return "Task's execution2";
//        };
//        Callable<String> callableTask2 = () -> {
//            TimeUnit.MILLISECONDS.sleep(3000);
//            return "Task's execution3";
//        };
//        List<Callable<String>> callableTasks = new ArrayList<>();
//        callableTasks.add(callableTask);
//        callableTasks.add(callableTask1);
//        callableTasks.add(callableTask2);
//
//        ExecutorService executor = Executors.newFixedThreadPool(1);
//        Future<String> future = executor.submit(callableTask2);
//        String result = future.get(3000, TimeUnit.MILLISECONDS);
//        System.out.println(result);
////        List<Future<String>> futures = executor.invokeAll(callableTasks);
////        for (Future<String> f : futures) {
////            String result = null;
////            try {
////                result = f.get();
////                System.out.println(result);
////            } catch (InterruptedException | ExecutionException e) {
////                e.printStackTrace();
////            }
////        }
//
//
//    }
//
//    @Ignore
//    @Test
//    public void test() {
//        Specification<user> specification = new Specification<user>() {
//            @Override
//            public Predicate toPredicate(Root<user> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
////                List<Predicate> predicates = new ArrayList<Predicate>();
////                Predicate[] pre = new Predicate[predicates.size()];
////                if(schoolParam.getParent()!=null){
////                    //这里相当于数据库字段 parent（也是Long类型） = 前台传过来的值schoolParam.getParent()
////                    predicates.add(criteriaBuilder.equal(root.get("parent").as(Long.class),schoolParam.getParent()));
////                }
////                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
////                return criteriaQuery.where(predicates.toArray(pre)).getRestriction();
//                Path path = root.get("id");
//                Predicate predicate = criteriaBuilder.lt(path, 6);
//                return predicate;
//            }
//        };
//        List<user> list = userRepository.findAll(specification);
//        list.forEach(System.out::println);
//
//    }
//
//    @Ignore
//    @Test
//    public void contextLoads() {
//        List<String> ints = new ArrayList<>();
//        ints.add("1");
//        ints.add("2");
//        ints.add("3");
//        redisUtil.lSet("photo:photo", ints);
////        ints.forEach(System.out::println);
////       Optional.of(ints).orElse(new ArrayList<>()).forEach(System.out::println);
//    }

}
