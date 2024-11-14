package reactiv.end.to.end.query.bean;

import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.function.Supplier;

public abstract class BaseServiceBean {

    protected <T, R> R copyProperties(T source, Supplier<R> targetSupplier) {
        R target = targetSupplier.get();
        assert target != null;
        assert source != null;

        RecordComponent[] recordComponents = source.getClass().getRecordComponents();
        if(recordComponents == null || recordComponents.length == 0){
            BeanUtils.copyProperties(source, target);
        }else {
            for (RecordComponent recordComponent : recordComponents) {
                try {
                    Method targetMethod = target.getClass().getDeclaredMethod(recordComponent.getName(), recordComponent.getType());
                    Object value = recordComponent.getAccessor().invoke(source);
                    targetMethod.invoke(target, value);
                }catch (Exception ignore){
                    System.out.println(ignore);
                }
            }
        }
        return target;
    }
}
