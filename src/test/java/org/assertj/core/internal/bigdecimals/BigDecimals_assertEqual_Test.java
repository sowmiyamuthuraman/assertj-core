/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.core.internal.bigdecimals;

import static java.math.BigDecimal.*;

import static org.assertj.core.error.ShouldBeEqual.shouldBeEqual;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.FailureMessages.actualIsNull;


import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.BigDecimals;
import org.assertj.core.internal.BigDecimalsBaseTest;
import org.assertj.core.presentation.StandardRepresentation;
import org.junit.Test;


/**
 * Tests for <code>{@link BigDecimals#assertEqual(AssertionInfo, BigDecimal, bigdecimal)}</code>.
 * 
 * @author Joel Costigliola
 */
public class BigDecimals_assertEqual_Test extends BigDecimalsBaseTest {

  private static final BigDecimal ONE_WITH_3_DECIMALS = new BigDecimal("1.000");

  @Test
  public void should_fail_if_actual_is_null() {
    thrown.expectAssertionError(actualIsNull());
    bigDecimals.assertEqual(someInfo(), null, ONE);
  }

  @Test
  public void should_pass_if_big_decimals_are_equal() {
    bigDecimals.assertEqual(someInfo(), ONE, ONE);
  }

  @Test
  public void should_fail_if_big_decimals_are_not_equal() {
    AssertionInfo info = someInfo();
    try {
      bigDecimals.assertEqual(info, ONE_WITH_3_DECIMALS, ONE);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldBeEqual(ONE_WITH_3_DECIMALS, ONE, info.representation()));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

  @Test
  public void should_fail_if_actual_is_null_whatever_custom_comparison_strategy_is() {
    thrown.expectAssertionError(actualIsNull());
    bigDecimalsWithComparatorComparisonStrategy.assertEqual(someInfo(), null, ONE);
  }

  @Test
  public void should_pass_if_big_decimals_are_equal_according_to_custom_comparison_strategy() {
    bigDecimalsWithComparatorComparisonStrategy.assertEqual(someInfo(), ONE_WITH_3_DECIMALS, ONE);
  }

  @Test
  public void should_fail_if_big_decimals_are_not_equal_according_to_custom_comparison_strategy() {
    AssertionInfo info = someInfo();
    try {
      bigDecimalsWithComparatorComparisonStrategy.assertEqual(info, TEN, ONE);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldBeEqual(TEN, ONE, comparatorComparisonStrategy,
          new StandardRepresentation()));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }
}
