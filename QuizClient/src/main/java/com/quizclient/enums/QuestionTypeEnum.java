package com.quizclient.enums;

import com.google.gson.annotations.SerializedName;

public enum QuestionTypeEnum {
    @SerializedName("1")
    CHECKBOX,
    @SerializedName("2")
    RADIO,
    @SerializedName("3")
    INPUT
}
