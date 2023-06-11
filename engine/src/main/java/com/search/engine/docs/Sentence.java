package com.search.engine.docs;

import java.util.ArrayList;

public class Sentence {
	private String initial;
	private ArrayList<String> terms;

	public Sentence() {
		this.initial="";
		this.terms=new ArrayList<>();
	}
	public Sentence(String sentence) {
		this();
		initial =sentence;
	}
	public String getInitial() {
		return initial;
	}
	public void setInitial(String initial) {
		this.initial = initial;
	}
	public ArrayList<String> getTerms() {
		return terms;
	}
	public void setTerms(ArrayList<String> terms) {
		this.terms = terms;
	}


}
