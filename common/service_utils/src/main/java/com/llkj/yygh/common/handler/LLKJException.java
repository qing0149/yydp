package com.llkj.yygh.common.handler;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName LLKJException
 * @Description TODO
 * @Author qing
 * @Date 2023/2/20 16:43
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LLKJException extends RuntimeException {
    @ApiModelProperty(value = "状态码")
    private Integer code;

    private String msg;
}
