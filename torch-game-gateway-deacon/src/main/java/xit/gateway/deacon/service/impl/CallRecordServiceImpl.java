package xit.gateway.deacon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xit.gateway.deacon.dao.CallRecordDAO;
import xit.gateway.deacon.service.CallRecordService;
import xit.gateway.core.pojo.CallRecord;
import xit.gateway.deacon.utils.UUIDUtils;

@Service
public class CallRecordServiceImpl implements CallRecordService {
    private final CallRecordDAO callRecordDAO;

    @Autowired
    public CallRecordServiceImpl(CallRecordDAO callRecordDAO) {
        this.callRecordDAO = callRecordDAO;
    }

    @Override
    public void add(CallRecord callRecord) {
        callRecord.setId(UUIDUtils.getRandom());
        callRecordDAO.insert(callRecord).subscribe();
    }
}
