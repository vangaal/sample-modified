// Copyright (c) ABBYY (BIT Software), 1993 - 2011. All rights reserved.
// Author: Starosvetskiy Artyom
// modified by : pradipta d.p 06/2014
package com.example.abbyyocr.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;

import com.abbyy.mobile.ocr4.RecognitionLanguage;

public final class PreferencesActivity extends PreferenceActivity {
	/** Logging tag. */
	private static final String TAG = "PreferencesActivity";
	
	private static final int MIN_LANGUAGES_COUNT = 1;
	private static final String ERROR_MSG_MIN_LANGUAGES_COUNT_REACHED = "minimal 1 pilihan bahasa harus dipilih";
	private static final String ERROR_MSG_MIN_LANGUAGES_COUNT_EXCEEDED = null; // The impelemntation should deny preferences initialization without languages.

	private static final int MAX_LANGUAGES_COUNT = 3;
	private static final String ERROR_MSG_MAX_LANGUAGES_COUNT_REACHED = "anda melewati batas maksimal pilihan basa";
	private static final String ERROR_MSG_MAX_LANGUAGES_COUNT_EXCEEDED = "jumlah pilhan bahasa sudah maksimal "
				+ "disarankan uncheck pilihan yang tidak digunakan";
	
	ListPreferenceMultiSelect.Filter ocrLanguagesFilter = new ListPreferenceMultiSelect.Filter() {
		@Override
		public boolean isEntryVisible( final CharSequence entry ) {
			return RecognitionContext.getLanguagesAvailableForOcr().contains(
					RecognitionLanguage.valueOf( String.valueOf( entry ) ) );
		}
	};

	public static void start( final Context context ) {
		final Intent intent = new Intent( context, PreferencesActivity.class );
		context.startActivity( intent );
	}

	@Override
	public void onCreate( final Bundle savedInstanceState ) {
		Log.v( PreferencesActivity.TAG, "onCreate()" );
		super.onCreate( savedInstanceState );

		addPreferencesFromResource( R.xml.preferences );
		
		final ListPreferenceMultiSelect ocrLanguagesPreference =
				(ListPreferenceMultiSelect) this.getPreferenceScreen().findPreference(
						getString( R.string.key_recognition_languages_ocr ) );
		ocrLanguagesPreference.setFilter( ocrLanguagesFilter );
		ocrLanguagesPreference.setEntriesCountConstraint(MIN_LANGUAGES_COUNT, PreferencesActivity.ERROR_MSG_MIN_LANGUAGES_COUNT_REACHED,
				PreferencesActivity.ERROR_MSG_MIN_LANGUAGES_COUNT_EXCEEDED, MAX_LANGUAGES_COUNT, PreferencesActivity.ERROR_MSG_MAX_LANGUAGES_COUNT_REACHED,
				PreferencesActivity.ERROR_MSG_MAX_LANGUAGES_COUNT_EXCEEDED);
	
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
