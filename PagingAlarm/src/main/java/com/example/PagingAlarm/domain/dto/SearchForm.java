package com.example.PagingAlarm.domain.dto;

import com.example.PagingAlarm.domain.enumType.Rank;
import com.example.PagingAlarm.domain.enumType.SortType;
import lombok.Data;

@Data
public class SearchForm {
    private Integer ageGe;
    private Integer ageLe;

    private Rank rankGe;
    private Rank rankLe;

    private SortType sortType;
}
