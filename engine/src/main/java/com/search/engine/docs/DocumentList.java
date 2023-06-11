package com.search.engine.docs;

import java.util.ArrayList;

import com.search.engine.docs.Document;

public class DocumentList {

	private ArrayList<Document> documents;

	public DocumentList() {
		documents = new ArrayList<>();
	}

	public ArrayList<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(ArrayList<Document> documents) {
		this.documents = documents;
	}
	
}
