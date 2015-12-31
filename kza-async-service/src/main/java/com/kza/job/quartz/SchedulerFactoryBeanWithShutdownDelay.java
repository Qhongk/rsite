package com.kza.job.quartz;

import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 重写SchedulerFactoryBean，覆盖destroy()方法 
 * 防止容器关闭导致的调度无法释放，造成泄露
 * 
 */
public class SchedulerFactoryBeanWithShutdownDelay extends SchedulerFactoryBean {

	@Override
	public void destroy() throws SchedulerException {
		super.destroy();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
