package com.devstack.lms.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AllRegistrations {
    private StringProperty registeredDate;
    private StringProperty studentName;
    private StringProperty paymentType;

    public AllRegistrations(String registeredDate, String studentName, String paymentType) {
        this.registeredDate = new SimpleStringProperty(registeredDate);
        this.studentName = new SimpleStringProperty(studentName);
        this.paymentType = new SimpleStringProperty(paymentType);
    }

    public String getRegisteredDate() {
        return registeredDate.get();
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate.set(registeredDate);
    }

    public StringProperty registeredDateProperty() {
        return registeredDate;
    }

    public String getStudentName() {
        return studentName.get();
    }

    public void setStudentName(String studentName) {
        this.studentName.set(studentName);
    }

    public StringProperty studentNameProperty() {
        return studentName;
    }

    public String getPaymentType() {
        return paymentType.get();
    }

    public void setPaymentType(String paymentType) {
        this.paymentType.set(paymentType);
    }

    public StringProperty paymentTypeProperty() {
        return paymentType;
    }
}
