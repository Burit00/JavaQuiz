package com.quizclient.enums;

import com.google.gson.annotations.SerializedName;

public enum QuestionTypeEnum {
    @SerializedName("0")
    RADIO(0),
    @SerializedName("1")
    CHECKBOX(1),
    @SerializedName("2")
    INPUT(2);


    private QuestionTypeEnum(final int questionType) {}
}
