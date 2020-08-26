package org.greenbyme.angelhack.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasicResponseDto<T> {

    private T data;
    private Integer status;
    private String message;

    private BasicResponseDto(T data, int status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public static <Void> BasicResponseDto<Void> empty() {
        return new BasicResponseDto<>(null, 0, null);
    }

    public static <Void> BasicResponseDto<Void> error(final int code) {
        return new BasicResponseDto<>(null, code, null);
    }

    public static <Void> BasicResponseDto<Void> error(final int code, final String message) {
        return new BasicResponseDto<>(null, code, message);
    }

    public static <T> BasicResponseDto<T> of(final T data, final int code) {
        return new BasicResponseDto<>(data, code, null);
    }

    public static <T> BasicResponseDto<T> of(final T data, final int code, final String message) {
        return new BasicResponseDto<>(data, code, message);
    }
}
