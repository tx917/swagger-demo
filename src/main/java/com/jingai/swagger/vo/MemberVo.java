package com.jingai.swagger.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("会员信息")
public class MemberVo {

    @ApiModelProperty("会员id")
    private int id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("年龄")
    private int age;

    @ApiModelProperty("性别")
    private String sex;

}
