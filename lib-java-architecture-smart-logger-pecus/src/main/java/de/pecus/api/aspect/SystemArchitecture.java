package de.pecus.api.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Configurable;

@Aspect
@Configurable
public class SystemArchitecture {

    /**
     * A join point is in the service layer if the method is defined
     * in a type in the de.pecus.api.services.impl package or any sub-package
     * under that.
     */
    @Pointcut("within(de.pecus.api.services.impl..*)")
    public void inServiceLayer() {}

    /**
     * A join point is in the data access layer if the method is defined
     * in a type in the de.pecus.api.repositories package or any sub-package
     * under that.
     */
    @Pointcut("within(de.pecus.api.repositories..*)")
    public void inDataAccessLayer() {}
    
    /**
     * A join point is in the web layer if the method is defined
     * in a type in the de.pecus.api.controllers.impl package or any sub-package
     * under that.
     */
    @Pointcut("execution(* de.pecus.api.controllers.impl.*.*(..))")
	public void controllers(){}
    
    /**
     * A join point is in the web layer if the method is defined
     * in a type in the de.pecus.api.controllers.impl package or any sub-package
     * under that.
     */
    @Pointcut("@annotation(de.pecus.api.annotation.Auditable)")
    public void auditable(){}
    
    /**
     * A join point is in the web layer if the method is defined
     * in a type in the de.pecus.api.controllers.impl package or any sub-package
     * under that.
     */
    @Pointcut("@annotation(de.pecus.api.annotation.ArchitectureComponent)")
    public void architectureComponent(){}
    
    /**
     * A join point is in the web layer if the method is defined
     * in a type in the de.pecus.api.controllers.impl package or any sub-package
     * under that.
     */
    @Pointcut("execution(* de.pecus.api.smartlogger.controllers.impl.*.*(..))")
    public void smartLoggerControllers(){}

    /**
     * A business service is the execution of any method defined on a service
     * interface. This definition assumes that interfaces are placed in the
     * "service" package, and that implementation types are in sub-packages.
     */
    @Pointcut("execution(* de.pecus.api.services.*.impl.*.*(..))")
    public void businessService() {}

    /**
     * A data access operation is the execution of any method defined on a
     * dao interface. This definition assumes that interfaces are placed in the
     * "de.pecus.api.repositories" package.
     */
    @Pointcut("execution(* de.pecus.api.repositories.*.*(..))")
    public void dataAccessOperation() {}
    
    /**
     * A business service is the execution of any method defined on a service
     * interface. This definition assumes that interfaces are placed in the
     * "service" package.
     */
    @Pointcut("execution(* de.pecus.api.services.impl.*.*(..))")
    public void businessServiceImpl() {}
    
}