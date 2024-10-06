package com.sulaihi.genericcallapiinretrofit.Repository.RepoImpl.BaseRepository.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class ErrorResponse {
    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("traceId")
    @Expose
    private String traceId;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;

    /*    @SerializedName("errors")
        private ErrorData errors;*/

    @SerializedName("errors")
    private Map<String, List<String>> errors;

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public int getStatus() {
        return status;
    }

    public String getTraceId() {
        return traceId;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, List<String>> errors) {
        this.errors = errors;
    }


    public String GetErrorsMessage() {
        StringBuilder errorMessageBuilder = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : errors.entrySet()) {
            String fieldName = entry.getKey();
            List<String> fieldErrors = entry.getValue();
            // Concatenate the field name and its associated error messages
            errorMessageBuilder.append(fieldName).append(": ");
            for (String error : fieldErrors) {
                errorMessageBuilder.append(error).append("; ");
            }
        }

        String errorMessage = errorMessageBuilder.toString();
        return errorMessage;
    }
  /*  public class ErrorData {
        @SerializedName("newPassword")
        private String[] newPasswordErrors;

        public String[] getNewPasswordErrors() {
            return newPasswordErrors;
        }
    }*/
}