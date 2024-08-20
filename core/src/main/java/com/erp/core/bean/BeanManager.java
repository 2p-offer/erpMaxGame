package com.erp.core.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContextException;

import java.lang.annotation.Annotation;
import java.util.Map;


public class BeanManager {
    private static ConfigurableListableBeanFactory BEAN_FACTORY;

    private BeanManager() {
    }

    /**
     * 获取对象
     *
     * @param clazz class
     * @return Object 一个以类型注册的bean实例
     * @throws BeansException exception
     */
    public static <T> T getBean(Class<T> clazz) throws BeansException {
        checkBeanFactory();
        return BEAN_FACTORY.getBean(clazz);
    }

    /**
     * 获取对象
     *
     * @param clazz class
     * @param param 属性参数
     * @param <T>   generic type
     * @return Object 一个以类型注册的bean实例
     * @throws BeansException exception
     */
    public static <T> T getBean(Class<T> clazz, Object... param) throws BeansException {
        checkBeanFactory();
        return BEAN_FACTORY.getBean(clazz, param);
    }

    /**
     * 获取对象
     *
     * @param name  bean name
     * @param clazz class type
     * @param <T>   generic type
     * @return Object 一个以类型注册的bean实例
     * @throws BeansException exception
     */
    public static <T> T getBean(String name, Class<T> clazz) throws BeansException {
        checkBeanFactory();
        return BEAN_FACTORY.getBean(name, clazz);
    }

    /**
     * 获取对象
     *
     * @param name bean name
     * @return Object 一个以所给名字注册的bean的实例
     * @throws BeansException exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        checkBeanFactory();
        return (T) BEAN_FACTORY.getBean(name);
    }

    /**
     * 获取对象
     *
     * @param name  bean name
     * @param param 属性参数
     * @return Object 一个以所给名字注册的bean的实例
     * @throws BeansException exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name, Object... param) throws BeansException {
        checkBeanFactory();
        return (T) BEAN_FACTORY.getBean(name, param);
    }

    /**
     * 获取特定类型的所有对象
     *
     * @param clazz class
     * @param <T>   generic type
     * @return Map<String, T>
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return BEAN_FACTORY.getBeansOfType(clazz);
    }

    /**
     * 获取拥有特定注解的所有Bean
     *
     * @param annotationType annotation type
     * @return Map<String, Object>
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        checkBeanFactory();
        return BEAN_FACTORY.getBeansWithAnnotation(annotationType);
    }

    /**
     * 创建原型Bean
     *
     * @param clazz class
     * @param param 属性参数
     * @param <T>   generic type
     * @return T
     */
    public static <T> T createPrototypeBean(Class<T> clazz, Object... param) {
        return getBean(clazz, param);
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     *
     * @param name bean name
     * @return boolean
     */
    public static boolean containsBean(String name) {
        checkBeanFactory();
        return BEAN_FACTORY.containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
     * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     *
     * @param name bean name
     * @return boolean
     * @throws NoSuchBeanDefinitionException exception
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        checkBeanFactory();
        return BEAN_FACTORY.isSingleton(name);
    }

    /**
     * @param name bean name
     * @return Class 注册对象的类型
     * @throws NoSuchBeanDefinitionException exception
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        checkBeanFactory();
        return BEAN_FACTORY.getType(name);
    }

    private static void checkBeanFactory() {
        if (BEAN_FACTORY == null) {
            throw new ApplicationContextException("application context not initialized");
        }
    }

    public static void setBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        if (BEAN_FACTORY != null) {
            throw new IllegalStateException(String.format("eviction listener was already set to %s", BEAN_FACTORY));
        }
        BEAN_FACTORY = beanFactory;
    }

}
