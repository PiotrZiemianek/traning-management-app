package pl.sda.training.management.app.utils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

public class IdGenerator extends IdentityGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor s, Object obj) {
        if (obj == null)
        {
            throw new HibernateException(new NullPointerException());
        }
        final Serializable id = s.getEntityPersister(null, obj).getClassMetadata().getIdentifier(obj, s);
        return id == null? super.generate(s, obj) : id;
    }
}
