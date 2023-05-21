package com.scalefocus.view.view.model;

public class ViewCount {

    private int countTown;
    private int countType;
    private int countCompany;
    private int countUsers;
    private int countComment;

    public ViewCount() {
    }

    public ViewCount(int countTown, int countType, int countCompany, int countUsers, int countComment) {
        this.countTown = countTown;
        this.countType = countType;
        this.countCompany = countCompany;
        this.countUsers = countUsers;
        this.countComment = countComment;
    }

    public int getCountTown() {
        return countTown;
    }

    public int getCountType() {
        return countType;
    }

    public int getCountCompany() {
        return countCompany;
    }

    public int getCountUsers() {
        return countUsers;
    }

    public int getCountComment() {
        return countComment;
    }

    @Override
    public String toString() {
        return "ViewCount{" +
                "countTown=" + countTown +
                ", countType=" + countType +
                ", countCompany=" + countCompany +
                ", countUsers=" + countUsers +
                ", countComment=" + countComment +
                '}';
    }
}
