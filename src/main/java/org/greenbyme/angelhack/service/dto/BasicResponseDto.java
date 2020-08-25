package org.greenbyme.angelhack.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasicResponseDto<T> {

    private T data;
    private String status;
    private String message;

    public BasicResponseDto(T data, String status, String message) {
        this.data = data;
        this.status = status;[]
        this.message = message;
    }

    public static <Void> BasicResponseDto<Void> empty() {
        return new BasicResponseDto<>(null, null, null);
    }

    public static <Void> BasicResponseDto<Void> error(final String code) {
        return new BasicResponseDto<>(null, code, null);
    }

    public static <Void> BasicResponseDto<Void> error(final String code, final String message) {
        return new BasicResponseDto<>(null, code, message);
    }

    public static <T> BasicResponseDto<T> of(final T data) {
        return new BasicResponseDto<>(data, null, null);
    }

    public static <T> BasicResponseDto<T> of(final T data, final String code) {
        return new BasicResponseDto<>(data, code, null);
    }

    public static <T> BasicResponseDto<T> of(final T data, final String code, final String message) {
        return new BasicResponseDto<>(data, code, message);
    }
}
