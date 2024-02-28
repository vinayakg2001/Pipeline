package com.asis;

import java.util.ArrayList;
import java.util.List;

public class QuaterData_blp {
	private String quarter_name;
	private double _G1;
	private double _G3;
	private double _1A;
	private double _G10;	
	private double _G11;
	private double _1B;
	private double _GST_Refund;
	private double _W1;
	private double _4;
	private double _5A;
	private double _7D;
	private double _ATO_Total_Refund;
	private double _ATO_Reports;
	

	public QuaterData_blp(String quarter_name) {
		this.quarter_name = quarter_name;
		this._G1 = 0.0;
		this._G3 = 0.0;
		_1A = 0.0;
		this._G10 = 0.0;
		this._G11 = 0.0;
		_1B = 0.0;
		this._GST_Refund = 0.0;
		this._W1 = 0.0;
		this._4 = 0.0;
		_5A = 0.0;
		_7D = 0.0;
		this._ATO_Total_Refund = 0.0;
		this._ATO_Reports = 0.0;
	}


	public String getQuarter_name() {
		return quarter_name;
	}


	public void setQuarter_name(String quarter_name) {
		this.quarter_name = quarter_name;
	}


	public double get_G1() {
		return _G1;
	}


	public void set_G1(double _G1) {
		this._G1 = _G1;
	}


	public double get_G3() {
		return _G3;
	}


	public void set_G3(double _G3) {
		this._G3 = _G3;
	}


	public double get_1A() {
		return _1A;
	}


	public void set_1A(double _1a) {
		_1A = _1a;
	}


	public double get_G10() {
		return _G10;
	}


	public void set_G10(double _G10) {
		this._G10 = _G10;
	}


	public double get_G11() {
		return _G11;
	}


	public void set_G11(double _G11) {
		this._G11 = _G11;
	}


	public double get_1B() {
		return _1B;
	}


	public void set_1B(double _1b) {
		_1B = _1b;
	}


	public double get_GST_Refund() {
		return _GST_Refund;
	}


	public void set_GST_Refund(double _GST_Refund) {
		this._GST_Refund = _GST_Refund;
	}


	public double get_W1() {
		return _W1;
	}


	public void set_W1(double _W1) {
		this._W1 = _W1;
	}


	public double get_4() {
		return _4;
	}


	public void set_4(double _4) {
		this._4 = _4;
	}


	public double get_5A() {
		return _5A;
	}


	public void set_5A(double _5a) {
		_5A = _5a;
	}


	public double get_7D() {
		return _7D;
	}


	public void set_7D(double _7d) {
		_7D = _7d;
	}


	public double get_ATO_Total_Refund() {
		return _ATO_Total_Refund;
	}


	public void set_ATO_Total_Refund(double _ATO_Total_Refund) {
		this._ATO_Total_Refund = _ATO_Total_Refund;
	}


	public double get_ATO_Reports() {
		return _ATO_Reports;
	}


	public void set_ATO_Reports(double _ATO_Reports) {
		this._ATO_Reports = _ATO_Reports;
	}
	
	
	
	public void setDefaultData(QuaterData_blp obj) {		
		//qd.setQuarter_name("jun");
		this._G1 = 0.0;
		this._G3 = 0.0;
		_1A = 0.0;
		this._G10 = 0.0;
		this._G11 = 0.0;
		_1B = 0.0;
		this._GST_Refund = 0.0;
		this._W1 = 0.0;
		this._4 = 0.0;
		_5A = 0.0;
		_7D = 0.0;
		this._ATO_Total_Refund = 0.0;
		this._ATO_Reports = 0.0;
		
	}
	

}
