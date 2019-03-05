package com.example.netKit.piece.todo;

import com.example.netKit.persistence.Account;

import java.util.ArrayList;
import java.util.Date;

public class TimeLinePiece {


    private String ownerId  = Account.getUserId();
    private String content;
    private Date start;
    private Date end;
    private int type;

    public TimeLinePiece(String content, Date start, Date end, int type) {
        this.content = content;
        this.start = start;
        this.end = end;
        this.type = type;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

}
