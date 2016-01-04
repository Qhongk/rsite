package com.kza.job.quartz;

import com.kza.common.annotations.Locker;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;

/**
 * Created by rick01.kong on 2015/11/27.
 */
public abstract class AbsQuartzJobBean extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(QuartzJobBean.class);

    private Boolean hasLocker = null;

    private String methodName = null;

    private static final String EXECUTE_METHOD_NAME = "run";

//    @Autowired
//    private IBackendTaskLockService service;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        if (null == hasLocker) {
            try {
                Method method = this.getClass().getDeclaredMethod(EXECUTE_METHOD_NAME, JobExecutionContext.class);
                hasLocker = method.isAnnotationPresent(Locker.class) ? true : false;
            } catch (NoSuchMethodException e) {
                hasLocker = false;
                e.printStackTrace();
            }
            methodName = new StringBuilder(this.getClass().getName()).append(".").append(EXECUTE_METHOD_NAME).toString();
        }

//        if (hasLocker && StringUtils.isNotBlank(methodName) && service.isLocked(methodName)) {
        if (hasLocker && StringUtils.isNotBlank(methodName)) {
            logger.info("{} is locked!", methodName);
        } else {
            StopWatch watch = new StopWatch();
            logger.info("{} started!", methodName);
            watch.start();
            run(jobExecutionContext);
            watch.stop();
            logger.info("{} ended!, {} milliseconds elapsed!", methodName, watch.getTotalTimeMillis());
        }
    }

    protected abstract void run(JobExecutionContext jobExecutionContext) throws JobExecutionException;
}
