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
    }
}
