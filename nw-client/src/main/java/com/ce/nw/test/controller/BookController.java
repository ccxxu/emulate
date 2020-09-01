package com.ce.nw.test.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * @author ccx
 * @date 2019-03-03
 */
@Controller
@RequestMapping("/book")
public class BookController {

    @PreAuthorize("hasAuthority('book:list')")
    @GetMapping("/list")
    public String list() {

        return "book/list";
    }

    @GetMapping("/info")
    @ResponseBody
    public Principal info(Principal principal) {
        return principal;
    }

    @PreAuthorize("hasAuthority('member:save')")
    @ResponseBody
    @PostMapping("/add")
    public String add() {

        return "add";
    }

    @PreAuthorize("hasAuthority('member:detail')")
    @ResponseBody
    @GetMapping("/detail")
    public String detail() {
        return "detail";
    }
}
