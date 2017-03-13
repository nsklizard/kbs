package ru;

/**
 * Created by lizard on 12.03.2017.
 */
public class PaymentConfirmation {
    private Long bookingId;
    private String token;

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
