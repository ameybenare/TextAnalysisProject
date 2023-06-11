package com.search.engine.docs;

import com.opencsv.bean.CsvBindByName;

public class ReaderCsvOccupations {

	@CsvBindByName(column = "conceptType")
	private String conceptType;
	
	@CsvBindByName(column = "conceptUri")
	private String conceptUri;
	
	@CsvBindByName(column = "iscoGroup")
	private Integer iscoGroup;
	
	@CsvBindByName(column = "preferredLabel")
	private String preferredLabel;
	
	@CsvBindByName(column = "altLabels")
	private String altLabels;
	
	@CsvBindByName(column = "regulatedProfessionNote")
	private String regulatedProfessionNote;
	
	@CsvBindByName(column = "scopeNote")
	private String scopeNote;
	
	@CsvBindByName(column = "definition")
	private String definition;
	
	@CsvBindByName(column = "description")
	private String description;
	
	@CsvBindByName(column = "code")
	private String code;

	public String getConceptType() {
		return conceptType;
	}

	public void setConceptType(String conceptType) {
		this.conceptType = conceptType;
	}

	public String getConceptUri() {
		return conceptUri;
	}

	public void setConceptUri(String conceptUri) {
		this.conceptUri = conceptUri;
	}

	public Integer getIscoGroup() {
		return iscoGroup;
	}

	public void setIscoGroup(Integer iscoGroup) {
		this.iscoGroup = iscoGroup;
	}

	public String getPreferredLabel() {
		return preferredLabel;
	}

	public void setPreferredLabel(String preferredLabel) {
		this.preferredLabel = preferredLabel;
	}

	public String getAltLabels() {
		return altLabels;
	}

	public void setAltLabels(String altLabels) {
		this.altLabels = altLabels;
	}

	public String getRegulatedProfessionNote() {
		return regulatedProfessionNote;
	}

	public void setRegulatedProfessionNote(String regulatedProfessionNote) {
		this.regulatedProfessionNote = regulatedProfessionNote;
	}

	public String getScopeNote() {
		return scopeNote;
	}

	public void setScopeNote(String scopeNote) {
		this.scopeNote = scopeNote;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
		

	
}
