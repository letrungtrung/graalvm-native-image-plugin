/*
 * Copyright 2020 Shinya Mochida
 *
 * Licensed under the Apache License,Version2.0(the"License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,software
 * Distributed under the License is distributed on an"AS IS"BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mikeneck.graalvm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class JniConfigTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final TestJsonReader reader = new TestJsonReader();

    @Test
    public void jsonWithContents() throws IOException {
        try (InputStream inputStream = reader.configJsonResource("config/jni-config-1.json")) {
            JniConfig jniConfig = objectMapper.readValue(inputStream, JniConfig.class);
            assertThat(jniConfig, hasItems(
                    new ClassUsage(
                            IllegalArgumentException.class, 
                            new MethodUsage("<init>", "java.lang.String")),
                    new ClassUsage(
                            ArrayList.class, 
                            new MethodUsage("<init>"), 
                            MethodUsage.of("add", Object.class))
            ));
        }
    }

    @Test
    public void jsonWithoutContents() throws IOException {
        try (InputStream inputStream = reader.configJsonResource("config/jni-config-2.json")) {
            JniConfig jniConfig = objectMapper.readValue(inputStream, JniConfig.class);
            assertThat(jniConfig, is(Collections.emptySortedSet()));
        }
    }
}
