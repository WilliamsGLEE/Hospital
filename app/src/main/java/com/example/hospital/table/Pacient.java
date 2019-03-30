package com.example.hospital.table;

/**
 * Created by Wachsbeere on 2018/12/30.
 */

public class Pacient {

    private int hospitalizationNo;      //assigned
    private String name;
    private String sex;
    private String admissionDate;
    private String dischargedDate;
    private int roomNo;
    private int doctorNo;

    public int getDoctorNo() {
        return doctorNo;
    }

    public void setDoctorNo(int doctorNo) {
        this.doctorNo = doctorNo;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public int getHospitalizationNo() {
        return hospitalizationNo;
    }

    public void setHospitalizationNo(int hospitalizationNo) {
        this.hospitalizationNo = hospitalizationNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getDischargedDate() {
        return dischargedDate;
    }

    public void setDischargedDate(String dischargedDate) {
        this.dischargedDate = dischargedDate;
    }
}
