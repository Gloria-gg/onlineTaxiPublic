package com.mashibing.servicedriveruser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashibing.internalcommon.dto.DriverUserWorkStatus;
import feign.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Gloria
 * @since 2023-05-11
 */
@Repository
public interface DriverUserWorkStatusMapper extends BaseMapper<DriverUserWorkStatus> {

    /**
     * 通过城市代码搜索有多少司机个数
     *
     * @param cityCode
     * @return
     */
    public int selectDriverCountByCityCode(@Param("cityCode") String cityCode);
}
