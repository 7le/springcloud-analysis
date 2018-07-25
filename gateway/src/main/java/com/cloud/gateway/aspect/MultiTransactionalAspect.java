package com.cloud.gateway.aspect;

import com.cloud.gateway.annotation.MultiTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Stack;

/**
 * 多数据源事务
 *
 * @author 7le
 */
@Slf4j
@Aspect
@Order(-7)
@Component
public class MultiTransactionalAspect {

    @Autowired
    private ApplicationContext applicationContext;

    @Around(value = "@annotation(multiTransactional)")
    public Object around(ProceedingJoinPoint pjp, MultiTransactional multiTransactional) throws Throwable {
        Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack = new Stack<>();
        Stack<TransactionStatus> transactionStatusStack = new Stack<>();
        try {
            if (!openTransaction(dataSourceTransactionManagerStack, transactionStatusStack, multiTransactional)) {
                return null;
            }
            Object ret = pjp.proceed();
            commit(dataSourceTransactionManagerStack, transactionStatusStack);
            return ret;
        } catch (Throwable e) {
            rollback(dataSourceTransactionManagerStack, transactionStatusStack);
            log.error(String.format(
                    "MultiTransactionalAspect catch exception class: %s method: %s detail:", pjp.getTarget().getClass().getSimpleName(),
                    pjp.getSignature().getName()), e);
            throw e;
        }
    }

    private boolean openTransaction(Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack,
                                    Stack<TransactionStatus> transactionStatusStack, MultiTransactional multiTransactional) {
        String[] transactionMangerNames = multiTransactional.values();
        if (ArrayUtils.isEmpty(multiTransactional.values())) {
            return false;
        }
        for (String beanName : transactionMangerNames) {
            DataSourceTransactionManager dataSourceTransactionManager = (DataSourceTransactionManager) applicationContext
                    .getBean(beanName);
            TransactionStatus transactionStatus = dataSourceTransactionManager
                    .getTransaction(new DefaultTransactionDefinition());
            transactionStatusStack.push(transactionStatus);
            dataSourceTransactionManagerStack.push(dataSourceTransactionManager);
        }
        return true;
    }


    private void commit(Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack,
                        Stack<TransactionStatus> transactionStatusStack) {
        while (!dataSourceTransactionManagerStack.isEmpty()) {
            dataSourceTransactionManagerStack.pop().commit(transactionStatusStack.pop());
        }
    }

    private void rollback(Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack,
                          Stack<TransactionStatus> transactionStatusStack) {
        while (!dataSourceTransactionManagerStack.isEmpty()) {
            dataSourceTransactionManagerStack.pop().rollback(transactionStatusStack.pop());
        }
    }
}
