/*
 * Copyright (C) 2013 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.squareup.phrase;

import com.squareup.phrase.ListPhrase.Formatter;
import edu.emory.mathcs.backport.java.util.Collections;
import java.util.Arrays;
import java.util.HashSet;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class) @Config(manifest = Config.NONE)
public class ListPhraseTest {
  private final static String twoElementPattern = "{a} and {b}";
  private final static String nonFinalElementPattern = "{a}, {b}";
  private final static String finalElementPattern = "{a}, and {b}";

  @Rule public ExpectedException thrown = ExpectedException.none();

  ListPhrase phrase;

  @Before public void setUp() {
    phrase = ListPhrase.from(twoElementPattern, nonFinalElementPattern, finalElementPattern);
  }

  @Test public void formatEmptyListReturnsEmptyString() {
    assertThat(phrase.format(Collections.emptyList()).length()).isEqualTo(0);
  }

  @Test public void formatOneItemReturnsIdentity() {
    assertThat(format("one")).isEqualTo("one");
  }

  @Test public void formatTwoItemsUsesTwoElementPattern() {
    assertThat(format("one", "two")).isEqualTo("one and two");
  }

  @Test public void formatThreeItemsUsesThreeElementPattern() {
    assertThat(format("one", "two", "three")).isEqualTo("one, two, and three");
  }

  @Test public void formatMoreThanThreeItems() {
    assertThat(format("one", "two", "three", "four")).isEqualTo(
        "one, two, three, and four");
  }

  @Test public void formatWorksWithNonListIterables() {
    assertThat(format(new HashSet<>(Arrays.asList("item")))).isEqualTo("item");
  }

  @Test public void formatWorksWithOneNull() {
    assertThat(format((String) null)).isEqualTo("");
  }

  @Test public void formatWorksWithTwoNulls() {
    assertThat(format(Arrays.asList(null, null))).isEqualTo(" and ");
  }

  @Test public void formatWorksWithThreeNulls() {
    assertThat(format(Arrays.asList(null, null, null))).isEqualTo(", , and ");
  }

  @Test public void formatNonCharSequenceUsesToString() {
    assertThat(format(1, 2, 3)).isEqualTo("1, 2, and 3");
  }

  @Test public void formatWithFormatter() {
    Formatter<Integer> formatter = new Formatter<Integer>() {
      @Override public CharSequence format(Integer item) {
        return String.format("0x%d", item);
      }
    };

    assertThat(phrase.format(Arrays.asList(1, 2, 3), formatter).toString()).isEqualTo(
        "0x1, 0x2, and 0x3");
  }

  @Test public void formatThrowsIllegalArgumentExceptionForPatternWithInvalidKeys() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("list pattern should only contain keys {a} and {b}");

    ListPhrase.from("{one}{two}");
  }

  @Test public void formatThrowsIllegalArgumentExceptionForPatternWithMissingKey() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("list pattern should only contain keys {a} and {b}");

    ListPhrase.from("{a}");
  }

  private String format(Iterable<?> items) {
    return phrase.format(items).toString();
  }

  private String format(Object... items) {
    // Have to return a String, comparing CharSequences of different concrete types doesn't work in FEST.
    return phrase.format(items).toString();
  }
}
