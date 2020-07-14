package com.benz.web.logger;

import com.benz.web.security.AuthEntryPointJwt;
import org.slf4j.LoggerFactory;

public class Logger {

    public static final org.slf4j.Logger ERROR_LOGGER= LoggerFactory.getLogger(AuthEntryPointJwt.class);

}
