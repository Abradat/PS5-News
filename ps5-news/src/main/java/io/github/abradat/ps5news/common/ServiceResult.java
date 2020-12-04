package io.github.abradat.ps5news.common;

public class ServiceResult <T> {
    private T t;
    private ResultStatus resultStatus;
    private String description;

    public ServiceResult(T t, ResultStatus resultStatus, String description) {
        this.t = t;
        this.resultStatus = resultStatus;
        this.description = description;
    }

    public T getData() {
        return t;
    }

    public void setData(T t) {
        this.t = t;
    }

    public ResultStatus getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(ResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
