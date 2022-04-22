package xit.gateway.deacon.dao;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import xit.gateway.core.pojo.CallRecord;

@Repository
public interface CallRecordDAO extends ReactiveCrudRepository<CallRecord, String> {
    @Modifying
    @Query(
            "insert into call_record" +
                    "(id, gateway_host, gateway_port, gateway_uri," +
                    "route_id, route_desc, route_host, route_port, route_url," +
                    "route_creation_datetime, route_update_datetime," +
                    "service_id, success, start_timestamp, call_time)" +
                    "values" +
                    "(:#{#callRecord.id}," +
                    ":#{#callRecord.gatewayHost}, :#{#callRecord.gatewayPort}, :#{#callRecord.gatewayUri}," +
                    ":#{#callRecord.routeId}, :#{#callRecord.routeDesc}," +
                    ":#{#callRecord.routeHost}, :#{#callRecord.routePort}, :#{#callRecord.routeUrl}," +
                    ":#{#callRecord.routeCreationDatetime}, :#{#callRecord.routeUpdateDatetime}," +
                    ":#{#callRecord.serviceId}, :#{#callRecord.success}," +
                    ":#{#callRecord.timestamp}, :#{#callRecord.callTime})"
    )
    Mono<Void> insert(@Param("callRecord") CallRecord callRecord);
}
