/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.lang.reflect.Array;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 */
public class ArrayUtil {

	public static boolean[] append(boolean[]... arrays) {
		int length = 0;

		for (boolean[] array : arrays) {
			length += array.length;
		}

		boolean[] newArray = new boolean[length];

		int previousLength = 0;

		for (boolean[] array : arrays) {
			System.arraycopy(array, 0, newArray, previousLength, array.length);

			previousLength += array.length;
		}

		return newArray;
	}

	public static boolean[] append(boolean[] array, boolean value) {
		boolean[] newArray = new boolean[array.length + 1];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = value;

		return newArray;
	}

	public static byte[] append(byte[]... arrays) {
		int length = 0;

		for (byte[] array : arrays) {
			length += array.length;
		}

		byte[] newArray = new byte[length];

		int previousLength = 0;

		for (byte[] array : arrays) {
			System.arraycopy(array, 0, newArray, previousLength, array.length);

			previousLength += array.length;
		}

		return newArray;
	}

	public static byte[] append(byte[] array, byte value) {
		byte[] newArray = new byte[array.length + 1];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = value;

		return newArray;
	}

	public static char[] append(char[]... arrays) {
		int length = 0;

		for (char[] array : arrays) {
			length += array.length;
		}

		char[] newArray = new char[length];

		int previousLength = 0;

		for (char[] array : arrays) {
			System.arraycopy(array, 0, newArray, previousLength, array.length);

			previousLength += array.length;
		}

		return newArray;
	}

	public static char[] append(char[] array, char value) {
		char[] newArray = new char[array.length + 1];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = value;

		return newArray;
	}

	public static double[] append(double[]... arrays) {
		int length = 0;

		for (double[] array : arrays) {
			length += array.length;
		}

		double[] newArray = new double[length];

		int previousLength = 0;

		for (double[] array : arrays) {
			System.arraycopy(array, 0, newArray, previousLength, array.length);

			previousLength += array.length;
		}

		return newArray;
	}

	public static double[] append(double[] array, double value) {
		double[] newArray = new double[array.length + 1];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = value;

		return newArray;
	}

	public static float[] append(float[]... arrays) {
		int length = 0;

		for (float[] array : arrays) {
			length += array.length;
		}

		float[] newArray = new float[length];

		int previousLength = 0;

		for (float[] array : arrays) {
			System.arraycopy(array, 0, newArray, previousLength, array.length);

			previousLength += array.length;
		}

		return newArray;
	}

	public static float[] append(float[] array, float value) {
		float[] newArray = new float[array.length + 1];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = value;

		return newArray;
	}

	public static int[] append(int[]... arrays) {
		int length = 0;

		for (int[] array : arrays) {
			length += array.length;
		}

		int[] newArray = new int[length];

		int previousLength = 0;

		for (int[] array : arrays) {
			System.arraycopy(array, 0, newArray, previousLength, array.length);

			previousLength += array.length;
		}

		return newArray;
	}

	public static int[] append(int[] array, int value) {
		int[] newArray = new int[array.length + 1];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = value;

		return newArray;
	}

	public static long[] append(long[]... arrays) {
		int length = 0;

		for (long[] array : arrays) {
			length += array.length;
		}

		long[] newArray = new long[length];

		int previousLength = 0;

		for (long[] array : arrays) {
			System.arraycopy(array, 0, newArray, previousLength, array.length);

			previousLength += array.length;
		}

		return newArray;
	}

	public static long[] append(long[] array, long value) {
		long[] newArray = new long[array.length + 1];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = value;

		return newArray;
	}

	public static short[] append(short[]... arrays) {
		int length = 0;

		for (short[] array : arrays) {
			length += array.length;
		}

		short[] newArray = new short[length];

		int previousLength = 0;

		for (short[] array : arrays) {
			System.arraycopy(array, 0, newArray, previousLength, array.length);

			previousLength += array.length;
		}

		return newArray;
	}

	public static short[] append(short[] array, short value) {
		short[] newArray = new short[array.length + 1];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = value;

		return newArray;
	}

	public static <T> T[] append(T[]... arrays) {
		int length = 0;

		for (T[] array : arrays) {
			length += array.length;
		}

		Class<?> arraysClass = arrays[0].getClass();

		T[] newArray = (T[])Array.newInstance(
			arraysClass.getComponentType(), length);

		int previousLength = 0;

		for (T[] array : arrays) {
			System.arraycopy(array, 0, newArray, previousLength, array.length);

			previousLength += array.length;
		}

		return newArray;
	}

	public static <T> T[] append(T[] array, T value) {
		Class<?> arrayClass = array.getClass();

		T[] newArray = (T[])Array.newInstance(
			arrayClass.getComponentType(), array.length + 1);

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[array.length] = value;

		return newArray;
	}

	public static <T> T[] append(T[] array1, T[] array2) {
		Class<?> array1Class = array1.getClass();

		T[] newArray = (T[])Array.newInstance(
			array1Class.getComponentType(), array1.length + array2.length);

		System.arraycopy(array1, 0, newArray, 0, array1.length);

		System.arraycopy(array2, 0, newArray, array1.length, array2.length);

		return newArray;
	}

	public static <T> T[][] append(T[][] array1, T[] value) {
		Class<?> array1Class = array1.getClass();

		T[][] newArray = (T[][])Array.newInstance(
			array1Class.getComponentType(), array1.length + 1);

		System.arraycopy(array1, 0, newArray, 0, array1.length);

		newArray[array1.length] = value;

		return newArray;
	}

	public static <T> T[][] append(T[][] array1, T[][] array2) {
		Class<?> array1Class = array1.getClass();

		T[][] newArray = (T[][])Array.newInstance(
			array1Class.getComponentType(), array1.length + array2.length);

		System.arraycopy(array1, 0, newArray, 0, array1.length);
		System.arraycopy(array2, 0, newArray, array1.length, array2.length);

		return newArray;
	}

	public static boolean[] clone(boolean[] array) {
		boolean[] newArray = new boolean[array.length];

		System.arraycopy(array, 0, newArray, 0, array.length);

		return newArray;
	}

	public static boolean[] clone(boolean[] array, int from, int to) {
		boolean[] newArray = new boolean[to - from];

		System.arraycopy(
			array, from, newArray, 0,
			Math.min(array.length - from, newArray.length));

		return newArray;
	}

	public static byte[] clone(byte[] array) {
		byte[] newArray = new byte[array.length];

		System.arraycopy(array, 0, newArray, 0, array.length);

		return newArray;
	}

	public static byte[] clone(byte[] array, int from, int to) {
		byte[] newArray = new byte[to - from];

		System.arraycopy(
			array, from, newArray, 0,
			Math.min(array.length - from, newArray.length));

		return newArray;
	}

	public static char[] clone(char[] array) {
		char[] newArray = new char[array.length];

		System.arraycopy(array, 0, newArray, 0, array.length);

		return newArray;
	}

	public static char[] clone(char[] array, int from, int to) {
		char[] newArray = new char[to - from];

		System.arraycopy(
			array, from, newArray, 0,
			Math.min(array.length - from, newArray.length));

		return newArray;
	}

	public static double[] clone(double[] array) {
		double[] newArray = new double[array.length];

		System.arraycopy(array, 0, newArray, 0, array.length);

		return newArray;
	}

	public static double[] clone(double[] array, int from, int to) {
		double[] newArray = new double[to - from];

		System.arraycopy(
			array, from, newArray, 0,
			Math.min(array.length - from, newArray.length));

		return newArray;
	}

	public static float[] clone(float[] array) {
		float[] newArray = new float[array.length];

		System.arraycopy(array, 0, newArray, 0, array.length);

		return newArray;
	}

	public static float[] clone(float[] array, int from, int to) {
		float[] newArray = new float[to - from];

		System.arraycopy(
			array, from, newArray, 0,
			Math.min(array.length - from, newArray.length));

		return newArray;
	}

	public static int[] clone(int[] array) {
		int[] newArray = new int[array.length];

		System.arraycopy(array, 0, newArray, 0, array.length);

		return newArray;
	}

	public static int[] clone(int[] array, int from, int to) {
		int[] newArray = new int[to - from];

		System.arraycopy(
			array, from, newArray, 0,
			Math.min(array.length - from, newArray.length));

		return newArray;
	}

	public static long[] clone(long[] array) {
		long[] newArray = new long[array.length];

		System.arraycopy(array, 0, newArray, 0, array.length);

		return newArray;
	}

	public static long[] clone(long[] array, int from, int to) {
		long[] newArray = new long[to - from];

		System.arraycopy(
			array, from, newArray, 0,
			Math.min(array.length - from, newArray.length));

		return newArray;
	}

	public static short[] clone(short[] array) {
		short[] newArray = new short[array.length];

		System.arraycopy(array, 0, newArray, 0, array.length);

		return newArray;
	}

	public static short[] clone(short[] array, int from, int to) {
		short[] newArray = new short[to - from];

		System.arraycopy(
			array, from, newArray, 0,
			Math.min(array.length - from, newArray.length));

		return newArray;
	}

	public static <T> T[] clone(T[] array) {
		Class<?> arrayClass = array.getClass();

		T[] newArray = (T[])Array.newInstance(
			arrayClass.getComponentType(), array.length);

		System.arraycopy(array, 0, newArray, 0, array.length);

		return newArray;
	}

	public static <T> T[] clone(T[] array, int from, int to) {
		Class<?> arrayClass = array.getClass();

		T[] newArray = (T[])Array.newInstance(
			arrayClass.getComponentType(), to - from);

		System.arraycopy(
			array, from, newArray, 0,
			Math.min(array.length - from, newArray.length));

		return newArray;
	}

	public static <T> T[][] clone(T[][] array) {
		Class<?> arrayClass = array.getClass();

		T[][] newArray = (T[][])Array.newInstance(
			arrayClass.getComponentType(), array.length);

		System.arraycopy(array, 0, newArray, 0, array.length);

		return newArray;
	}

	public static <T> T[][] clone(T[][] array, int from, int to) {
		Class<?> arrayClass = array.getClass();

		T[][] newArray = (T[][])Array.newInstance(
			arrayClass.getComponentType(), to - from);

		System.arraycopy(
			array, from, newArray, 0,
			Math.min(array.length - from, newArray.length));

		return newArray;
	}

	public static void combine(
		Object[] array1, Object[] array2, Object[] combinedArray) {

		System.arraycopy(array1, 0, combinedArray, 0, array1.length);

		System.arraycopy(
			array2, 0, combinedArray, array1.length, array2.length);
	}

	public static boolean contains(boolean[] array, boolean value) {
		if ((array == null) || (array.length == 0)) {
			return false;
		}

		for (int i = 0; i < array.length; i++) {
			if (value == array[i]) {
				return true;
			}
		}

		return false;
	}

	public static boolean contains(byte[] array, byte value) {
		if ((array == null) || (array.length == 0)) {
			return false;
		}

		for (int i = 0; i < array.length; i++) {
			if (value == array[i]) {
				return true;
			}
		}

		return false;
	}

	public static boolean contains(char[] array, char value) {
		if ((array == null) || (array.length == 0)) {
			return false;
		}

		for (int i = 0; i < array.length; i++) {
			if (value == array[i]) {
				return true;
			}
		}

		return false;
	}

	public static boolean contains(double[] array, double value) {
		if ((array == null) || (array.length == 0)) {
			return false;
		}

		for (int i = 0; i < array.length; i++) {
			if (value == array[i]) {
				return true;
			}
		}

		return false;
	}

	public static boolean contains(float[] array, float value) {
		if ((array == null) || (array.length == 0)) {
			return false;
		}

		for (int i = 0; i < array.length; i++) {
			if (value == array[i]) {
				return true;
			}
		}

		return false;
	}

	public static boolean contains(int[] array, int value) {
		if ((array == null) || (array.length == 0)) {
			return false;
		}

		for (int i = 0; i < array.length; i++) {
			if (value == array[i]) {
				return true;
			}
		}

		return false;
	}

	public static boolean contains(long[] array, long value) {
		if ((array == null) || (array.length == 0)) {
			return false;
		}

		for (int i = 0; i < array.length; i++) {
			if (value == array[i]) {
				return true;
			}
		}

		return false;
	}

	public static boolean contains(Object[] array, Object value) {
		if ((array == null) || (array.length == 0) || (value == null)) {
			return false;
		}

		for (int i = 0; i < array.length; i++) {
			if (value.equals(array[i])) {
				return true;
			}
		}

		return false;
	}

	public static boolean contains(short[] array, short value) {
		if ((array == null) || (array.length == 0)) {
			return false;
		}

		for (int i = 0; i < array.length; i++) {
			if (value == array[i]) {
				return true;
			}
		}

		return false;
	}

	public static String[] distinct(String[] array) {
		return distinct(array, null);
	}

	public static String[] distinct(
		String[] array, Comparator<String> comparator) {

		if ((array == null) || (array.length == 0)) {
			return array;
		}

		Set<String> set = null;

		if (comparator == null) {
			set = new TreeSet<String>();
		}
		else {
			set = new TreeSet<String>(comparator);
		}

		for (int i = 0; i < array.length; i++) {
			String s = array[i];

			if (!set.contains(s)) {
				set.add(s);
			}
		}

		return set.toArray(new String[set.size()]);
	}

	public static boolean[] filter(
		boolean[] input, Predicate<Boolean> predicate) {

		if (input == null) {
			return null;
		}

		if (input.length == 0) {
			return input;
		}

		List<Boolean> survivors = new ArrayList<Boolean>();

		for (int i = 0; i < input.length; i++) {
			if (predicate.keep(input[i])) {
				survivors.add(input[i]);
			}
		}

		return toArray(survivors.toArray(new Boolean[survivors.size()]));
	}

	public static byte[] filter(byte[] input, Predicate<Byte> predicate) {

		if (input == null) {
			return null;
		}

		if (input.length == 0) {
			return input;
		}

		List<Byte> survivors = new ArrayList<Byte>();

		for (int i = 0; i < input.length; i++) {
			if (predicate.keep(input[i])) {
				survivors.add(input[i]);
			}
		}

		return toArray(survivors.toArray(new Byte[survivors.size()]));
	}

	public static char[] filter(char[] input, Predicate<Character> predicate) {

		if (input == null) {
			return null;
		}

		if (input.length == 0) {
			return input;
		}

		List<Character> survivors = new ArrayList<Character>();

		for (int i = 0; i < input.length; i++) {
			if (predicate.keep(input[i])) {
				survivors.add(input[i]);
			}
		}

		return toArray(survivors.toArray(new Character[survivors.size()]));
	}

	public static double[] filter(double[] input, Predicate<Double> predicate) {

		if (input == null) {
			return null;
		}

		if (input.length == 0) {
			return input;
		}

		List<Double> survivors = new ArrayList<Double>();

		for (int i = 0; i < input.length; i++) {
			if (predicate.keep(input[i])) {
				survivors.add(input[i]);
			}
		}

		return toArray(survivors.toArray(new Double[survivors.size()]));
	}

	public static float[] filter(float[] input, Predicate<Float> predicate) {

		if (input == null) {
			return null;
		}

		if (input.length == 0) {
			return input;
		}

		List<Float> survivors = new ArrayList<Float>();

		for (int i = 0; i < input.length; i++) {
			if (predicate.keep(input[i])) {
				survivors.add(input[i]);
			}
		}

		return toArray(survivors.toArray(new Float[survivors.size()]));
	}

	public static int[] filter(int[] input, Predicate<Integer> predicate) {

		if (input == null) {
			return null;
		}

		if (input.length == 0) {
			return input;
		}

		List<Integer> survivors = new ArrayList<Integer>();

		for (int i = 0; i < input.length; i++) {
			if (predicate.keep(input[i])) {
				survivors.add(input[i]);
			}
		}

		return toArray(survivors.toArray(new Integer[survivors.size()]));
	}

	public static long[] filter(long[] input, Predicate<Long> predicate) {

		if (input == null) {
			return null;
		}

		if (input.length == 0) {
			return input;
		}

		List<Long> survivors = new ArrayList<Long>();

		for (int i = 0; i < input.length; i++) {
			if (predicate.keep(input[i])) {
				survivors.add(input[i]);
			}
		}

		return toArray(survivors.toArray(new Long[survivors.size()]));
	}

	public static short[] filter(short[] input, Predicate<Short> predicate) {

		if (input == null) {
			return null;
		}

		if (input.length == 0) {
			return input;
		}

		List<Short> survivors = new ArrayList<Short>();

		for (int i = 0; i < input.length; i++) {
			if (predicate.keep(input[i])) {
				survivors.add(input[i]);
			}
		}

		return toArray(survivors.toArray(new Short[survivors.size()]));
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] filter(T[] input, Predicate<T> predicate) {

		if (input == null) {
			return null;
		}

		if (input.length == 0) {
			return input;
		}

		List<T> origin = (List<T>)Arrays.asList(input);
		ArrayList<T> survivors = new ArrayList<T>();

		for (T item : origin) {
			if (predicate.keep(item)) {
				survivors.add(item);
			}
		}

		Object[] result = survivors.toArray();
		return (T[])Arrays.copyOf(result, result.length, input.getClass());
	}

	public static int getLength(Object[] array) {
		if (array == null) {
			return 0;
		}
		else {
			return array.length;
		}
	}

	public static Object getValue(Object[] array, int pos) {
		if ((array == null) || (array.length <= pos)) {
			return null;
		}
		else {
			return array[pos];
		}
	}

	public static boolean[] remove(boolean[] array, boolean value) {
		List<Boolean> list = new ArrayList<Boolean>();

		for (int i = 0; i < array.length; i++) {
			if (value != array[i]) {
				list.add(new Boolean(array[i]));
			}
		}

		return toArray(list.toArray(new Boolean[list.size()]));
	}

	public static byte[] remove(byte[] array, byte value) {
		List<Byte> list = new ArrayList<Byte>();

		for (int i = 0; i < array.length; i++) {
			if (value != array[i]) {
				list.add(new Byte(array[i]));
			}
		}

		return toArray(list.toArray(new Byte[list.size()]));
	}

	public static char[] remove(char[] array, char value) {
		List<Character> list = new ArrayList<Character>();

		for (int i = 0; i < array.length; i++) {
			if (value != array[i]) {
				list.add(new Character(array[i]));
			}
		}

		return toArray(list.toArray(new Character[list.size()]));
	}

	public static double[] remove(double[] array, double value) {
		List<Double> list = new ArrayList<Double>();

		for (int i = 0; i < array.length; i++) {
			if (value != array[i]) {
				list.add(new Double(array[i]));
			}
		}

		return toArray(list.toArray(new Double[list.size()]));
	}

	public static int[] remove(int[] array, int value) {
		List<Integer> list = new ArrayList<Integer>();

		for (int i = 0; i < array.length; i++) {
			if (value != array[i]) {
				list.add(new Integer(array[i]));
			}
		}

		return toArray(list.toArray(new Integer[list.size()]));
	}

	public static long[] remove(long[] array, long value) {
		List<Long> list = new ArrayList<Long>();

		for (int i = 0; i < array.length; i++) {
			if (value != array[i]) {
				list.add(new Long(array[i]));
			}
		}

		return toArray(list.toArray(new Long[list.size()]));
	}

	public static short[] remove(short[] array, short value) {
		List<Short> list = new ArrayList<Short>();

		for (int i = 0; i < array.length; i++) {
			if (value != array[i]) {
				list.add(new Short(array[i]));
			}
		}

		return toArray(list.toArray(new Short[list.size()]));
	}

	public static String[] remove(String[] array, String value) {
		List<String> list = new ArrayList<String>();

		for (String s : array) {
			if (!s.equals(value)) {
				list.add(s);
			}
		}

		return list.toArray(new String[list.size()]);
	}

	public static String[] removeByPrefix(String[] array, String prefix) {
		List<String> list = new ArrayList<String>();

		for (String s : array) {
			if (!s.startsWith(prefix)) {
				list.add(s);
			}
		}

		return list.toArray(new String[list.size()]);
	}

	public static void reverse(String[] array) {
		for (int left = 0, right = array.length - 1; left < right;
				left++, right--) {

			String value = array[left];

			array[left] = array[right];
			array[right] = value;
		}
	}

	public static boolean[] subset(boolean[] array, int start, int end) {
		if ((start < 0) || (end < 0) || ((end - start) < 0)) {
			return array;
		}

		boolean[] newArray = new boolean[end - start];

		System.arraycopy(array, start, newArray, 0, end - start);

		return newArray;
	}

	public static byte[] subset(byte[] array, int start, int end) {
		if ((start < 0) || (end < 0) || ((end - start) < 0)) {
			return array;
		}

		byte[] newArray = new byte[end - start];

		System.arraycopy(array, start, newArray, 0, end - start);

		return newArray;
	}

	public static char[] subset(char[] array, int start, int end) {
		if ((start < 0) || (end < 0) || ((end - start) < 0)) {
			return array;
		}

		char[] newArray = new char[end - start];

		System.arraycopy(array, start, newArray, 0, end - start);

		return newArray;
	}

	public static double[] subset(double[] array, int start, int end) {
		if ((start < 0) || (end < 0) || ((end - start) < 0)) {
			return array;
		}

		double[] newArray = new double[end - start];

		System.arraycopy(array, start, newArray, 0, end - start);

		return newArray;
	}

	public static float[] subset(float[] array, int start, int end) {
		if ((start < 0) || (end < 0) || ((end - start) < 0)) {
			return array;
		}

		float[] newArray = new float[end - start];

		System.arraycopy(array, start, newArray, 0, end - start);

		return newArray;
	}

	public static int[] subset(int[] array, int start, int end) {
		if ((start < 0) || (end < 0) || ((end - start) < 0)) {
			return array;
		}

		int[] newArray = new int[end - start];

		System.arraycopy(array, start, newArray, 0, end - start);

		return newArray;
	}

	public static long[] subset(long[] array, int start, int end) {
		if ((start < 0) || (end < 0) || ((end - start) < 0)) {
			return array;
		}

		long[] newArray = new long[end - start];

		System.arraycopy(array, start, newArray, 0, end - start);

		return newArray;
	}

	public static short[] subset(short[] array, int start, int end) {
		if ((start < 0) || (end < 0) || ((end - start) < 0)) {
			return array;
		}

		short[] newArray = new short[end - start];

		System.arraycopy(array, start, newArray, 0, end - start);

		return newArray;
	}

	public static <T> T[] subset(T[] array, int start, int end) {
		if ((start < 0) || (end < 0) || ((end - start) < 0)) {
			return array;
		}

		Class<?> arrayClass = array.getClass();

		T[] newArray = (T[])Array.newInstance(
			arrayClass.getComponentType(), end - start);

		System.arraycopy(array, start, newArray, 0, end - start);

		return newArray;
	}

	public static Boolean[] toArray(boolean[] array) {
		Boolean[] newArray = new Boolean[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = Boolean.valueOf(array[i]);
		}

		return newArray;
	}

	public static boolean[] toArray(Boolean[] array) {
		boolean[] newArray = new boolean[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i].booleanValue();
		}

		return newArray;
	}

	public static Byte[] toArray(byte[] array) {
		Byte[] newArray = new Byte[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = Byte.valueOf(array[i]);
		}

		return newArray;
	}

	public static byte[] toArray(Byte[] array) {
		byte[] newArray = new byte[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i].byteValue();
		}

		return newArray;
	}

	public static Character[] toArray(char[] array) {
		Character[] newArray = new Character[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = Character.valueOf(array[i]);
		}

		return newArray;
	}

	public static char[] toArray(Character[] array) {
		char[] newArray = new char[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i].charValue();
		}

		return newArray;
	}

	public static Double[] toArray(double[] array) {
		Double[] newArray = new Double[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = new Double(array[i]);
		}

		return newArray;
	}

	public static double[] toArray(Double[] array) {
		double[] newArray = new double[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i].doubleValue();
		}

		return newArray;
	}

	public static Float[] toArray(float[] array) {
		Float[] newArray = new Float[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = new Float(array[i]);
		}

		return newArray;
	}

	public static float[] toArray(Float[] array) {
		float[] newArray = new float[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i].floatValue();
		}

		return newArray;
	}

	public static Integer[] toArray(int[] array) {
		Integer[] newArray = new Integer[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = new Integer(array[i]);
		}

		return newArray;
	}

	public static int[] toArray(Integer[] array) {
		int[] newArray = new int[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i].intValue();
		}

		return newArray;
	}

	public static Long[] toArray(long[] array) {
		Long[] newArray = new Long[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = new Long(array[i]);
		}

		return newArray;
	}

	public static long[] toArray(Long[] array) {
		long[] newArray = new long[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i].longValue();
		}

		return newArray;
	}

	public static Short[] toArray(short[] array) {
		Short[] newArray = new Short[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = new Short(array[i]);
		}

		return newArray;
	}

	public static short[] toArray(Short[] array) {
		short[] newArray = new short[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i].shortValue();
		}

		return newArray;
	}

	public static double[] toDoubleArray(Collection<Double> collection) {
		double[] newArray = new double[collection.size()];

		if (collection instanceof List) {
			List<Double> list = (List<Double>)collection;

			for (int i = 0; i < list.size(); i++) {
				Double value = list.get(i);

				newArray[i] = value.doubleValue();
			}
		}
		else {
			int i = 0;

			Iterator<Double> iterator = collection.iterator();

			while (iterator.hasNext()) {
				Double value = iterator.next();

				newArray[i++] = value.doubleValue();
			}
		}

		return newArray;
	}

	public static float[] toFloatArray(Collection<Float> collection) {
		float[] newArray = new float[collection.size()];

		if (collection instanceof List) {
			List<Float> list = (List<Float>)collection;

			for (int i = 0; i < list.size(); i++) {
				Float value = list.get(i);

				newArray[i] = value.floatValue();
			}
		}
		else {
			int i = 0;

			Iterator<Float> iterator = collection.iterator();

			while (iterator.hasNext()) {
				Float value = iterator.next();

				newArray[i++] = value.floatValue();
			}
		}

		return newArray;
	}

	public static int[] toIntArray(Collection<Integer> collection) {
		int[] newArray = new int[collection.size()];

		if (collection instanceof List) {
			List<Integer> list = (List<Integer>)collection;

			for (int i = 0; i < list.size(); i++) {
				Integer value = list.get(i);

				newArray[i] = value.intValue();
			}
		}
		else {
			int i = 0;

			Iterator<Integer> iterator = collection.iterator();

			while (iterator.hasNext()) {
				Integer value = iterator.next();

				newArray[i++] = value.intValue();
			}
		}

		return newArray;
	}

	public static long[] toLongArray(Collection<Long> collection) {
		long[] newArray = new long[collection.size()];

		if (collection instanceof List) {
			List<Long> list = (List<Long>)collection;

			for (int i = 0; i < list.size(); i++) {
				Long value = list.get(i);

				newArray[i] = value.longValue();
			}
		}
		else {
			int i = 0;

			Iterator<Long> iterator = collection.iterator();

			while (iterator.hasNext()) {
				Long value = iterator.next();

				newArray[i++] = value.longValue();
			}
		}

		return newArray;
	}

	public static Long[] toLongArray(Object[] array) {
		Long[] newArray = new Long[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = (Long)array[i];
		}

		return newArray;
	}

	public static short[] toShortArray(Collection<Short> collection) {
		short[] newArray = new short[collection.size()];

		if (collection instanceof List) {
			List<Short> list = (List<Short>)collection;

			for (int i = 0; i < list.size(); i++) {
				Short value = list.get(i);

				newArray[i] = value.shortValue();
			}
		}
		else {
			int i = 0;

			Iterator<Short> iterator = collection.iterator();

			while (iterator.hasNext()) {
				Short value = iterator.next();

				newArray[i++] = value.shortValue();
			}
		}

		return newArray;
	}

	/**
	 * @see ListUtil#toString(List, String)
	 */
	public static String toString(Object[] array, String param) {
		return toString(array, param, StringPool.COMMA);
	}

	/**
	 * @see ListUtil#toString(List, String, String)
	 */
	public static String toString(
		Object[] array, String param, String delimiter) {

		return toString(array, param, delimiter, null);
	}

	public static String toString(
		Object[] array, String param, String delimiter, Locale locale) {

		if ((array == null) || (array.length == 0)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(2 * array.length - 1);

		for (int i = 0; i < array.length; i++) {
			Object bean = array[i];

			Object value = BeanPropertiesUtil.getObject(bean, param);

			if (value != null) {
				if (locale != null) {
					sb.append(LanguageUtil.get(locale, value.toString()));
				}
				else {
					sb.append(value);
				}
			}

			if ((i + 1) != array.length) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	/**
	 * @see ListUtil#toString(List, Accessor)
	 */
	public static <T, V> String toString(T[] list, Accessor<T, V> accessor) {
		return toString(list, accessor, StringPool.COMMA);
	}

	/**
	 * @see ListUtil#toString(List, Accessor, String)
	 */
	public static <T, V> String toString(
		T[] list, Accessor<T, V> accessor, String delimiter) {

		return toString(list, accessor, delimiter, null);
	}

	public static <T, V> String toString(
		T[] list, Accessor<T, V> accessor, String delimiter, Locale locale) {

		if ((list == null) || (list.length == 0)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(2 * list.length - 1);

		for (int i = 0; i < list.length; i++) {
			T bean = list[i];

			V value = accessor.get(bean);

			if (value != null) {
				if (locale != null) {
					sb.append(LanguageUtil.get(locale, value.toString()));
				}
				else {
					sb.append(value);
				}
			}

			if ((i + 1) != list.length) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	public static String[] toStringArray(boolean[] array) {
		String[] newArray = new String[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}

		return newArray;
	}

	public static String[] toStringArray(byte[] array) {
		String[] newArray = new String[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}

		return newArray;
	}

	public static String[] toStringArray(char[] array) {
		String[] newArray = new String[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}

		return newArray;
	}

	public static String[] toStringArray(Date[] array, DateFormat dateFormat) {
		String[] newArray = new String[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = dateFormat.format(array[i]);
		}

		return newArray;
	}

	public static String[] toStringArray(double[] array) {
		String[] newArray = new String[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}

		return newArray;
	}

	public static String[] toStringArray(float[] array) {
		String[] newArray = new String[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}

		return newArray;
	}

	public static String[] toStringArray(int[] array) {
		String[] newArray = new String[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}

		return newArray;
	}

	public static String[] toStringArray(JSONArray array) {
		String[] newArray = new String[array.length()];

		for (int i = 0; i < array.length(); i++) {
			newArray[i] = array.getString(i);
		}

		return newArray;
	}

	public static String[] toStringArray(long[] array) {
		String[] newArray = new String[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}

		return newArray;
	}

	public static String[] toStringArray(Object[] array) {
		String[] newArray = new String[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}

		return newArray;
	}

	public static String[] toStringArray(short[] array) {
		String[] newArray = new String[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}

		return newArray;
	}

	public static byte[] unique(byte[] array) {
		List<Byte> list = new UniqueList<Byte>();

		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}

		return toArray(list.toArray(new Byte[list.size()]));
	}

	public static double[] unique(double[] array) {
		List<Double> list = new UniqueList<Double>();

		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}

		return toArray(list.toArray(new Double[list.size()]));
	}

	public static float[] unique(float[] array) {
		List<Float> list = new UniqueList<Float>();

		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}

		return toArray(list.toArray(new Float[list.size()]));
	}

	public static int[] unique(int[] array) {
		List<Integer> list = new UniqueList<Integer>();

		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}

		return toArray(list.toArray(new Integer[list.size()]));
	}

	public static long[] unique(long[] array) {
		List<Long> list = new UniqueList<Long>();

		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}

		return toArray(list.toArray(new Long[list.size()]));
	}

	public static short[] unique(short[] array) {
		List<Short> list = new UniqueList<Short>();

		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}

		return toArray(list.toArray(new Short[list.size()]));
	}

}