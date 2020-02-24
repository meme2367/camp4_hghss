package com.rest.recruit.model;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Recruit {
    private int recruitId;
    private int companyId;
    private String companyName;
    private String imageFileName;
    private String employmentPageUrl;
    private String startTime;
    private String endTime;
    private int recruitType;
    private String content;
    private int viewCount;
    private boolean favorite;
    private int favoriteCount;

    public void setRecruit(Recruit recruit) {
        this.recruitId = recruit.recruitId;
        this.companyId = recruit.companyId;
        this.companyName = recruit.companyName;
        this.imageFileName = recruit.imageFileName;
        this.employmentPageUrl = recruit.employmentPageUrl;
        this.startTime = recruit.startTime;
        this.endTime = recruit.endTime;
        this.recruitType = recruit.recruitType;
        this.content = recruit.content;



    }
}
