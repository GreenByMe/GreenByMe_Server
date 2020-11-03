package org.greenbyme.angelhack.domain.user;

public enum PlatformType {

    NONE("어플 내 이메일 가입"), KAKAO("카카오"), GOOGLE("구글"), NAVER("네이버");

    private String platform;

    PlatformType(String platform) {
        this.platform = platform;
    }

    public String getPlatform() {
        return platform;
    }
}
