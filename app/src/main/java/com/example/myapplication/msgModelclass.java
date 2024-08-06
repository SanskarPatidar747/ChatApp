package com.example.myapplication;

public class msgModelclass {
    String message;
    String senderid;
    long timeStamo;

    public msgModelclass() {
    }

    public msgModelclass(String message, String senderid, long timeStamo) {
        this.message = message;
        this.senderid = senderid;
        this.timeStamo = timeStamo;
    }

    public long getTimeStamo() {
        return timeStamo;
    }

    public void setTimeStamo(long timeStamo) {
        this.timeStamo = timeStamo;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
