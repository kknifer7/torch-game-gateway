package xit.gateway.deacon.dao;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import xit.gateway.pojo.CallLog;

@Repository
public interface CallLogDAO extends ReactiveCrudRepository<CallLog, String> {
    @Modifying
    @Query(
            "insert into call_log" +
                    "(id, gateway_id, gateway_host, gateway_port, gateway_uri," +
                    "route_id, route_desc, route_host, route_port, route_url," +
                    "route_creation_datetime, route_update_datetime," +
                    "service_name, success, start_timestamp, consume_time)" +
                    "values" +
                    "(:#{#callLog.id}," +
                    ":#{#callLog.gatewayId}," +
                    ":#{#callLog.gatewayHost}, :#{#callLog.gatewayPort}," +
                    ":#{#callLog.gatewayUri}," +
                    ":#{#callLog.routeId}, :#{#callLog.routeDesc}," +
                    ":#{#callLog.routeHost}, :#{#callLog.routePort}, :#{#callLog.routeUrl}," +
                    ":#{#callLog.routeCreationDatetime}, :#{#callLog.routeUpdateDatetime}," +
                    ":#{#callLog.serviceName}, :#{#callLog.success}," +
                    ":#{#callLog.timestamp}, :#{#callLog.consumeTime})"
    )
    Mono<Void> insert(@Param("callLog") CallLog callLog);
}
