package com.skt.shms.shmsauthservice.service.response;

import com.skt.shms.shmsauthservice.model.response.CommonResult;
import com.skt.shms.shmsauthservice.model.response.ListResult;
import com.skt.shms.shmsauthservice.model.response.SingleResult;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResponseService {

    // 단일건 결과 처리 메소드
    public <T> SingleResult<T> getSingleResult(T data, int code, String msg) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result, code, msg);
        return result;
    }

    // 복수건 결과 처리 메서드
    public <T> ListResult<T> getListResult(List<T> list, int code, String msg) {
        ListResult<T> result = new ListResult<>();
        result.setList(list);
        setSuccessResult(result, code, msg);
        return result;
    }

    // 성공 결과만 처리
    public CommonResult getSuccessResult(int code, String msg) {
        CommonResult result = new CommonResult();
        setSuccessResult(result, code, msg);
        return result;
    }

    // 실패 결과만 처리
    public CommonResult getFailResult(int code, String msg) {
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        setFailResult(result, code, msg);
        return result;
    }

    // API 요청 성공 시 응답 모델을 성공 데이터로 세팅
    private void setSuccessResult(CommonResult result, int code, String msg) {
        result.setSuccess(true);
        result.setCode(code);
        result.setMsg(msg);
    }
    // private void setSuccessResult(CommonResult result) {
    //     result.setSuccess(true);
    //     result.setCode(CommonResponse.SUCCESS.getCode());
    //     result.setMsg(CommonResponse.SUCCESS.getMsg());
    // }

    // API 요청 실패 시 응답 모델을 실패 데이터로 세팅
    private void setFailResult(CommonResult result, int code, String msg) {
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
    }
}
