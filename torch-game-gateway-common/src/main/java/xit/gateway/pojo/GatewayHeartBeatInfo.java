package xit.gateway.pojo;

/**
 *
 * @author Knifer
 * Description: deacon在接收到心跳后，创建的用于统计网关心跳情况的实体
 * Date: 2022/04/25
 */
public class GatewayHeartBeatInfo {
    private String gatewayId;
    private int loseBeatTimes;

    public GatewayHeartBeatInfo() {
    }

    public GatewayHeartBeatInfo(String gatewayId, int loseBeatTimes) {
        this.gatewayId = gatewayId;
        this.loseBeatTimes = loseBeatTimes;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public int getLoseBeatTimes() {
        return loseBeatTimes;
    }

    public void setLoseBeatTimes(int loseBeatTimes) {
        this.loseBeatTimes = loseBeatTimes;
    }
}
