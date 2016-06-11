package com.elliothutchinson.cms.service;

import java.time.ZonedDateTime;

public class CurrentTimeDateTimeService implements DateTimeService {

    @Override
    public ZonedDateTime getCurrentDateAndTime() {
        return ZonedDateTime.now();
    }
}
