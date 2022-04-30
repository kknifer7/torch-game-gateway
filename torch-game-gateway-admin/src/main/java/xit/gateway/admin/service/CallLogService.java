package xit.gateway.admin.service;

import xit.gateway.admin.domain.CallLog;

import java.util.List;

public interface CallLogService {
    List<CallLog> findAll();
}
