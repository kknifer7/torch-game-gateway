package xit.gateway.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xit.gateway.admin.domain.CallLog;

public interface CallLogRepository extends JpaRepository<CallLog, String> {

}