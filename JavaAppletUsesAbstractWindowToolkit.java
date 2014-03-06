package windchill;

import java.awt.*; 
import java.awt.event.*; 
import java.applet.*;

/* 
  <applet code="Windchill" width=400 height=600> 
  </applet> 
 */ 

public class Windchill extends Applet implements ItemListener, AdjustmentListener {
  Panel panWind, panTemp, panChill;
	Label showWindchill, showWindSpeed, showTemp;
	Scrollbar scWind, scTemp;
	CheckboxGroup speed, temp;
	Checkbox mph, kph, farenheit, centigrade;

	public void init() {
		setLayout(new GridLayout(3, 1));
		panWind = new Panel();
		panWind.setLayout(new GridLayout(4, 1));
		panTemp = new Panel();
		panTemp.setLayout(new GridLayout(4, 1));
		panChill = new Panel();
		panChill.setLayout(new GridLayout(1, 1));
		add(panWind);
		add(panTemp);
		add(panChill);
		panWind.setBackground(Color.white);
		panTemp.setBackground(Color.lightGray);
		panChill.setBackground(Color.cyan);
		scWind = new Scrollbar(Scrollbar.HORIZONTAL, 10, 3, 5, 53);
		panWind.add(scWind);
		speed = new CheckboxGroup();
		mph = new Checkbox("MPH", speed, true);
		kph = new Checkbox("KPH", speed, false);
		panWind.add(mph);
		panWind.add(kph);
		scTemp = new Scrollbar(Scrollbar.HORIZONTAL, 32, 3, -50, 93);
		panTemp.add(scTemp);
		temp = new CheckboxGroup();
		farenheit = new Checkbox("F", temp, true);
		centigrade = new Checkbox("C", temp, false);
		panTemp.add(farenheit);
		panTemp.add(centigrade);
		showWindSpeed = new Label();
		panWind.add(showWindSpeed);
		showTemp = new Label();
		panTemp.add(showTemp);
		showWindchill = new Label();
		panChill.add(showWindchill);
		mph.addItemListener(this);
		kph.addItemListener(this);
		farenheit.addItemListener(this);
		centigrade.addItemListener(this);
		scWind.addAdjustmentListener(this);
		scTemp.addAdjustmentListener(this);
		computeWindchill();
	}

	public void itemStateChanged(ItemEvent e) {
		computeWindchill();
	}

	public void adjustmentValueChanged(AdjustmentEvent e) {
		computeWindchill();
	}

	public void computeWindchill() {
		double x, windSpeed, windSpeedKm, airTemp, airTempC, wcIndex, wcIndexC;
		boolean windM, tempF;
		int intWindSpeed, intWindSpeedKm, intAirTemp, intAirTempC, intWCIndex, intWCIndexC;
		windM = mph.getState();
		tempF = farenheit.getState();
		windSpeed = (double) scWind.getValue();
		windSpeedKm = windSpeed * 1.60934;
		airTemp = (double) scTemp.getValue();
		airTempC = convertTemp(airTemp);
		x = 0.303439 * Math.sqrt(windSpeed) - 0.0202886 * windSpeed;
		wcIndex = 91.9 - (91.4 - airTemp) * (x + 0.474266);
		wcIndexC = convertTemp(wcIndex);
		intWindSpeed = (int) windSpeed;
		intWindSpeedKm = (int) windSpeedKm;
		intAirTemp = (int) airTemp;
		intAirTempC = (int) airTempC;
		intWCIndex = (int) wcIndex;
		intWCIndexC = (int) wcIndexC;
		if (windM) {
			showWindSpeed.setText("Wind Speed is " + intWindSpeed + " MPH");
		} else {
			showWindSpeed.setText("Wind Speed is " + intWindSpeedKm + " KPH");
		}
		if (tempF) {
			showTemp.setText("Temperature is " + intAirTemp + " F");
			showWindchill.setText("Windchill Index is " + intWCIndex + " F");
		} else {
			showTemp.setText("Temperature is " + intAirTempC + " C");
			showWindchill.setText("Windchill Index is " + intWCIndexC + " C");
		}
		repaint();
	}

	private double convertTemp(double tempFarenheit) {
		return ((tempFarenheit - 32.0) * 5.0 / 9.0);
	}

}

