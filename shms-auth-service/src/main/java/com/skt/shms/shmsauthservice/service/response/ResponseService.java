package com.skt.shms.shmsauthservice.service.response;

import com.skt.shms.shmsauthservice.model.response.CommonResult;
import com.skt.shms.shmsauthservice.model.response.ListResult;
import com.skt.shms.shmsauthservice.model.response.SingleResult;
import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

@Service
public class ResponseService {
    @Autowired
    private  MessageSource messageSource;

    // 단일건 결과
    public <T> SingleResult<T> getSingleResult(T data, String code, String msg) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result, code, msg);
        return result;
    }

    // 복수건 결과
    public <T> ListResult<T> getListResult(List<T> list, String code, String msg) {
        ListResult<T> result = new ListResult<>();
        result.setList(list);
        setSuccessResult(result, code, msg);
        return result;
    }

    // 성공 메시지만
    public CommonResult getSuccessResult(String code, String msg) {
        CommonResult result = new CommonResult();
        setSuccessResult(result, code, msg);
        return result;
    }

    // 성공 시 응답 모델을 성공 데이터로 세팅
    private void setSuccessResult(CommonResult result, String code, String msg) {
        if(code == null || code.equals("")){ code = "success.code"; }
        if(msg == null || msg.equals("")){ msg = "success.msg"; }
        
        result.setSuccess(true);
        result.setCode(Integer.parseInt(getMessage(code)));
        result.setMsg(getMessage(msg));
    }


    // 실패건 처리
    public CommonResult getFailResult(String code, String msg) {
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        setFailResult(result, code, msg);
        return result;
    }

    // 실패 시 응답 모델을 실패 데이터로 세팅
    private void setFailResult(CommonResult result, String code, String msg) {
        if(code == null || code.equals("")){ code = "failure.code"; }
        if(msg == null || msg.equals("")){ msg = "failure.msg"; }

        result.setSuccess(false);
        result.setCode(Integer.parseInt(getMessage(code)));
        result.setMsg(getMessage(msg));
    }

    private String getMessage(String code) {
        return getMessage(code, null);
    }

    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
