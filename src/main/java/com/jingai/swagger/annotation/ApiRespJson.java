package com.jingai.swagger.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * swagger接口返回json格式的数据说明
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiRespJson {

    /** Json格式如：{id: 会员id, name: 会员名称, age: 会员年龄, speciality: [{name: 特长名称}]} */
    String jsonStr() default "";

    /** Json数据的介绍 */
    String desc() default "";

}
