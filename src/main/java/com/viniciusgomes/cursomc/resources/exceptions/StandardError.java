package com.viniciusgomes.cursomc.resources.exceptions;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class StandardError implements Serializable {
    private static final long serialVersionUID = 1L;

    public String title;
    public Integer status;
    public String detail;
    public ZonedDateTime timeStamp;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public ZonedDateTime getTimeStamp() {
        ZoneId defaultZone = ZoneId.systemDefault();
        timeStamp = LocalDateTime.now().atZone(defaultZone);
        return timeStamp;
    }

    public void setTimeStamp(ZonedDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }


    public static final class Builder {
        private String title;
        private Integer status;
        private String detail;
        private ZonedDateTime timeStamp;

        private Builder () {

        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder title (String title) {
            this.title = title;
            return this;
        }

        public Builder status (Integer status) {
            this.status = status;
            return this;
        }

        public Builder detail (String detail) {
            this.detail = detail;
            return this;
        }


        public Builder timeStamp(ZonedDateTime timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public StandardError build() {
            StandardError standardError = new StandardError();
            standardError.timeStamp = this.timeStamp;
            standardError.title = this.title;
            standardError.status = this.status;
            standardError.detail = this.detail;
            return standardError;
        }
    }

//    private Integer status;
//    private String msg;
//    private Long timeStamp;
//
//    public StandardError(Integer status, String msg, Long timeStamp) {
//        this.status = status;
//        this.msg = msg;
//        this.timeStamp = timeStamp;
//    }
//
//    public Integer getStatus() {
//        return status;
//    }
//
//    public void setStatus(Integer status) {
//        this.status = status;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public Long getTimeStamp() {
//        return timeStamp;
//    }
//
//    public void setTimeStamp(Long timeStamp) {
//        this.timeStamp = timeStamp;
//    }
}
