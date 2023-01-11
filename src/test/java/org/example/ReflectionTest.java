package org.example;

import org.example.annotaion.Controller;
import org.example.annotaion.Service;
import org.example.model.User;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    /*
        @Controller 어노테이션이 생성되어 있는 모든 클래스를 찾아 출력한다.
     */

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);
    @Test
    void controllerScan() {
        Set<Class<?>> beans = getTypesAnnotatedWith(List.of(Controller.class, Service.class));

        logger.debug("beans: [{}]", beans);
    }

    @Test
    void showClass() {
        // 클래스에 대한 모든 정보를 출력하는 테스트 메소드

        Class<User> clazz = User.class;
        logger.debug(clazz.getName());

        logger.debug("User all declared fields: [{}]", Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList()));
        logger.debug("User all declared constructors: [{}]", Arrays.stream(clazz.getDeclaredConstructors()).collect(Collectors.toList()));
        logger.debug("User all declared methods: [{}]", Arrays.stream(clazz.getDeclaredMethods()).collect(Collectors.toList()));
    }


    @Test
    void load() throws ClassNotFoundException {
        // 힙 영역에 로드되어 있는 클래스 타입의 객체 가져오는 테스트 메소드

        // 1
        Class<User> clazz = User.class;

        // 2
        User user = new User("serverwizard", "홍홍홍");
        Class<? extends User> clazz2 = user.getClass();

        // 3
        Class<?> clazz3 = Class.forName("org.example.model.User");

        logger.debug("clazz: [{}]", clazz);
        logger.debug("clazz2: [{}]", clazz2);
        logger.debug("clazz3: [{}]", clazz3);

        assertThat(clazz == clazz2).isTrue();
        assertThat(clazz2 == clazz3).isTrue();
        assertThat(clazz3 == clazz).isTrue();
        // 세 가지 모두 같은 객체임을 확인함
    }


    private Set<Class<?>> getTypesAnnotatedWith(List<Class<? extends Annotation>> annotaions) {
        Reflections reflections = new Reflections("org.example");

        Set<Class<?>> beans = new HashSet<>();
        annotaions.forEach(annotation -> beans.addAll(reflections.getTypesAnnotatedWith(annotation)));

//        Set<Class<?>> beans = new HashSet<>();
//        beans.addAll(reflections.getTypesAnnotatedWith(Controller.class));  // @Controller 가 생성된 클래스 찾아서 beans에 추가
//        beans.addAll(reflections.getTypesAnnotatedWith(Service.class));     // @Service 가 생성된 클래스 찾아서 beans에 추가
//
//        logger.debug("beans: [{}]", beans);

        return beans;
    }
}
