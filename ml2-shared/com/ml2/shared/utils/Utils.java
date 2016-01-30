package com.ml2.shared.utils;

import com.badlogic.gdx.Gdx;

public class Utils {
	/**
	 * @param a : will come first in the new array
	 * @param b : will come second in the new array
	 * @return an array of bytes containing the contents of <b>a</b> followed by <b>b</b>.
	 */
	public static byte[] concat(byte[] a, byte[] b) {
		byte[] result = new byte[a.length+b.length];
		try {
			System.arraycopy(a, 0, result, 0, a.length);
			System.arraycopy(b, 0, result, a.length, b.length);
		}
		catch(IndexOutOfBoundsException e) { Gdx.app.error("ML2 Utils", e.getStackTrace().toString()); }
		catch(ArrayStoreException e) { Gdx.app.error("ML2 Utils", e.getStackTrace().toString()); }
		catch(NullPointerException e) { Gdx.app.error("ML2 Utils", e.getStackTrace().toString()); }
		return result;
	}
}
