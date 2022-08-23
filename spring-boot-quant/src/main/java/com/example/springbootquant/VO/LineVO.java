package com.example.springbootquant.VO;

import lombok.Data;

import java.util.List;

@Data
public class LineVO {
    private List<String> dates;
    private List<Integer> alarmCount;
    private List<Integer> startCount;
}
