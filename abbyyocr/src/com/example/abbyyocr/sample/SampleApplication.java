// Copyright (c) ABBYY (BIT Software), 1993 - 2011. All rights reserved.
// Author: Starosvetskiy Artyom

package com.example.abbyyocr.sample;

import android.app.Application;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.abbyy.mobile.ocr4.AssetDataSource;
import com.abbyy.mobile.ocr4.DataSource;
import com.abbyy.mobile.ocr4.Engine;
import com.abbyy.mobile.ocr4.FileLicense;
import com.abbyy.mobile.ocr4.License;
import com.abbyy.mobile.ocr4.RecognitionLanguage;
import com.example.abbyyocr.utils.PreferenceUtils;

/**
 * Sample application class.
 */
public class SampleApplication extends Application {
	/** Logging tag. */
	private static final String TAG = "SampleApplication";

	private static final String _licenseFile = "SMTT-4200-0003-7427-4432-3108.ABBYY.License";
	private static final String _applicationID = "Android_ID";

	private static final String _patternsFileExtension = ".mp3";
	private static final String _dictionariesFileExtension = ".mp3";
	private static final String _keywordsFileExtension = ".mp3";

	@Override
	public void onCreate() {
		Log.v( SampleApplication.TAG, "onCreate()" );
		super.onCreate();

		// Write default settings to the settings store. These values will be written only during the first
		// startup or
		// if the values are rubbed.
		PreferenceManager.setDefaultValues( this, R.xml.preferences, true );

		final DataSource assetDataSrouce = new AssetDataSource( this.getAssets() );

		final List<DataSource> dataSources = new ArrayList<DataSource>();
		dataSources.add( assetDataSrouce );

		Engine.loadNativeLibrary();
		try {
			Engine.createInstance( dataSources, new FileLicense( assetDataSrouce,
					SampleApplication._licenseFile, SampleApplication._applicationID ),
					new Engine.DataFilesExtensions( SampleApplication._patternsFileExtension,
							SampleApplication._dictionariesFileExtension,
							SampleApplication._keywordsFileExtension ) );

			RecognitionContext.createInstance( this );

			filterRecognitionLanguagesPreferences( RecognitionContext.getLanguagesAvailableForOcr(),
					getString( R.string.key_recognition_languages_ocr ) );
		} catch( final IOException e ) {
		} catch( final License.BadLicenseException e ) {
		}
	}

	@Override
	public void onTerminate() {
		Log.v( SampleApplication.TAG, "onTerminate()" );
		try {
			Engine.destroyInstance();
			RecognitionContext.destroyInstance();
		} catch( final IllegalStateException e ) {
			Log.e( SampleApplication.TAG, "onTerminate failed", e );
		}
		super.onTerminate();
	}

	public void filterRecognitionLanguagesPreferences( final Set<RecognitionLanguage> availableLanguages,
			final String preferenceKey ) {
		final Set<RecognitionLanguage> languages =
				PreferenceUtils.getRecognitionLanguages( this, preferenceKey );
		languages.retainAll( availableLanguages );
		PreferenceUtils.setRecognitionLanguages( this, preferenceKey, languages );
	}

}
