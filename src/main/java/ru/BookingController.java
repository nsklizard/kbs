package ru;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lizard on 12.03.2017.
 */
@RestController
public class BookingController {
    @Autowired
    private BookingRepository bookingRepository;

    @RequestMapping(path = "/book", method = RequestMethod.POST)
    public Pair<ReturnCode, Long> book(@RequestBody Booking booking) {
        if (booking.getCell() != null && !booking.getCell().isEmpty()) {
            if (bookingRepository.getReservedKartsNumber(booking.getStartTime()) < BookingConstants.MAX_KART_NUMBER) {
                Booking saved = bookingRepository.save(booking);
                return Pair.of(ReturnCode.OK, saved.getId());
            } else {
                return Pair.of(ReturnCode.ALL_SLOTS_RESERVED, -1L);
            }
        }
        return Pair.of(ReturnCode.FAILED, -2L);
    }

    @RequestMapping("/checkDate")
    public List<BookingAnonymized> getReservedOnDate(@RequestParam("date") @DateTimeFormat(pattern = "dd.MM.yyyy") Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, 23);
        calendar.add(Calendar.MINUTE, 59);
        calendar.add(Calendar.SECOND, 59);

        List<Booking> bookings = bookingRepository.findByStartTimeBetween(date, calendar.getTime());

        if (bookings != null) {
            return bookings.stream().map(b -> new BookingAnonymized(b.getId(), b.getKartsNumber(), b.getStartTime())).collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    @RequestMapping(value = "/confirmPayment", method = RequestMethod.POST)
    public Pair<ReturnCode, Long> confirmPayment(@RequestBody PaymentConfirmation paymentConfirmation) {
        if (paymentConfirmation != null
                && paymentConfirmation.getBookingId() != null
                && paymentConfirmation.getToken() != null
                && !paymentConfirmation.getToken().isEmpty()) {
            Booking booking = bookingRepository.findOne(paymentConfirmation.getBookingId());
            booking.setPayment(paymentConfirmation.getToken());
            Booking saved = bookingRepository.save(booking);
            return Pair.of(ReturnCode.OK, saved.getId());
        }
        return Pair.of(ReturnCode.FAILED, -1L);
    }
}
