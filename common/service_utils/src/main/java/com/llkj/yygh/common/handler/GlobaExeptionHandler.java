package com.llkj.yygh.common.handler;

import com.llkj.yygh.common.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName GlobaExeptionHandler
 * @Description TODO
 * @Author qing
 * @Date 2023/2/20 16:31
 * @Version 1.0
 */
@ControllerAdvice
public class GlobaExeptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R RExecetion(Exception e) {
        e.printStackTrace();
        return R.error().message("统一异常处理");
    }

    /*
     *
     * 自定义异常处理
     * */
    @ExceptionHandler(LLKJException.class)
    @ResponseBody
    public R RExecetion(LLKJException e) {
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMessage());
    }
}
