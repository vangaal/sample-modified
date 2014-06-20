// Copyright (c) ABBYY (BIT Software), 1993 - 2012. All rights reserved.
// edited by pradipta d.p 06/2014
package com.example.abbyyocr.sample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.abbyyocr.sample.RecognitionContext.RecognitionTarget;

/**
 * An activity which just displays recognition result in a text view.
 */
public final class ProcessResultsActivity extends ActivityBase {
	/** Logging tag. */
	static final String TAG = "ProcessResultsActivity";

	private static final String KEY_RESULT = "com.abbyy.mobile.ocr4.RESULT";

	private String _resultText;
	private TextView _resultView;

	/**
	 * Start the {@link ProcessResultsActivity}.
	 * 
	 * @param context
	 *            A context of the application package.
	 * @param result
	 *            A recognition result.
	 */
	public static void start( final Context context, final Object result ) {
		RecognitionContext.setRecognitionResult( result );
		context.startActivity( new Intent( context, ProcessResultsActivity.class ) );
	}

	@Override
	protected void onCreate( final Bundle savedInstanceState ) {
		Log.v( ProcessResultsActivity.TAG, "onCreate()" );
		super.onCreate( savedInstanceState );

		// Initialize the activity.
		if( !initialize() ) {
			Log.w( ProcessResultsActivity.TAG, "Failed to initialize" );
			finish();
		}

		// Load widgets and fill them with data.
		setContentView( R.layout.process_results_view );
		
		_resultView = (TextView) findViewById( R.id.label_result );

		loadData( savedInstanceState );
	}

	@Override
	protected void onSaveInstanceState( final Bundle outState ) {
		Log.v( ProcessResultsActivity.TAG, "onSaveInstanceState()" );
		super.onSaveInstanceState( outState );

		outState.putString( ProcessResultsActivity.KEY_RESULT, _resultText );
	}


	/**
	 * Initialize an activity.
	 * 
	 * @param savedInstanceState
	 *            If the activity is being re-initialized after
	 *            previously being shut down then this Bundle contains the data it
	 *            most recently supplied in onSaveInstanceState.
	 *            <b>Note: Otherwise it is null.</b>
	 * @return {@code true} if activity was initialized successfully, {@code false} otherwise.
	 */
	private boolean initialize() {
		return RecognitionContext.getRecognitionResult() != null;
	}

	/**
	 * Fill widgets with data.
	 * 
	 * @param savedInstanceState
	 *            If the activity is being re-initialized after
	 *            previously being shut down then this Bundle contains the data it
	 *            most recently supplied in onSaveInstanceState.
	 *            <b>Note: Otherwise it is null.</b>
	 */
	private void loadData( final Bundle savedInstanceState ) {
		if( savedInstanceState != null ) {
			_resultText = savedInstanceState.getString( ProcessResultsActivity.KEY_RESULT );
		}

		if( _resultText == null ) {
			final StringBuilder resultTextBuilder = new StringBuilder();
			if( RecognitionContext.shouldDetectPageOrientation()
					&& ( RecognitionContext.getRecognitionTarget() == RecognitionTarget.TEXT)) {
				resultTextBuilder
						.append( "Detected rotation:\n" )
						.append( RecognitionContext.getRotationType().name() )
						.append( "\n\n" );
			}
			
			
			
			resultTextBuilder
					.append( "Result:\n" )
					.append( RecognitionContext.getRecognitionResult().toString() );
			_resultText = resultTextBuilder.toString();
		}

		_resultView.setText( _resultText );
	}

	@Override
	protected void onPause() {
		Log.d( ProcessResultsActivity.TAG, "onPause()" );
		super.onPause();
	}

	@Override
	protected void onRestart() {
		Log.d( ProcessResultsActivity.TAG, "onRestart()" );
		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.d( ProcessResultsActivity.TAG, "onResume()" );
		super.onResume();
	}

	@Override
	protected void onStop() {
		Log.d( ProcessResultsActivity.TAG, "onStop()" );
		super.onStop();
	}

}
