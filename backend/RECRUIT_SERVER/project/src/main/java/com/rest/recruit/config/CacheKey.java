package com.rest.recruit.config;

public class CacheKey {
    private CacheKey(){}
    public static final String CALENDAR = "calendar";
    public static final String RECRUIT = "detailRecruit";
    public static final String POSITION = "detailPosition";
    public static final int DETAIL_POSITION_EXPIRE_SEC = 6000;
    public static final int CALENDAR_EXPIRE_SEC = 6000;
    public static final int DETAIL_RECRUIT_EXPIRE_SEC = 6000;

}
