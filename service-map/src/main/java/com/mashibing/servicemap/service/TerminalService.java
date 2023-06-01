package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.servicemap.remote.TerminalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 11:14 AM 5/15/23
 */
@Service
public class TerminalService {
    @Autowired
    private TerminalClient terminalClient;

    public ResponseResult<TerminalResponse> addTerminal(String name, String desc) {
        return terminalClient.addTerminal(name, desc);
    }

    public ResponseResult<List<TerminalResponse>> aroundSearch(String center, Integer radius) {
        return terminalClient.aroundSearch(center, radius);
    }

    public ResponseResult trSearch(String tid, Long startTime, Long endTime) {
        return terminalClient.trSearch(tid, startTime, endTime);
    }
}
