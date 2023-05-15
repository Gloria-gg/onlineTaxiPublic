package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.servicemap.remote.TerminalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 11:14 AM 5/15/23
 */
@Service
public class TerminalService {
    @Autowired
    private TerminalClient terminalClient;

    public ResponseResult<TerminalResponse> addTerminal(String name) {
        return terminalClient.addTerminal(name);
    }
}
