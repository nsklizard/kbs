package ru;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by lizard on 12.03.2017.
 */
public interface BookingRepository extends CrudRepository<Booking, Long> {

    @Query("select coalesce(sum(b.kartsNumber),0) from Booking b where b.startTime = :date")
    int getReservedKartsNumber(@Param("date") Date startTime);

    List<Booking> findByStartTimeBetween(Date start, Date end);
}
