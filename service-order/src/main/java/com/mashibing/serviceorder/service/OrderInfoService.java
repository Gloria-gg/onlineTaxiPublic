package com.mashibing.serviceorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.serviceorder.mapper.OrderInfoMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.ORB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Gloria
 * @since 2023-05-18
 */
@Service
@Slf4j
public class OrderInfoService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    public ResponseResult addOrder(OrderRequest orderRequest) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1584370883330600961L);
        List<OrderInfo> orderInfos = orderInfoMapper.selectByMap(map);
        log.info("从数据库中提取出来的数据是：" + orderInfos.get(0).toString());

        return ResponseResult.success("");
    }


}
