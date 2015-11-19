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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anrosoft.weatherzone.WeatherInfo;
import com.anrosoft.weatherzone.WeatherInfo.ForecastInfo;
import com.anrosoft.weatherzone.WeatherZone;
import com.anrosoft.weatherzone.WeatherZone.SEARCH_MODE;
import com.anrosoft.weatherzone.WeatherZone.UNIT;
import com.anrosoft.weatherzone.WeatherZoneExceptionListener;
import com.anrosoft.weatherzone.WeatherZoneInfoListener;

public class WeatherViewActivity extends ActionBarActivity implements
		WeatherZoneInfoListener, WeatherZoneExceptionListener {

	private ImageView mIvWeather0;
	private TextView mTvErrorMessage;
	private TextView mTvTitle;
	private TextView mTvCurretnTempareture;
	private TextView mTvCurretnDate;
	private TextView mTvWeatherHumidity;
	private TextView mTvWeatherWind;
	private TextView mTvWeatherWindDirection;
	private TextView mTvWeatherWindSpeed;
	private TextView mTvWeatherPressure;
	private TextView mTvWeatherHeaderVisibility;
	private TextView mTvWeatherVisibility;
	private EditText mEtAreaOfCity;
	private TextView[] foreCastWeatherTitle;
	private TextView[] foreCastWeatherDate;
	private TextView[] foreCastWeather;
	private TextView[] foreCastWeatherLowTempareture;
	private TextView[] foreCastWeatherHighTempareture;
	private ImageView[] foreCastWeatherImage;
	private Button mBtSearch;
	private Button mBtGPS;
	private Button mSearchVisiable;
	private LinearLayout mWeatherInfosLayout;
	private int celsiusOrFahrenheit = 0;
	private boolean[] forecastViewEnable = { true, true, true, true, true };

	private static HashMap<String, String> allWeatherInfoHashMap = new HashMap<String, String>();

	private static final String YOUTUBE_CHANNEL = "UCqZzw1k0zMyGMBIHTjslMyw";
	private static final String YOUTUBE_ID = "2AERMXfPJJc";
	private static final String WEBSITE_APPS = "https://apps.anrosoft.com";
	private static final String PLAYSTORE_DEVELOPER = "https://play.google.com/store/apps/developer?id=AnroSoft";
	private static final String DEVELOPER_MAIL = "anrosoft.office@gmail.com";
	private static final String APP_NAME = "Game Wallpaper";
	private final static String myDateFormatString = "EEE, d MMM yyyy hh:mm a";
	private final static String directoryName = "all_weather_zone";
	private final static String fileName = "awz_info_025611";
	private static final String fileNameSetting = "awz_setting_info_96528";
	private SimpleDateFormat myDateFormat = new SimpleDateFormat(
			myDateFormatString);

	private final static File weatherInfoDirectory = new File(Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/"
			+ directoryName + "/");
	private final static File weatherInfoFile = new File(weatherInfoDirectory,
			fileName + ".txt");
	private final static File weatherSettingInfoFile = new File(
			weatherInfoDirectory, fileNameSetting + ".txt");

	private WeatherZone mWeatherZone = WeatherZone
			.getInstance(5000, 5000, true);

	private ProgressDialog mProgressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather_layout);

		setTitle(R.string.app_view_title);
		getActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.drawable.actionbar_bg_style));

		LinearLayout searchOption = (LinearLayout) findViewById(R.id.search_layout_test);
		searchOption.setVisibility(View.GONE);

		mWeatherZone.setExceptionListener(this);

		mTvTitle = (TextView) findViewById(R.id.textview_title);
		mTvCurretnTempareture = (TextView) findViewById(R.id.current_tempareture);
		mTvCurretnDate = (TextView) findViewById(R.id.current_date);
		mTvWeatherHumidity = (TextView) findViewById(R.id.current_humidity);
		mTvWeatherWind = (TextView) findViewById(R.id.current_wind);
		mTvWeatherWindDirection = (TextView) findViewById(R.id.current_wind_direction);
		mTvWeatherWindSpeed = (TextView) findViewById(R.id.current_wind_speed);
		mTvWeatherPressure = (TextView) findViewById(R.id.current_pressure);
		mTvWeatherVisibility = (TextView) findViewById(R.id.current_visibility);
		mTvWeatherHeaderVisibility = (TextView) findViewById(R.id.current_visibility_header);

		foreCastWeatherTitle = new TextView[WeatherZone.FORECAST_INFO_MAX_SIZE];
		foreCastWeather = new TextView[WeatherZone.FORECAST_INFO_MAX_SIZE];
		foreCastWeatherDate = new TextView[WeatherZone.FORECAST_INFO_MAX_SIZE];
		foreCastWeatherLowTempareture = new TextView[WeatherZone.FORECAST_INFO_MAX_SIZE];
		foreCastWeatherHighTempareture = new TextView[WeatherZone.FORECAST_INFO_MAX_SIZE];
		foreCastWeatherImage = new ImageView[WeatherZone.FORECAST_INFO_MAX_SIZE];

		int[] weaatherTitleIDs = { R.id.weather_title_01,
				R.id.weather_title_02, R.id.weather_title_03,
				R.id.weather_title_04, R.id.weather_title_05 };
		int[] weaatherDateIDs = { R.id.weather_date_01, R.id.weather_date_02,
				R.id.weather_date_03, R.id.weather_date_04,
				R.id.weather_date_05 };
		int[] weaatherIDs = { R.id.weather_01, R.id.weather_02,
				R.id.weather_03, R.id.weather_04, R.id.weather_05 };
		int[] weaatherLowTemparetureIDs = { R.id.weather_temp_low_01,
				R.id.weather_temp_low_02, R.id.weather_temp_low_03,
				R.id.weather_temp_low_04, R.id.weather_temp_low_05 };
		int[] weaatherHighTemparetureIDs = { R.id.weather_temp_high_01,
				R.id.weather_temp_high_02, R.id.weather_temp_high_03,
				R.id.weather_temp_high_04, R.id.weather_temp_high_05 };
		int[] weaatherImageeIDs = { R.id.weather_image_01,
				R.id.weather_image_02, R.id.weather_image_03,
				R.id.weather_image_04, R.id.weather_image_05 };
		for (int i = 0; i < WeatherZone.FORECAST_INFO_MAX_SIZE; i++) {
			foreCastWeatherTitle[i] = (TextView) findViewById(weaatherTitleIDs[i]);
			foreCastWeatherDate[i] = (TextView) findViewById(weaatherDateIDs[i]);
			foreCastWeather[i] = (TextView) findViewById(weaatherIDs[i]);
			foreCastWeatherLowTempareture[i] = (TextView) findViewById(weaatherLowTemparetureIDs[i]);
			foreCastWeatherHighTempareture[i] = (TextView) findViewById(weaatherHighTemparetureIDs[i]);
			foreCastWeatherImage[i] = (ImageView) findViewById(weaatherImageeIDs[i]);
		}

		mTvErrorMessage = (TextView) findViewById(R.id.textview_error_message);
		mIvWeather0 = (ImageView) findViewById(R.id.imageview_weather_info_0);

		mEtAreaOfCity = (EditText) findViewById(R.id.edittext_area);
		mEtAreaOfCity.setHint("Enter City Name");

		mBtSearch = (Button) findViewById(R.id.search_button);
		mBtSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String _location = mEtAreaOfCity.getText().toString();
				if (!TextUtils.isEmpty(_location)) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(mEtAreaOfCity.getWindowToken(),
							0);
					searchByPlaceName(_location);
					showProgressDialog();
				} else {
					Toast.makeText(getApplicationContext(),
							"Please Enter Your Location", 1).show();
				}
			}
		});

		mBtGPS = (Button) findViewById(R.id.gps_button);
		mBtGPS.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				searchByGPS();
				showProgressDialog();
			}
		});

		mSearchVisiable = (Button) findViewById(R.id.serach_visiable);
		mSearchVisiable.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LinearLayout searchOption = (LinearLayout) findViewById(R.id.search_layout_test);
				if (searchOption.getVisibility() == View.GONE) {
					searchOption.setVisibility(View.VISIBLE);
					mEtAreaOfCity.setHint("Enter City Name");
				} else if (searchOption.getVisibility() == View.VISIBLE) {
					searchOption.setVisibility(View.GONE);
				}
			}
		});

		mWeatherInfosLayout = (LinearLayout) findViewById(R.id.weather_infos);

		initLayoutVisivility();

		boolean initOption = initWeatherInfo();
		if (!initOption) {
			searchByGPS();
			showProgressDialog();
		}
	}

	private void initLayoutVisivility() {
		// initiated setting status
		String appStateString = getAppSettingState().trim();
		String[] appState = appStateString.split("d");
		if (appState.length == 6) {
			celsiusOrFahrenheit = Integer.parseInt(appState[0].trim());
			for (int i = 1; i < 6; i++) {
				int index = Integer.parseInt(appState[i].trim());
				if (index == 1) {
					forecastViewEnable[i - 1] = true;
				} else {
					forecastViewEnable[i - 1] = false;
				}
			}

			int[] forecastLayoutIDs = { R.id.forecastLayout01,
					R.id.forecastLayout02, R.id.forecastLayout03,
					R.id.forecastLayout04, R.id.forecastLayout05 };

			// String tt = "" + forecastViewEnable[0] + forecastViewEnable[1]
			// + forecastViewEnable[2] + forecastViewEnable[3]
			// + forecastViewEnable[4];
			// Toast.makeText(getApplicationContext(), tt, Toast.LENGTH_LONG)
			// .show();

			for (int i = 0; i < 5; i++) {
				LinearLayout forecastViewLayout = (LinearLayout) findViewById(forecastLayoutIDs[i]);
				if (!forecastViewEnable[i]) {
					forecastViewLayout.setVisibility(View.GONE);
				} else {
					forecastViewLayout.setVisibility(View.VISIBLE);
				}
			}
		}
	}

	public String getAppSettingState() {
		try {
			if (!weatherInfoDirectory.exists()) {
				weatherInfoDirectory.mkdir();
				weatherInfoFile.createNewFile();
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

	private boolean initWeatherInfo() {
		String allInfo = "";
		try {
			if (!weatherInfoDirectory.exists()) {
				weatherInfoDirectory.mkdir();
				weatherInfoFile.createNewFile();
				return false;
			}
			if (!weatherInfoFile.exists()) {
				weatherInfoFile.createNewFile();
				return false;
			}

		} catch (Exception e) {
			Toast.makeText(getBaseContext(), "init : " + e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}

		allInfo = FileUtils.readFileData(getApplicationContext(),
				weatherInfoFile);
		allInfo = allInfo.trim();
		if (allInfo.length() > 0) {
			return setWeatherInfo(allInfo);
		} else {
			return false;
		}
	}

	private void buildAllWQeatherInfoHashMap(String[] parseInfo) {
		allWeatherInfoHashMap.clear();
		for (int i = 0; i < parseInfo.length; i++) {
			String tempTitleZone[] = parseInfo[i].split("___");
			allWeatherInfoHashMap.put(tempTitleZone[0].trim(),
					tempTitleZone[1].trim());
		}
	}

	private void checkInfoIsUpdate(String dateString) {
		try {
			Date date = myDateFormat.parse(dateString);

			// milliseconds
			long different = (new Date()).getTime() - date.getTime();
			different /= 60000;
			long days = different / (60 * 24);
			different = different - days * (60 * 24);
			long hours = different / 60;

			String diffStringTitle = "Last Update ";
			if (days > 0) {
				diffStringTitle += days + " Days, ";
			}
			if (hours > 0) {
				diffStringTitle += hours + " Hours";
			}

			if (days > 0 || hours >= 4) {
				diffStringTitle += " ago!";
				mTvErrorMessage.setText(diffStringTitle);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean setWeatherInfo(String allInfo) {
		String[] parseInfo = allInfo.split("---");
		if (parseInfo.length != 29) {
			return false;
		} else {

			buildAllWQeatherInfoHashMap(parseInfo);
			checkInfoIsUpdate(allWeatherInfoHashMap.get(Keys.keys[2]));

			// header info
			mTvTitle.setText(allWeatherInfoHashMap.get(Keys.keys[0]));
			int tempValue = Integer.parseInt(allWeatherInfoHashMap
					.get(Keys.keys[1]));
			String unitText = "ºC";
			if (celsiusOrFahrenheit == 1) {
				tempValue = (tempValue * 9) / 5 + 32;
				unitText = "ºF";
			}
			mTvCurretnTempareture.setText(tempValue + unitText);
			mTvCurretnDate.setText(allWeatherInfoHashMap.get(Keys.keys[2]));
			mTvWeatherHeaderVisibility.setText("Visibility : "
					+ allWeatherInfoHashMap.get(Keys.keys[3]));

			// base info
			mTvWeatherHumidity.setText(allWeatherInfoHashMap.get(Keys.keys[4]));
			mTvWeatherWind.setText(allWeatherInfoHashMap.get(Keys.keys[5]));
			mTvWeatherWindDirection.setText(allWeatherInfoHashMap
					.get(Keys.keys[6]));
			mTvWeatherWindSpeed
					.setText(allWeatherInfoHashMap.get(Keys.keys[7]));
			mTvWeatherPressure.setText(allWeatherInfoHashMap.get(Keys.keys[8]));
			mTvWeatherVisibility.setText(allWeatherInfoHashMap
					.get(Keys.keys[3]));

			// five days forecast info
			for (int i = 9; i < 29; i += 4) {
				foreCastWeatherTitle[(i - 9) / 4].setText("FORECAST : 0"
						+ ((i - 9) / 4 + 1) + " DAY");
				foreCastWeatherDate[(i - 9) / 4].setText(allWeatherInfoHashMap
						.get(Keys.keys[i]));
				foreCastWeather[(i - 9) / 4].setText(allWeatherInfoHashMap
						.get(Keys.keys[i + 1]));
				tempValue = Integer.parseInt(allWeatherInfoHashMap
						.get(Keys.keys[i + 2]));
				unitText = "ºC";
				if (celsiusOrFahrenheit == 1) {
					tempValue = (9 * tempValue) / 5 + 32;
					unitText = "ºF";
				}
				foreCastWeatherLowTempareture[(i - 9) / 4].setText(tempValue
						+ unitText);
				tempValue = Integer.parseInt(allWeatherInfoHashMap
						.get(Keys.keys[i + 3]));
				unitText = "ºC";
				if (celsiusOrFahrenheit == 1) {
					tempValue = (9 * tempValue) / 5 + 32;
					unitText = "ºF";
				}
				foreCastWeatherHighTempareture[(i - 9) / 4].setText(tempValue
						+ unitText);
			}
			return true;
		}
	}

	public void saveAllWeatherInfo(WeatherInfo weatherInfo) {

		try {
			if (!weatherInfoDirectory.exists()) {
				weatherInfoDirectory.mkdir();
			}
			if (!weatherInfoFile.exists()) {
				weatherInfoFile.createNewFile();
			}

			String allWeatherInfoText = "";

			// header info save
			allWeatherInfoText = allWeatherInfoText + Keys.keys[0] + "___"
					+ weatherInfo.getTitle().substring(16) + "---\n";
			allWeatherInfoText = allWeatherInfoText + Keys.keys[1] + "___"
					+ weatherInfo.getCurrentTemp() + "---\n";
			allWeatherInfoText = allWeatherInfoText
					+ Keys.keys[2]
					+ "___"
					+ weatherInfo.getCurrentConditionDate().substring(0,
							weatherInfo.getCurrentConditionDate().length() - 4)
					+ "---\n";
			allWeatherInfoText = allWeatherInfoText + Keys.keys[3] + "___"
					+ weatherInfo.getAtmosphereVisibility() + "---\n";

			// base info save
			allWeatherInfoText = allWeatherInfoText + Keys.keys[4] + "___"
					+ weatherInfo.getAtmosphereHumidity() + "---\n";
			allWeatherInfoText = allWeatherInfoText + Keys.keys[5] + "___"
					+ weatherInfo.getWindChill() + "---\n";
			allWeatherInfoText = allWeatherInfoText + Keys.keys[6] + "___"
					+ weatherInfo.getWindDirection() + "---\n";
			allWeatherInfoText = allWeatherInfoText + Keys.keys[7] + "___"
					+ weatherInfo.getWindSpeed() + "---\n";
			allWeatherInfoText = allWeatherInfoText + Keys.keys[8] + "___"
					+ weatherInfo.getAtmospherePressure() + "---\n";

			// five days forecast info save
			for (int i = 9; i < 29; i += 4) {
				final ForecastInfo forecastInfo = weatherInfo
						.getForecastInfoList().get((i - 9) / 4);
				allWeatherInfoText = allWeatherInfoText + Keys.keys[i] + "___"
						+ forecastInfo.getForecastDate() + "---\n";
				allWeatherInfoText = allWeatherInfoText + Keys.keys[i + 1]
						+ "___" + forecastInfo.getForecastText() + "---\n";
				allWeatherInfoText = allWeatherInfoText + Keys.keys[i + 2]
						+ "___" + forecastInfo.getForecastTempLow() + "---\n";
				allWeatherInfoText = allWeatherInfoText + Keys.keys[i + 3]
						+ "___" + forecastInfo.getForecastTempHigh() + "---\n";

			}
			FileUtils.writeFileData(getApplicationContext(), weatherInfoFile,
					allWeatherInfoText);

		} catch (Exception e) {
			Toast.makeText(getBaseContext(), "save : " + e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
	}

	public String getCurrentDate() {
		SimpleDateFormat formatter;

		formatter = new SimpleDateFormat(myDateFormatString,
				Locale.getDefault());
		return formatter.format(new Date());
	}

	@Override
	public void gotWeatherInfo(WeatherInfo weatherInfo) {
		// TODO Auto-generated method stub
		hideProgressDialog();
		LinearLayout searchOption = (LinearLayout) findViewById(R.id.search_layout_test);
		searchOption.setVisibility(View.GONE);
		if (weatherInfo != null) {
			setNormalLayout();
			if (mWeatherZone.getSearchMode() == SEARCH_MODE.GPS) {
				mEtAreaOfCity.setHint("Enter City Name");
			}

			saveAllWeatherInfo(weatherInfo);

			LinearLayout layoutErrorMessage = (LinearLayout) findViewById(R.id.layout_last_update_hint_message);
			layoutErrorMessage.setVisibility(View.GONE);

			LinearLayout layoutTitleMessage = (LinearLayout) findViewById(R.id.layout_title_message);
			layoutTitleMessage.setVisibility(View.VISIBLE);
			LinearLayout layoutWeathrInfoMessage = (LinearLayout) findViewById(R.id.layout_weathr_info_message);
			layoutWeathrInfoMessage.setVisibility(View.VISIBLE);
			LinearLayout weatherInfos = (LinearLayout) findViewById(R.id.weather_infos);
			weatherInfos.setVisibility(View.VISIBLE);

			weatherInfo.getCurrentTemp();

			mTvTitle.setText("" + weatherInfo.getTitle().substring(16));
			int tempValue = weatherInfo.getCurrentTemp();
			String unitText = "ºC";
			if (celsiusOrFahrenheit == 1) {
				unitText = "ºF";
				tempValue = (9 * tempValue) / 5 + 32;
			}
			mTvCurretnTempareture.setText(tempValue + unitText);
			mTvCurretnDate
					.setText(weatherInfo.getCurrentConditionDate().substring(0,
							weatherInfo.getCurrentConditionDate().length() - 4));
			mTvWeatherHeaderVisibility.setText("Visibility : "
					+ weatherInfo.getAtmosphereVisibility());

			mTvWeatherHumidity
					.setText("" + weatherInfo.getAtmosphereHumidity());
			mTvWeatherWind.setText("" + weatherInfo.getWindChill());
			mTvWeatherWindDirection
					.setText("" + weatherInfo.getWindDirection());
			mTvWeatherWindSpeed.setText("" + weatherInfo.getWindSpeed());
			mTvWeatherPressure
					.setText("" + weatherInfo.getAtmospherePressure());
			mTvWeatherVisibility.setText(""
					+ weatherInfo.getAtmosphereVisibility());
			if (weatherInfo.getCurrentConditionIcon() != null) {
				mIvWeather0.setImageBitmap(weatherInfo
						.getCurrentConditionIcon());
			}

			for (int i = 0; i < WeatherZone.FORECAST_INFO_MAX_SIZE; i++) {
				final ForecastInfo forecastInfo = weatherInfo
						.getForecastInfoList().get(i);
				foreCastWeatherTitle[i].setText("FORECAST : 0" + (i + 1)
						+ " DAY");
				foreCastWeatherDate[i].setText(""
						+ forecastInfo.getForecastDate());
				foreCastWeather[i].setText("" + forecastInfo.getForecastText());
				tempValue = forecastInfo.getForecastTempLow();
				unitText = "ºC";
				if (celsiusOrFahrenheit == 1) {
					unitText = "ºF";
					tempValue = (9 * tempValue) / 5 + 32;
				}
				foreCastWeatherLowTempareture[i].setText(tempValue + unitText);
				tempValue = forecastInfo.getForecastTempHigh();
				unitText = "ºC";
				if (celsiusOrFahrenheit == 1) {
					tempValue = (9 * tempValue) / 5 + 32;
					unitText = "ºF";
				}
				foreCastWeatherHighTempareture[i].setText(tempValue + unitText);
				if (forecastInfo.getForecastConditionIcon() != null) {
					foreCastWeatherImage[i].setImageBitmap(forecastInfo
							.getForecastConditionIcon());
				}
			}
		} else {
			setNoResultLayout();
		}
	}

	@Override
	public void onFailConnection(final Exception e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFailParsing(final Exception e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFailFindLocation(final Exception e) {
		// TODO Auto-generated method stub

	}

	private void setNormalLayout() {
		mWeatherInfosLayout.setVisibility(View.VISIBLE);
		mTvTitle.setVisibility(View.VISIBLE);
		mTvErrorMessage.setVisibility(View.INVISIBLE);
	}

	private void setNoResultFoundLayoutChange() {
		LinearLayout layoutErrorMessage = (LinearLayout) findViewById(R.id.layout_last_update_hint_message);
		layoutErrorMessage.setVisibility(View.VISIBLE);

		LinearLayout layoutTitleMessage = (LinearLayout) findViewById(R.id.layout_title_message);
		layoutTitleMessage.setVisibility(View.GONE);
		LinearLayout layoutWeathrInfoMessage = (LinearLayout) findViewById(R.id.layout_weathr_info_message);
		layoutWeathrInfoMessage.setVisibility(View.GONE);
		LinearLayout weatherInfos = (LinearLayout) findViewById(R.id.weather_infos);
		weatherInfos.setVisibility(View.GONE);
	}

	private void setNoResultLayout() {
		if (mWeatherZone.getSearchMode() == SEARCH_MODE.GPS) {
			noResultFoundAlertDialog("Network problem, try again!");
		} else {
			noResultFoundAlertDialog("Check your spelling ...");
		}
		// setNoResultFoundLayoutChange();
		// mTvTitle.setVisibility(View.INVISIBLE);
		// mWeatherInfosLayout.setVisibility(View.INVISIBLE);
		// mTvErrorMessage.setVisibility(View.VISIBLE);
		// mTvErrorMessage.setText("Sorry, no result returned");
		// mProgressDialog.cancel();
	}

	private void noResultFoundAlertDialog(String message) {

		// Build the alert dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("No Result");
		builder.setMessage(message);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int i) {

			}
		});
		Dialog alertDialog = builder.create();
		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.show();

	}

	private void checkGPSService() {
		// Get Location Manager and check for GPS & Network location services
		LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
				|| !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			// Build the alert dialog
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Location Services Not Active");
			builder.setIcon(R.drawable.ic_launcher);
			builder.setMessage("Please Enable Location Services and GPS");
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialogInterface,
								int i) {
							// Show location settings when the user acknowledges
							// the alert dialog
							Intent intent = new Intent(
									Settings.ACTION_LOCATION_SOURCE_SETTINGS);
							startActivity(intent);
						}
					});
			Dialog alertDialog = builder.create();
			alertDialog.setCanceledOnTouchOutside(false);
			alertDialog.show();
		}
	}

	private void searchByGPS() {
		checkGPSService();
		mWeatherZone.setNeedDownloadIcons(true);
		mWeatherZone.setUnit(UNIT.CELSIUS);
		mWeatherZone.setSearchMode(SEARCH_MODE.GPS);
		mWeatherZone.queryWeatherZoneByGPS(getApplicationContext(), this);
	}

	private void searchByPlaceName(String location) {
		mWeatherZone.setNeedDownloadIcons(true);
		mWeatherZone.setUnit(UNIT.CELSIUS);
		mWeatherZone.setSearchMode(SEARCH_MODE.PLACE_NAME);
		mWeatherZone.queryWeatherZoneByPlaceName(getApplicationContext(),
				location, WeatherViewActivity.this);
	}

	private void showProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.cancel();
		}
		mProgressDialog = new ProgressDialog(WeatherViewActivity.this);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.setTitle(getResources().getString(
				R.string.progress_title));
		mProgressDialog.setIcon(R.drawable.ic_launcher);
		mProgressDialog.setMessage(getResources().getString(
				R.string.progress_message));
		mProgressDialog.show();
	}

	private void hideProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.cancel();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
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
			settingInterface();
			return true;
		} else if (id == R.id.action_settings) {
			settingInterface();
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

	public void seeWebSite(View v) {
		Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri
				.parse(WEBSITE_APPS));
		startActivity(intent);
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

	private void settingInterface() {
		Intent in = new Intent(WeatherViewActivity.this,
				WeatherSettingActivity.class);
		startActivity(in);
	}

	@Override
	protected void onResume() {
		initLayoutVisivility();
		// five days forecast info
		boolean isInitPossibleoption = true;
		String allInfo = "";
		try {
			if (!weatherInfoDirectory.exists()) {
				weatherInfoDirectory.mkdir();
				isInitPossibleoption = false;
			}
			if (!weatherInfoFile.exists()) {
				weatherInfoFile.createNewFile();
				isInitPossibleoption = false;
			}

		} catch (Exception e) {
			Toast.makeText(getBaseContext(), "init : " + e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}

		allInfo = FileUtils.readFileData(getApplicationContext(),
				weatherInfoFile);
		allInfo = allInfo.trim();
		String[] parsingData = allInfo.split("---");

		if (isInitPossibleoption && parsingData.length == 29) {
			allInfo = FileUtils.readFileData(getApplicationContext(),
					weatherInfoFile);
			allInfo = allInfo.trim();
			buildAllWQeatherInfoHashMap(parsingData);

			for (int i = 9; i < 29; i += 4) {
				int tempValue = Integer.parseInt(""
						+ allWeatherInfoHashMap.get(Keys.keys[i + 2]));
				String unitText = "ºC";
				if (celsiusOrFahrenheit == 1) {
					unitText = "ºF";
					tempValue = (9 * tempValue) / 5 + 32;
				}
				foreCastWeatherLowTempareture[(i - 9) / 4].setText(tempValue
						+ unitText);
				tempValue = Integer.parseInt(allWeatherInfoHashMap
						.get(Keys.keys[i + 3]));
				unitText = "ºC";
				if (celsiusOrFahrenheit == 1) {
					unitText = "ºF";
					tempValue = (9 * tempValue) / 5 + 32;
				}
				foreCastWeatherHighTempareture[(i - 9) / 4].setText(tempValue
						+ unitText);
			}
			int tempValue = Integer.parseInt(allWeatherInfoHashMap
					.get(Keys.keys[1]));
			String unitText = "ºC";
			if (celsiusOrFahrenheit == 1) {
				unitText = "ºF";
				tempValue = (tempValue * 9) / 5 + 32;
			}
			mTvCurretnTempareture.setText(tempValue + unitText);
		}
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		hideProgressDialog();
		mProgressDialog = null;
		super.onDestroy();
	}
}
