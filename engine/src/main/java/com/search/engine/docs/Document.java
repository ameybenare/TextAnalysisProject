package com.search.engine.docs;

import java.util.ArrayList;

import com.search.engine.docs.Sentence;

public class Document {
	private String text;
	private ArrayList<Sentence> sentences;
	
	public Document() {
		sentences = new ArrayList<>();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ArrayList<Sentence> getSentences() {
		return sentences;
	}

	public void setSentences(ArrayList<Sentence> sentences) {
		this.sentences = sentences;
	}
	// TODO Add Term Frequency
	
}
