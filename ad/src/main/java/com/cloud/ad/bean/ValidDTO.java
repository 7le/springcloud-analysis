package com.cloud.ad.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * valid 测试
 *
 * @author 7le
 */
@Data
public class ValidDTO {

    @NotNull(message = "账号不能为空")
    @ApiModelProperty(value = "账号")
    @Pattern(regexp = "^[^'/:*?\"<>|\\\\]+$", message = "name不能有特殊字符")
    public String name;

    @ApiModelProperty(value = "类型", allowEmptyValue = true)
    public String type;
}
