package xit.gateway.deacon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xit.gateway.api.service.CallLogService;
import xit.gateway.deacon.dao.CallLogDAO;
import xit.gateway.pojo.CallLog;
import xit.gateway.utils.UUIDUtils;

@Service
public class CallLogServiceImpl implements CallLogService {
    private final CallLogDAO callLogDAO;

    @Autowired
    public CallLogServiceImpl(CallLogDAO callLogDAO) {
        this.callLogDAO = callLogDAO;
    }

    @Override
    public void add(CallLog callLog) {
        callLog.setId(UUIDUtils.getRandom());
        callLogDAO.insert(callLog).subscribe();
    }
}
