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

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.view.View;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Formats a list in a size-dependent way.
 * List patterns are specified by {@link Phrase Phrases} that define how to separate two elements
 * of the list (denoted by {@code {a}} and {@code {b}}. You can define 3 different separators:
 * <ul>
 * <li>separator for lists with exactly 2 elements (e.g. "first <b>and</b> second")</li>
 * <li>for lists with more than 2 elements, the separator for all but the last element (e.g.
 * "first<b>,</b> second<b>,</b> …")</li>
 * <li>for lists with more than 2 elements, the separator for the second-last and last element
 * (e.g. "second-last<b>, and</b> last")</li>
 * </ul>
 * List patterns are just {@code Phrases}, so preserve spans.
 * {@code ListPhrase} objects are not safe for concurrent use on multiple threads.
 *
 * <p>
 * E.g.
 * <pre>
 *  ListPhrase list = new ListPhrase("{a} and {b}", "{a}, {b}", "{a}, and {b}")
 *  list.format() → ""
 *  list.format("one") → "one"</li>
 *  list.format("one", "two") → "one and two"</li>
 *  list.format("one", "two", "three") → "one, two, and three"</li>
 * </pre>
 * The separator patterns don't have to be different:
 * <pre>
 *  String comma = "{a}, {b}"
 *  ListPhrase list = new ListPhrase(comma, comma, comma)
 *  list.format() → ""
 *  list.format("one") → "one"</li>
 *  list.format("one", "two") → "one, two"</li>
 *  list.format("one", "two", "three") → "one, two, three"</li>
 * </pre>
 */
public class ListPhrase {

  /**
   * Entry point into this API.
   *
   * @param pattern separator for all elements
   * @throws IllegalArgumentException if pattern contains any syntax errors.
   */
  public static ListPhrase from(Fragment fragment, @StringRes int pattern) {
    return ListPhrase.from(fragment.getResources(), pattern, pattern, pattern);
  }

  /**
   * Entry point into this API.
   *
   * @param twoElementPattern separator for 2-element lists
   * @param nonFinalElementPattern separator for non-final elements of lists with 3 or more elements
   * @param finalElementPattern separator for final elements in lists with 3 or more elements
   * @throws IllegalArgumentException if pattern contains any syntax errors.
   */
  public static ListPhrase from(Fragment fragment, @StringRes int twoElementPattern,
      @StringRes int nonFinalElementPattern, @StringRes int finalElementPattern) {
    return ListPhrase.from(fragment.getResources(), twoElementPattern, nonFinalElementPattern,
        finalElementPattern);
  }

  /**
   * Entry point into this API.
   *
   * @param pattern separator for all elements
   * @throws IllegalArgumentException if pattern contains any syntax errors.
   */
  public static ListPhrase from(View view, @StringRes int pattern) {
    return ListPhrase.from(view.getResources(), pattern, pattern, pattern);
  }

  /**
   * Entry point into this API.
   *
   * @param twoElementPattern separator for 2-element lists
   * @param nonFinalElementPattern separator for non-final elements of lists with 3 or more elements
   * @param finalElementPattern separator for final elements in lists with 3 or more elements
   * @throws IllegalArgumentException if pattern contains any syntax errors.
   */
  public static ListPhrase from(View view, @StringRes int twoElementPattern,
      @StringRes int nonFinalElementPattern, @StringRes int finalElementPattern) {
    return ListPhrase.from(view.getResources(), twoElementPattern, nonFinalElementPattern,
        finalElementPattern);
  }

  /**
   * Entry point into this API.
   *
   * @param pattern separator for all elements
   * @throws IllegalArgumentException if pattern contains any syntax errors.
   */
  public static ListPhrase from(Context context, @StringRes int pattern) {
    return ListPhrase.from(context.getResources(), pattern, pattern, pattern);
  }

  /**
   * Entry point into this API.
   *
   * @param twoElementPattern separator for 2-element lists
   * @param nonFinalElementPattern separator for non-final elements of lists with 3 or more elements
   * @param finalElementPattern separator for final elements in lists with 3 or more elements
   * @throws IllegalArgumentException if pattern contains any syntax errors.
   */
  public static ListPhrase from(Context context, @StringRes int twoElementPattern,
      @StringRes int nonFinalElementPattern, @StringRes int finalElementPattern) {
    return ListPhrase.from(context.getResources(), twoElementPattern, nonFinalElementPattern,
        finalElementPattern);
  }

  /**
   * Entry point into this API.
   *
   * @param pattern separator for all elements
   * @throws IllegalArgumentException if pattern contains any syntax errors.
   */
  public static ListPhrase from(Resources resources, @StringRes int pattern) {
    return ListPhrase.from(resources, pattern, pattern, pattern);
  }

  /**
   * Entry point into this API.
   *
   * @param twoElementPattern separator for 2-element lists
   * @param nonFinalElementPattern separator for non-final elements of lists with 3 or more elements
   * @param finalElementPattern separator for final elements in lists with 3 or more elements
   * @throws IllegalArgumentException if pattern contains any syntax errors.
   */
  public static ListPhrase from(Resources res, @StringRes int twoElementPattern,
      @StringRes int nonFinalElementPattern, @StringRes int finalElementPattern) {
    Phrase.from(res, 5);
    return ListPhrase.from(Phrase.from(res, twoElementPattern),
        Phrase.from(res, nonFinalElementPattern), Phrase.from(res, finalElementPattern));
  }

  /**
   * Entry point into this API.
   *
   * @param pattern separator for all elements
   * @throws IllegalArgumentException if pattern contains any syntax errors.
   */
  public static ListPhrase from(CharSequence pattern) {
    return ListPhrase.from(pattern, pattern, pattern);
  }

  /**
   * Entry point into this API.
   *
   * @param twoElementPattern separator for 2-element lists
   * @param nonFinalElementPattern separator for non-final elements of lists with 3 or more elements
   * @param finalElementPattern separator for final elements in lists with 3 or more elements
   * @throws IllegalArgumentException if pattern contains any syntax errors.
   */
  public static ListPhrase from(CharSequence twoElementPattern, CharSequence nonFinalElementPattern,
      CharSequence finalElementPattern) {
    return ListPhrase.from(Phrase.from(twoElementPattern), Phrase.from(nonFinalElementPattern),
        Phrase.from(finalElementPattern));
  }

  /**
   * Entry point into this API.
   *
   * @param twoElementPattern separator for 2-element lists
   * @param nonFinalElementPattern separator for non-final elements of lists with 3 or more elements
   * @param finalElementPattern separator for final elements in lists with 3 or more elements
   * @throws IllegalArgumentException if pattern contains any syntax errors.
   */
  public static ListPhrase from(Phrase twoElementPattern, Phrase nonFinalElementPattern,
      Phrase finalElementPattern) {
    return new ListPhrase(twoElementPattern, nonFinalElementPattern, finalElementPattern);
  }

  public interface Formatter<T> {
    CharSequence format(T item);
  }

  private final Phrase twoElementPattern;
  private final Phrase nonFinalElementPattern;
  private final Phrase finalElementPattern;

  private ListPhrase(Phrase twoElementPattern, Phrase nonFinalElementPattern,
      Phrase finalElementPattern) {

    this.twoElementPattern = validateListPattern("two-element", twoElementPattern);
    this.nonFinalElementPattern = validateListPattern("non-final", nonFinalElementPattern);
    this.finalElementPattern = validateListPattern("final", finalElementPattern);
  }

  public <T> CharSequence format(T... items) {
    return format(Arrays.asList(items));
  }

  public <T> CharSequence format(Iterable<T> items) {
    return format(items, null);
  }

  public <T> CharSequence format(Iterable<T> items, Formatter<T> formatter) {
    return formatList(asList(items), formatter);
  }

  /**
   * Throws an {@link IllegalArgumentException} if {@code pattern} doesn't contain exactly the keys
   * {@code {a}} and {@code {b}}.
   *
   * @return {@code pattern}
   */
  private static Phrase validateListPattern(String name, Phrase pattern) {
    try {
      pattern.put("a", "").put("b", "").format();
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(
          name + " list pattern should only contain keys {a} and {b}");
    }

    return pattern;
  }

  private <T> CharSequence formatList(List<T> list, Formatter<T> formatter) {
    switch (list.size()) {
      case 0:
        return "";
      case 1:
        return formatIfNotNull(list.get(0), formatter);
      case 2:
        return twoElementPattern //
            .put("a", formatIfNotNull(list.get(0), formatter)) //
            .put("b", formatIfNotNull(list.get(1), formatter)) //
            .format();
      default:
        Phrase phrase = nonFinalElementPattern;
        CharSequence result = formatIfNotNull(list.get(0), formatter);
        list = list.subList(1, list.size());

        // Fold over the list.
        while (!list.isEmpty()) {
          if (list.size() == 1) {
            phrase = finalElementPattern;
          }

          result = phrase //
              .put("a", result) //
              .put("b", formatIfNotNull(list.get(0), formatter)) //
              .format();
          list = list.subList(1, list.size());
        }
        return result;
    }
  }

  private static <T> List<T> asList(Iterable<T> iterable) {
    List<T> list;

    if (iterable instanceof List) {
      list = ((List<T>) iterable);
    } else {
      list = new LinkedList<>();
      for (T item : iterable) {
        list.add(item);
      }
    }

    return list;
  }

  private static <T> CharSequence formatIfNotNull(T item, Formatter<T> formatter) {
    if (formatter == null) {
      return item == null ? "" : item.toString();
    }

    return formatter.format(item);
  }
}
