package com.gomefinance.uploadImg.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gomefinance.uploadImg.service.PictureUploadService;
import com.gomefinance.uploadImg.service.TmAppMainService;

@Component
public class ScheduleJobs {

	@Autowired
	TmAppMainService tmAppMainService;
	@Autowired
	PictureUploadService  pictureUploadService;

	@Scheduled(cron = "0 0/1 * * * ?")
	public void fixedDelayJob() {
		pictureUploadService.uploadToPicture();
	}
}
