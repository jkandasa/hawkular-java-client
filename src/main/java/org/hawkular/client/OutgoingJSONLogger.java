/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hawkular.client;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
public class OutgoingJSONLogger implements ClientRequestFilter {

    private Logger LOG = LoggerFactory.getLogger(OutgoingJSONLogger.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        String method = requestContext.getMethod();
        if (LOG.isDebugEnabled()) {
            LOG.debug("HTTP: {}", method);
            LOG.debug("URI: {}", requestContext.getUri());
            LOG.debug("Data: {}", mapper.writerWithDefaultPrettyPrinter()
                                    .writeValueAsString(requestContext.getEntity()));
        }
    }

}