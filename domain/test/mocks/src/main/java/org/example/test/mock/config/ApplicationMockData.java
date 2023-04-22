package org.example.test.mock.config;

import java.util.Map;
import org.example.test.data.config.TestData;
import org.example.test.mock.config.failure.TestFailure;
import org.immutables.value.Value.Immutable;

@Immutable
public interface ApplicationMockData extends MockData<TestData> {

}
