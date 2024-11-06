package com.joe.jobms.job.external;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
public class Review {
    private Long id;
    private String title;
    private String description;
    private double rating;

}
