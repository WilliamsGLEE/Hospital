package com.example.hospital.table;

/**
 * Created by Wachsbeere on 2019/1/7.
 */

public class BedAndNurse {

    private int roomNo;
    private int bedNo;
    private int nurseNo;
    private String name;
    private String jobName;

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public int getBedNo() {
        return bedNo;
    }

    public void setBedNo(int bedNo) {
        this.bedNo = bedNo;
    }

    public int getNurseNo() {
        return nurseNo;
    }

    public void setNurseNo(int nurseNo) {
        this.nurseNo = nurseNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
