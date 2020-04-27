package cn.zora.base.model;

import cn.zora.base.constants.ResultCode;
import cn.zora.base.enumerate.ResultCodeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * <h3>ZoraCloudTransferStation</h3>
 * <h4>cn.zora.base.model</h4>
 * <p>返回参数</p>
 *
 * @author zora
 * @since 2020.04.24
 */
@Data
@Slf4j
@Builder
public class Response<T> implements Serializable {
    @ApiModelProperty(value = "code")
    private Integer code;
    @ApiModelProperty(value = "消息描述")
    private String message;
    @ApiModelProperty(value = "返回对象")
    private T data;

    public static <T> Response<T> withRes(Consumer<Response<T>> consumer) {
        long start = System.currentTimeMillis();
        Response<T> response = Response.<T>builder().build();
        try {
            consumer.accept(response);
            long delta = System.currentTimeMillis() - start;
            response.setCode(ResultCode.SUCCESS.getCode());
            response.setMessage("succeeded with " + delta + " ms");
        } catch (IllegalArgumentException ex) {
            log.error("Illegal input param.", ex);
            long delta = System.currentTimeMillis() - start;
            response.setCode(ResultCode.PARAM_ERROR.getCode());
            response.setMessage("failed with " + delta + " ms, " + ex.getMessage());
        } catch (Exception ex) {
            log.error("Unexpected exception throw.", ex);
            long delta = System.currentTimeMillis() - start;
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("failed with " + delta + " ms, " + ex.getMessage());
        }

        return response;
    }

    public static <T> Response<T> type(ResultCodeEnum resultCodeEnum, T data, String message) {
        return Response.<T>builder()
                .code(resultCodeEnum.getCode())
                .message(message == null ? resultCodeEnum.getMessage() : message)
                .data(data)
                .build();
    }

    public static <T> Response<T> type(ResultCodeEnum resultCodeEnum) {
        return type(resultCodeEnum, null, null);
    }

    public static <T> Response<T> type(ResultCodeEnum resultCodeEnum, String message) {
        return type(resultCodeEnum, null, message);
    }

    public static <T> Response<T> type(ResultCodeEnum resultCodeEnum, T data) {
        return type(resultCodeEnum, data, null);
    }

    public static <T> Response<T> success(T data) {
        return Response.<T>builder()
                .code(ResultCode.SUCCESS.getCode())
                .message(ResultCode.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    public static <T> Response<T> success() {
        return success(null);
    }

    public static <T> Response<T> fail(T data) {
        return Response.<T>builder()
                .code(ResultCode.FAIL.getCode())
                .message(ResultCode.FAIL.getMessage())
                .data(data)
                .build();
    }

    public static <T> Response<T> fail() {
        return fail(null);
    }

    public static <T> Response<T> paramError(T data) {
        return Response.<T>builder()
                .code(ResultCode.PARAM_ERROR.getCode())
                .message(ResultCode.PARAM_ERROR.getMessage())
                .data(data)
                .build();
    }

    public static <T> Response<T> paramError() {
        return paramError(null);
    }

    public static <T> Response<T> unAuthor(T data) {
        return Response.<T>builder()
                .code(ResultCode.UN_AUTHOR.getCode())
                .message(ResultCode.UN_AUTHOR.getMessage())
                .data(data)
                .build();
    }

    public static <T> Response<T> unAuthor() {
        return unAuthor(null);
    }
}
