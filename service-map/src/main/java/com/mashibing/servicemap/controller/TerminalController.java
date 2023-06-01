package com.mashibing.servicemap.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.servicemap.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Gloria
 * @Description: 根据轨迹service进行终端terminal等操作
 * @Date: Created in 11:12 AM 5/15/23
 */
@RestController
@RequestMapping("/terminal")
public class TerminalController {
    @Autowired
    private TerminalService terminalService;

    @PostMapping("/add")
    public ResponseResult<TerminalResponse> addTerminal(String name, String desc) {
        return terminalService.addTerminal(name, desc);
    }

    @PostMapping("/aroundsearch")
    public ResponseResult<List<TerminalResponse>> aroundSearch(String center, Integer radius) {
        return terminalService.aroundSearch(center, radius);
    }

    /**
     * 根据key、sid、tid、开始、结束时间 进行轨迹点查询
     *
     * @param tid
     * @param starttime
     * @param endtime
     * @return
     */
    @GetMapping("/trsearch")
    public ResponseResult trSearch(String tid, Long starttime, Long endtime) {
        return terminalService.trSearch(tid, starttime, endtime);
    }
}
