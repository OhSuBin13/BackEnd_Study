package com.example.PagingAlarm.controller;

import com.example.PagingAlarm.domain.Gamer;
import com.example.PagingAlarm.domain.dto.SearchForm;
import com.example.PagingAlarm.domain.enumType.Rank;
import com.example.PagingAlarm.domain.enumType.SortType;
import com.example.PagingAlarm.repository.GamerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pagination-sort-example")
@RequiredArgsConstructor
public class GamerController {

    private final GamerRepository gamerRepository;

    @GetMapping("/gamers")
    public String getGamers(@RequestParam(required = false, defaultValue = "1") int page,
                            Model model, @ModelAttribute SearchForm form) {

        // 검색 조건
        if (form.getAgeGe() == null) form.setAgeGe(0);
        if (form.getAgeLe() == null) form.setAgeLe(999);
        if (form.getRankGe() == null) form.setRankGe(Rank.BRONZE);
        if (form.getRankLe() == null) form.setRankLe(Rank.DIAMOND);

        if (form.getAgeGe() > form.getAgeLe() || form.getRankGe().compareTo(form.getRankLe()) == 1) {
            model.addAttribute("message", "검색 조건 에러");
            model.addAttribute("nextUrl", "/pagination-sort-example/gamers");
            return "pagination_sort_example/message";
        }

        // 정렬 조건
        if (form.getSortType() == null) form.setSortType(SortType.ID_ASC);
        SortType sortType = form.getSortType();

        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        if (sortType == SortType.ID_ASC) pageRequest = PageRequest.of(page - 1, 7, Sort.by("id").ascending());
        else if (sortType == SortType.ID_DESC) pageRequest = PageRequest.of(page - 1, 7, Sort.by("id").descending());
        else if (sortType == SortType.AGE_ASC) pageRequest = PageRequest.of(page - 1, 7, Sort.by("age").ascending());
        else if (sortType == SortType.AGE_DESC) pageRequest = PageRequest.of(page - 1, 7, Sort.by("age").descending());
        else if (sortType == SortType.RANK_ASC) pageRequest = PageRequest.of(page - 1, 7, Sort.by("rank").ascending());
        else if (sortType == SortType.RANK_DESC) pageRequest = PageRequest.of(page - 1, 7, Sort.by("rank").descending());
        else {
            model.addAttribute("message", "정렬 조건 에러");
            model.addAttribute("nextUrl", "/pagination-sort-example/gamers");
            return "pagination_sort_example/message";
        }

        Page<Gamer> gamers =
                gamerRepository.findByAgeGreaterThanEqualAndAgeLessThanEqualAndRankGreaterThanEqualAndRankLessThanEqual(
                        form.getAgeGe(), form.getAgeLe(), form.getRankGe(), form.getRankLe(), pageRequest);

        model.addAttribute("gamers", gamers);
        model.addAttribute("searchForm", form);

        model.addAttribute("ranks", Rank.values());
        model.addAttribute("sortTypes", SortType.values());
        return "pagination_sort_example/home";
    }
}