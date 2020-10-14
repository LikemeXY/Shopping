package com.edu.portal.controller;

import com.edu.common.bean.SearchResult;
import com.edu.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")
    public String  search(String q, @RequestParam(value = "page",defaultValue = "1") int page,
                          Model model){
        try {
            q = new String(q.getBytes("iso8859-1"),"utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        SearchResult result = searchService.query(q,page);

        model.addAttribute("query",q);
        model.addAttribute("totalPages",result.getPageCount());
        model.addAttribute("itemList",result.getItemList());
        model.addAttribute("page",page);
        return "search";
    }
}
