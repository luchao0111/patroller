package com.preapm.agent.plugin.interceptor.filter;

import java.util.Arrays;

import com.preapm.sdk.zipkin.ZipkinClientContext;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class BackLogFilter extends Filter<ILoggingEvent> {

	@Override
	public FilterReply decide(ILoggingEvent event) {

		Object[] argumentArray = event.getArgumentArray();
		if (argumentArray == null || argumentArray.length == 0) {
			return FilterReply.ACCEPT;
		}

		String message = event.getMessage();
		if (message.startsWith("pre.")) {
			try {
				ZipkinClientContext.getClient().sendBinaryAnnotation(message, Arrays.toString(argumentArray), null);
				;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return FilterReply.ACCEPT;
	}

}