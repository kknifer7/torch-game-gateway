package xit.gateway.api.fuse;

import xit.gateway.pojo.CallLog;

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
    void fuseIfNecessary(CallLog record);

    /**
     * 重新获取应用熔断器配置
     */
    void flush();
}
