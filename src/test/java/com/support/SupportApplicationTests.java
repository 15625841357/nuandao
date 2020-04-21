package com.support;

import com.google.gson.Gson;
import com.support.pojo.test;
import com.support.repository.userRelationRepository;
import com.support.repository.userRepository;
import com.support.service.communityService;
import com.support.service.photoService;
import com.support.test.TaskTest;
import com.support.utils.RedisUtil;
import com.support.utils.iflytekUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SpringBootTest
@CacheConfig(cacheNames = "photo:")
@RunWith(SpringRunner.class)
@Slf4j
public class SupportApplicationTests {
    @Resource
    private RedisUtil redisUtil;
    private Gson gson = new Gson();
    @Autowired
    private userRepository userRepository;
    @Autowired
    private userRelationRepository userRelationRepository;
    @Autowired
    private com.support.repository.friendTokenRepository friendTokenRepository;
    @Autowired
    private communityService communityService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private photoService photoService;
    @Autowired
    private TaskTest TaskTest;
    @Autowired
    private com.support.test.testService testService;

    @Test
    public void B()   {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("任务1");

        Future<String> s1 = TaskTest.register();
//        Future<String> s2 = TaskTest.register();
        try {
            log.info("测试："+s1.get());
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
