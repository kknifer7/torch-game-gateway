package xit.gateway.admin.utils;


import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import xit.gateway.utils.UUIDUtils;

import java.io.Serializable;

public class MyIdGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return UUIDUtils.getRandom();
    }
}
