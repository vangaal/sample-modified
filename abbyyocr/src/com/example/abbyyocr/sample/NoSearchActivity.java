package com.example.abbyyocr.sample;

import com.example.abbyyocr.sample.ActivityBase;

/**
 * An activity which disables the search key function.
 * <p>
 * To use it, just inherit from {@code NoSearchActivity} instead of {@code Activity}.
 */
public class NoSearchActivity extends ActivityBase {
	@Override
	public boolean onSearchRequested() {
		return false;
	}
}
