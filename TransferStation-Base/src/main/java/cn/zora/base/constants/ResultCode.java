package cn.zora.base.constants;


import cn.zora.base.enumerate.ResultCodeEnum;

/**
 * <h3>study</h3>
 * <p>返回结果码</p>
 *
 * @author : zora
 * @date : 2019/11/28
 */
public interface ResultCode {
    /**
     * 请求成功，一切OK
     */
    ResultPattern SUCCESS = ResultCodeEnum.common_0_200;
    /**
     * 请求失败，权限不足
     */
    ResultPattern UN_AUTHOR = ResultCodeEnum.common_1_403;
    /**
     * 请求失败，内部错误
     */
    ResultPattern FAIL = ResultCodeEnum.common_1_400;
    /**
     * 请求失败，参数错误
     */
    ResultPattern PARAM_ERROR = ResultCodeEnum.common_1_410;
    /**
     * 请求失败，系统异常
     */
    ResultPattern SYSTEM_ERROR = ResultCodeEnum.common_4_506;
}
