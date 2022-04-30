package xit.gateway.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xit.gateway.admin.domain.CallLog;
import xit.gateway.admin.repository.CallLogRepository;
import xit.gateway.admin.service.CallLogService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CallLogServicelmpl implements CallLogService {
    private final CallLogRepository callLogRepository;

    @Override
    public List<CallLog> findAll() {
        List<CallLog> all = callLogRepository.findAll();
        return all;
    }
}
