package com.jingai.swagger.controller;

import com.jingai.swagger.annotation.ApiRespJson;
import com.jingai.swagger.config.AppConfig;
import com.jingai.swagger.vo.MemberVo;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "会员接口")
@RestController
public class TestController {

    @ApiOperation(value = "获取会员信息")
    @ApiImplicitParam(name = "id", dataType = "java.lang.Integer", value = "会员id", required = true)
    @ApiResponse(code = 200, message = "返回成功", response = AppConfig.class)
    @ApiModelProperty("会员信息")
    @GetMapping("member/get")
    public Map<String, Object> getMember(@RequestParam Integer id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("会员id不能为空");
        }
        HashMap<String, Object> rs = new HashMap<String, Object>(4);
        rs.put("id", 1);
        rs.put("name", "张三");
        rs.put("sex", "男");
        rs.put("age", "25");
        return rs;
    }

    @ApiOperation(value = "获取会员信息1")
    @ApiImplicitParam(name = "id", dataType = "java.lang.Integer", value = "会员id", required = true)
    @ApiResponse(code = 200, message = "返回成功1", response = MemberVo.class)
    @GetMapping("member/get1")
    public MemberVo getMember1(@RequestParam Integer id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("会员id不能为空");
        }
        MemberVo memberVo = new MemberVo();
        memberVo.setId(id);
        memberVo.setAge(25);
        memberVo.setName("李四");
        memberVo.setSex("男");
        return memberVo;
    }

    @ApiOperation(value = "获取会员信息")
    @ApiImplicitParam(name = "id", dataType = "java.lang.Integer", value = "会员id", required = true)
    @ApiRespJson(jsonStr = "{id:会员id,name:会员名称,sex:性别,age:年龄}", desc = "介绍")
    @GetMapping("member/get2")
    public Map<String, Object> getMember2(@RequestParam Integer id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("会员id不能为空");
        }
        HashMap<String, Object> rs = new HashMap<String, Object>(4);
        rs.put("id", 3);
        rs.put("name", "张三");
        rs.put("sex", "男");
        rs.put("age", "25");
        return rs;
    }

}
