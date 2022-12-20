package com.zerobase.fastlms;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class test01 {


    public static void main(String[] args) {
        String value="2022-12-25";

        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try{
            System.out.println(LocalDate.parse(value,formatter));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
