package com.neuqer.mail.dto.response;

import java.util.List;

/**
 * Created by dgy on 17-5-24.
 */
public class AddMultipleResponse {

    List<ErrorMessage> errorMessages;

    public List<ErrorMessage> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<ErrorMessage> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public class ErrorMessage{
        private String mobile;

        private String remark;

        private Long row;

        private int exceptionCode;

        private String exceptionMessage;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Long getRow() {
            return row;
        }

        public void setRow(Long row) {
            this.row = row;
        }

        public int getExceptionCode() {
            return exceptionCode;
        }

        public void setExceptionCode(int exceptionCode) {
            this.exceptionCode = exceptionCode;
        }

        public String getExceptionMessage() {
            return exceptionMessage;
        }

        public void setExceptionMessage(String exceptionMessage) {
            this.exceptionMessage = exceptionMessage;
        }
    }
}
