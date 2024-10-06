package com.sulaihi.genericcallapiinretrofit.Repository.RepoImpl.BaseRepository.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GeneralResponseMdl<R> {


    @SerializedName("success")
    @Expose
    public boolean success;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("entity")
    @Expose
    public R entity;

    @SerializedName("responsecode")
    @Expose
    public String responsecode;

    public GeneralResponseMdl() {
    }

    public GeneralResponseMdl(boolean success,String Responsecode,String message,R entity) {
        this.success = success;
        this.message = message;
        this.entity = entity;
        this.responsecode = Responsecode;
    }

    public GeneralResponseMdl(boolean success,String Responsecode,R entity) {
        this.success = success;
        this.message = message;
        this.entity = entity;
        this.responsecode = Responsecode;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public R getEntity() {
        return entity;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setEntity(R entity) {
        this.entity = entity;
    }

    public String getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(String responsecode) {
        responsecode = responsecode;
    }


}
