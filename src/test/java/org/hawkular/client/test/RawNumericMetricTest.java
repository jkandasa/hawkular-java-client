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
package org.hawkular.client.test;

import java.time.Duration;
import java.util.List;

import org.hawkular.metrics.core.api.NumericData;
import org.hawkular.metrics.core.api.Tenant;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

public class RawNumericMetricTest extends BaseTest {

    private final Tenant tenant = randomTenant();
    private final List<NumericData> expectedMetricData = NumericMetricGenerator.gen(1);

    public RawNumericMetricTest() throws Exception {
        super();
    }


    @Test
    public void addDataTest() throws Exception {
        client().metrics().addNumericMetricData(tenant.getId(), "cpu", expectedMetricData);
    }

    @Test(dependsOnMethods="addDataTest")
    public void getDataUsingDefaultParamsTest() throws Exception {
        List<NumericData> actual = client().metrics().getNumericMetricData(
                    tenant.getId(),
                    "cpu",
                    System.currentTimeMillis() - Duration.ofHours(8).toMillis(),
                    System.currentTimeMillis()
                    );

        Reporter.log(expectedMetricData.toString());
        Reporter.log(actual.toString());

        Assert.assertEquals(Lists.reverse(actual), expectedMetricData);
    }
}
