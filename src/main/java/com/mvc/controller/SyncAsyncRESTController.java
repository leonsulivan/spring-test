package com.mvc.controller;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvc.services.VersionService;
import com.mvc.services.VersionService.Version;

/**
 * A simple REST controller showing the current version
 * @author Jerome
 */
@RestController
public class SyncAsyncRESTController {
	
	@Autowired
	private VersionService versionService;

	@RequestMapping("/version")
	public Version version() {
		return versionService.getVersion();
	}

	@Async
	@RequestMapping("/async-version")
	public Callable<Version> async() {
		return () -> {
			Thread.sleep(1000);
			return versionService.getVersion();
		};
	}
}
