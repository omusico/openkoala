package org.openkoala.organisation.application.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.openkoala.organisation.application.JobApplication;
import org.openkoala.organisation.domain.Job;
import org.springframework.transaction.annotation.Transactional;

import com.dayatang.querychannel.service.QueryChannelService;
import com.dayatang.querychannel.support.Page;

@Named
@Transactional
public class JobApplicationImpl implements JobApplication {

	@Inject
	private QueryChannelService queryChannel;
	
	@Override
	public Page<Job> pagingQueryJobs(Job jobSearchExample, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();

		StringBuilder jpql = new StringBuilder("select _job from Job _job where _job.createDate <= ? and _job.terminateDate > ?");
		Date now = new Date();
		conditionVals.add(now);
		conditionVals.add(now);

		if (!StringUtils.isBlank(jobSearchExample.getName())) {
			jpql.append(" and _job.name like ?");
			conditionVals.add(MessageFormat.format("%{0}%", jobSearchExample.getName()));
		}
		if (!StringUtils.isBlank(jobSearchExample.getSn())) {
			jpql.append(" and _job.sn like ?");
			conditionVals.add(MessageFormat.format("%{0}%", jobSearchExample.getSn()));
		}
		if (!StringUtils.isBlank(jobSearchExample.getDescription())) {
			jpql.append(" and _job.description like ?");
			conditionVals.add(MessageFormat.format("%{0}%", jobSearchExample.getSn()));
		}

		return queryChannel.queryPagedResultByPageNo(jpql.toString(),conditionVals.toArray(), currentPage, pageSize);
	}

}