package com.acms.util;

import java.util.Collection;
import java.util.Map;

public class ValidateUtils {
	public static boolean isEmpty(String s) {
		return (s == null) || (s.length() == 0);
	}

	public static <E> boolean isEmpty(Collection<E> c) {
		return (c == null) || (c.size() == 0);
	}

	public static <E> boolean isEmpty(E[] a) {
		return (a == null) || (a.length == 0);
	}

	public static <K, E> boolean isEmpty(Map<K, E> m) {
		return (m == null) || (m.size() == 0);
	}

	public static boolean isEmpty(CharSequence c) {
		return (c == null) || (c.length() == 0);
	}

	public static boolean isNotEmpty(String s) {
		return (s != null) && (s.length() > 0);
	}

	public static <E> boolean isNotEmpty(Collection<E> c) {
		return (c != null) && (c.size() > 0);
	}

	public static <E> boolean isNotEmpty(E[] a) {
		return (a != null) && (a.length > 0);
	}

	public static <K, E> boolean isNotEmpty(Map<K, E> m) {
		return (m != null) && (m.size() > 0);
	}

	public static boolean isNotEmpty(CharSequence c) {
		return (c != null) && (c.length() > 0);
	}

	public static boolean isLetter(char c) {
		return Character.isLetter(c);
	}

	public static boolean isDigit(char c) {
		return Character.isDigit(c);
	}

	public static boolean isLetterOrDigit(char c) {
		return Character.isLetterOrDigit(c);
	}

	public static boolean isAlphanumeric(String s) {
		if (isEmpty(s))
			return false;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (!isLetterOrDigit(c))
				return false;
		}

		return true;
	}

	public static boolean isNumeric(String s) {
		if (isEmpty(s))
			return false;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (!isDigit(c))
				return false;
		}

		return true;
	}
}
