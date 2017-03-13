package ru;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by lizard on 12.03.2017.
 */
public class BookingAnonymized {
    private Long id;
    private int kartsNumber;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm Z")
    private Date startTime;

    public BookingAnonymized() {

    }
    public BookingAnonymized(Long id, int kartsNumber, Date startTime) {
        this.id = id;
        this.kartsNumber = kartsNumber;
        this.startTime = startTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getKartsNumber() {
        return kartsNumber;
    }

    public void setKartsNumber(int kartsNumber) {
        this.kartsNumber = kartsNumber;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
