package POJO;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter

public class BookingData {

    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    private String additionalneeds;
    private Map<String,String> bookingdates;

}
