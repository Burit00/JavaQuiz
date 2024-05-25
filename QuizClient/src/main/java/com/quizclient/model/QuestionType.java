package com.quizclient.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serial;

public enum QuestionType {
    @SerializedName("1")
    CHECKBOX,
    @SerializedName("2")
    RADIO,
    @SerializedName("3")
    INPUT
}
