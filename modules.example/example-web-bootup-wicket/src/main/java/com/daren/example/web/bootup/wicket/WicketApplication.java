/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.daren.example.web.bootup.wicket;

import com.daren.core.web.component.dashboard.Dashboard;
import com.daren.core.web.component.dashboard.DashboardContextInitializer;
import com.daren.core.web.component.dashboard.DefaultDashboard;
import com.daren.core.web.component.dashboard.web.DashboardContext;
import com.daren.core.web.component.dashboard.widget.NoticeWidgetDescriptor;
import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.atmosphere.EventBus;
import org.apache.wicket.atmosphere.ResourceRegistrationListener;
import org.apache.wicket.atmosphere.config.AtmosphereLogLevel;
import org.apache.wicket.protocol.http.WebApplication;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 */
public class WicketApplication extends WebApplication {
	private EventBus eventBus;
    private Dashboard dashboard;

	public static WicketApplication get() {
		return (WicketApplication) Application.get();
	}

	@Override
	public Class<HomePage> getHomePage() {
		return HomePage.class;
	}

	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public void init() {
		super.init();
		eventBus = new EventBus(this);
		eventBus.getParameters().setLogLevel(AtmosphereLogLevel.DEBUG);
		eventBus.addRegistrationListener(new ResourceRegistrationListener() {

			@Override
			public void resourceUnregistered(String uuid) {
				System.out.println("Unregistered " + uuid);
			}

			@Override
			public void resourceRegistered(String uuid, Page page) {
				System.out.println("Registered " + uuid);
			}
		});

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(
				1, new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						Thread ret = new Thread(r);
						ret.setDaemon(true);
						return ret;
					}
				});
		final Runnable beeper = new Runnable() {
			@Override
			public void run() {
				try {
					eventBus.post(new Date());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		scheduler.scheduleWithFixedDelay(beeper, 2, 2, TimeUnit.SECONDS);

        //init dashboard
        DashboardContext dashboardContext = getDashboardContext();
        dashboardContext.getWidgetRegistry()
                .registerWidget(new NoticeWidgetDescriptor());
        initDashboard();
	}

    private DashboardContext getDashboardContext() {
        return getMetaData(DashboardContextInitializer.DASHBOARD_CONTEXT_KEY);
    }

    private void initDashboard() {
        dashboard = getDashboardContext().getDashboardPersiter().load();
        if (dashboard == null) {
            dashboard = new DefaultDashboard("default", "Default");
        }
    }
}
