package com.shubham.mongoDB.Entities;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Apiresponce {

    public Current current;

    @Getter
    @Setter
    public class Current{
        public int last_updated_epoch;
        public String last_updated;
        public double temp_c;
        public double temp_f;
        public int is_day;
        public double feelslike_c;
        public double feelslike_f;
        public Condition condition;
    }
}
class Condition{
    public String text;
    public String icon;
    public int code;
}

class Location{
    public String name;
    public String region;
    public String country;
    public double lat;
    public double lon;
    public String tz_id;
    public int localtime_epoch;
    public String localtime;
}
