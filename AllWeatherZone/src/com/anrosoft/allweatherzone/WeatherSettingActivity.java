/*
 * Copyright (C) 2011 The Android Open Source Project
 * Copyright (C) 2014 Zhenghong Wang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.anrosoft.allweatherzone;

import java.io.File;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anrosoft.allweatherzone.R.drawable;

public class WeatherSettingActivity extends ActionBarActivity {

	private static final String YOUTUBE_CHANNEL = "UCqZzw1k0zMyGMBIHTjslMyw";
	private static final String YOUTUBE_ID = "2AERMXfPJJc";
	private static final String WEBSITE_APPS = "https://apps.anrosoft.com";
	private static final String PLAYSTORE_DEVELOPER = "https://play.google.com/store/apps/developer?id=AnroSoft";
	private static final String DEVELOPER_MAIL = "anrosoft.office@gmail.com";
	private static final String APP_NAME = "Game Wallpaper";
	private static final String directoryName = "all_weather_zone";
	private static final String fileNameSetting = "awz_setting_info_96528";

	private static final String tempFormatOption[] = { "Celsius", "Fahrenheit" };
	private int currentFormatIndex = 0; // current channel indicates
	private TextView tempFormatStatus;
	private LinearLayout developerContactLayout;
	private LinearLayout shareOptionLayout;
	private LinearLayout rateOptionLayout;

	private final static File weatherInfoDirectory = new File(Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/"
			+ directoryName + "/");
	private final static File weatherSettingInfoFile = new File(
			weatherInfoDirectory, fileNameSetting + ".txt");

	private boolean[] forecastOpenHideStatus = { false, false, false, false,
			false };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_layout);

		getActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.drawable.actionbar_bg_style));

		final CheckBox[] forecastCheckBox = {
				(CheckBox) findViewById(R.id.forecastCheckBox01),
				(CheckBox) findViewById(R.id.forecastCheckBox02),
				(CheckBox) findViewById(R.id.forecastCheckBox03),
				(CheckBox) findViewById(R.id.forecastCheckBox04),
				(CheckBox) findViewById(R.id.forecastCheckBox05) };

		LinearLayout[] forecastLayout = {
				(LinearLayout) findViewById(R.id.forecastLayout01),
				(LinearLayout) findViewById(R.id.forecastLayout02),
				(LinearLayout) findViewById(R.id.forecastLayout03),
				(LinearLayout) findViewById(R.id.forecastLayout04),
				(LinearLayout) findViewById(R.id.forecastLayout05) };

		final TextView[] forecastStatus = {
				(TextView) findViewById(R.id.forecastStatus01),
				(TextView) findViewById(R.id.forecastStatus02),
				(TextView) findViewById(R.id.forecastStatus03),
				(TextView) findViewById(R.id.forecastStatus04),
				(TextView) findViewById(R.id.forecastStatus05) };

		for (int i = 0; i < 5; i++) {
			final int index = i;
			forecastCheckBox[i].setClickable(false);
			forecastLayout[i].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (forecastCheckBox[index].isChecked()) {
						forecastCheckBox[index].setChecked(false);
						forecastStatus[index].setText("Status (Closed)");
						forecastOpenHideStatus[index] = false;
					} else {
						forecastCheckBox[index].setChecked(true);
						forecastStatus[index].setText("Status (Open)");
						forecastOpenHideStatus[index] = true;
					}
					saveAppState();
				}
			});
		}

		tempFormatStatus = (TextView) findViewById(R.id.tempFormatStatus);
		LinearLayout tempFormatLayout = (LinearLayout) findViewById(R.id.tempFormatLayout);
		tempFormatLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				displayTempFormatDialog();
			}
		});

		developerContactLayout = (LinearLayout) findViewById(R.id.developerContact);
		developerContactLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				contactOption();
			}
		});
		shareOptionLayout = (LinearLayout) findViewById(R.id.shareOption);
		shareOptionLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showShare();
			}
		});
		rateOptionLayout = (LinearLayout) findViewById(R.id.rateOption);
		rateOptionLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showRated();
			}
		});

		// initiated setting status
		String appStateString = getAppSettingState().trim();
		String[] appState = appStateString.split("d");
		if (appState.length == 6) {
			currentFormatIndex = Integer.parseInt(appState[0].trim());
			tempFormatStatus.setText(tempFormatOption[currentFormatIndex]);
			for (int i = 1; i < 6; i++) {
				int index = Integer.parseInt(appState[i].trim());
				if (index == 1) {
					forecastCheckBox[i - 1].setChecked(true);
					forecastStatus[i - 1].setText("Status (Open)");
					forecastOpenHideStatus[i - 1] = true;
				} else {
					forecastCheckBox[i - 1].setChecked(false);
					forecastStatus[i - 1].setText("Status (Closed)");
					forecastOpenHideStatus[i - 1] = false;
				}
			}
		}
	}

	public String getAppSettingState() {
		try {
			if (!weatherInfoDirectory.exists()) {
				weatherInfoDirectory.mkdir();
				weatherSettingInfoFile.createNewFile();
			}
			if (!weatherSettingInfoFile.exists()) {
				weatherSettingInfoFile.createNewFile();
			}
		} catch (Exception e) {
			Toast.makeText(getBaseContext(), "init : " + e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
		return FileUtils.readFileData(getApplicationContext(),
				weatherSettingInfoFile);
	}

	public void saveAppState() {
		String appState = currentFormatIndex + "d\n";
		for (int i = 0; i < 5; i++) {
			if (forecastOpenHideStatus[i]) {
				appState += "1d\n";
			} else {
				appState += "0d\n";
			}
		}

		try {
			if (!weatherInfoDirectory.exists()) {
				weatherInfoDirectory.mkdir();
				weatherSettingInfoFile.createNewFile();
			}
			if (!weatherSettingInfoFile.exists()) {
				weatherSettingInfoFile.createNewFile();
			}

		} catch (Exception e) {
			Toast.makeText(getBaseContext(), "init : " + e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}

		FileUtils.writeFileData(getApplicationContext(),
				weatherSettingInfoFile, appState);

		// int[] forecastLayoutIDs = { R.id.forecastLayout01,
		// R.id.forecastLayout02, R.id.forecastLayout03,
		// R.id.forecastLayout04, R.id.forecastLayout05 };
		//
		// Toast.makeText(getApplicationContext(), appState, Toast.LENGTH_LONG)
		// .show();
	}

	private void homeInterface() {
		Intent in = new Intent(WeatherSettingActivity.this,
				WeatherViewActivity.class);
		in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		finish();
		startActivity(in);
	}

	public void proVersionOption() {
		Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri
				.parse(PLAYSTORE_DEVELOPER));
		startActivity(intent);
	}

	public void contactOption() {
		try {
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"
					+ DEVELOPER_MAIL)));
		} catch (ActivityNotFoundException e) {

		}
	}

	public void showRated() {

		try {
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id="
							+ getPackageName())));
		} catch (ActivityNotFoundException e) {

		}

	}

	public void showShare() {

		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, APP_NAME
				+ " https://play.google.com/store/apps/details?id="
				+ getPackageName());
		sendIntent.setType("text/plain");
		startActivity(sendIntent);

	}

	// temperature format chooser dialog
	private void displayTempFormatDialog() {
		AlertDialog.Builder audioChannel = new AlertDialog.Builder(this);
		audioChannel.setIcon(drawable.ic_launcher); // dialog icon
		audioChannel.setTitle("Select Format"); // dialog title
		audioChannel.setSingleChoiceItems(tempFormatOption, currentFormatIndex,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						currentFormatIndex = which;
						tempFormatStatus
								.setText(tempFormatOption[currentFormatIndex]);
						saveAppState();
						dialog.dismiss(); // destroy dialog
					}
				}).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);

		MenuItem home = menu.findItem(R.id.action_settings);
		home.setTitle("Home");
		MenuItem homeI = menu.findItem(R.id.setting);
		homeI.setTitle("Home");
		homeI.setIcon(drawable.homeicon);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		// menu actions
		if (id == R.id.share) {
			showShare();
			return true;
		} else if (id == R.id.setting) {
			homeInterface();
			return true;
		} else if (id == R.id.action_settings) {
			homeInterface();
			return true;
		} else if (id == R.id.action_rate) {
			showRated();
			return true;
		} else if (id == R.id.action_share) {
			showShare();
			return true;
		} else if (id == R.id.action_contact) {
			contactOption();
			return true;
		} else if (id == R.id.action_pro_version) {
			proVersionOption();
			return true;
		} else if (id == R.id.action_exit) {
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
