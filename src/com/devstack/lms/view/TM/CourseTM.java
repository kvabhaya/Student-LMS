package com.devstack.lms.view.TM;

import javafx.scene.control.ButtonBar;

public class CourseTM { private String course_id;
    private String course_name;
    private double fee;
    private ButtonBar bar;

    public CourseTM() {
    }

    public CourseTM(String course_id, String course_name, double fee, ButtonBar bar) {
        this.course_id = course_id;
        this.course_name = course_name;
        this.fee = fee;
        this.bar = bar;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public ButtonBar getBar() {
        return bar;
    }

    public void setBar(ButtonBar bar) {
        this.bar = bar;
    }
}
