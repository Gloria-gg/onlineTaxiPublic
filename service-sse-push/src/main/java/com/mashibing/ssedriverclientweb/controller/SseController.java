package com.mashibing.ssedriverclientweb.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 1:58 PM 5/25/23
 */
@RestController
public class SseController {

    Map<String, SseEmitter> map = new HashMap<>();

    /**
     * 建立连接
     *
     * @param userId
     * @return
     */
    @GetMapping("/connect/{userId}")
    public SseEmitter connect(@PathVariable String userId) {
        System.out.println("userId" +
                "是：" + userId);

        SseEmitter sseEmitter = new SseEmitter(0L);
        map.put(userId, sseEmitter);

        return sseEmitter;
    }

    /**
     * 发送连接
     *
     * @param userId
     * @param content
     * @return
     */
    @GetMapping("/push")
    public String push(@RequestParam String userId, @RequestParam String content) {
        try {
            if (map.containsKey(userId)) {
                map.get(userId).send(content);
            }else{
                return "此用户不存在！";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "用户：" + userId + " 发送的消息内容是：" + content;
    }

    /**
     * 关闭连接
     *
     * @param userId
     * @return
     */
    @GetMapping("/close/{userId}")
    public String close(@PathVariable String userId) {
        if (map.containsKey(userId)) {
            map.remove(userId);
            System.out.println("已关闭用户：" + userId + " 的连接！");
        }

        return "关闭连接成功！";
    }
}
