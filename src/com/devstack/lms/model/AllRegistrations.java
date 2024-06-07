package com.devstack.lms.model;


public class AllRegistrations {
    private String registered_date;
    private String studentName;
    private String paymentType;

    public AllRegistrations() {
    }

    public AllRegistrations(String registered_date, String studentName, String paymentType) {
        this.registered_date = registered_date;
        this.studentName = studentName;
        this.paymentType = paymentType;
    }

    public String getRegistered_date() {
        return registered_date;
    }

    public void setRegistered_date(String registered_date) {
        this.registered_date = registered_date;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}