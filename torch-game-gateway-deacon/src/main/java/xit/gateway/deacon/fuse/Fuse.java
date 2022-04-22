package xit.gateway.deacon.fuse;

import xit.gateway.core.pojo.CallRecord;

/**
 * @author Knifer
 * Description: 熔断器
 * Date: 2022/04/17
 */
public interface Fuse {
    /**
     * 必要就熔断
     * @param record 调用记录
     */
    void fuseIfNecessary(CallRecord record);

    /**
     * 重新获取应用熔断器配置
     */
    void flush();
}
