package com.search.engine.docs;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
public class CSVReader {
	public CSVReader() {
		
	}
	@CsvBindByName(column = "conceptType")
	private String conceptType;
	
@Override
	public String toString() {
		return "CSVReader [conceptType=" + conceptType + "]";
	}


	public String getConceptType() {
		return conceptType;
	}


	public void setConceptType(String conceptType) {
		this.conceptType = conceptType;
	}

/*
	public String getConceptUri() {
		return conceptUri;
	}


	public void setConceptUri(String conceptUri) {
		this.conceptUri = conceptUri;
	}


	public String getSkillType() {
		return skillType;
	}


	public void setSkillType(String skillType) {
		this.skillType = skillType;
	}


	public String getReuseLevel() {
		return reuseLevel;
	}


	public void setReuseLevel(String reuseLevel) {
		this.reuseLevel = reuseLevel;
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


	public String getHiddenLabels() {
		return hiddenLabels;
	}


	public void setHiddenLabels(String hiddenLabels) {
		this.hiddenLabels = hiddenLabels;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getModifiedDate() {
		return modifiedDate;
	}


	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
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


	public String getInScheme() {
		return inScheme;
	}


	public void setInScheme(String inScheme) {
		this.inScheme = inScheme;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


//	@Override
//	public String toString() {
//		return "CSVReader [conceptType=" + conceptType + ", conceptUri=" + conceptUri + ", skillType=" + skillType
//				+ ", reuseLevel=" + reuseLevel + ", preferredLabel=" + preferredLabel + ", altLabels=" + altLabels
//				+ ", hiddenLabels=" + hiddenLabels + ", status=" + status + ", modifiedDate=" + modifiedDate
//				+ ", scopeNote=" + scopeNote + ", definition=" + definition + ", inScheme=" + inScheme
//				+ ", description=" + description + "]";
//	}
	
	@Override
	public String toString() {
		return "CSVReader [conceptType=" + conceptType + ", conceptUri=" + conceptUri + ", skillType=" + skillType
				+ ", reuseLevel=" + reuseLevel + ", preferredLabel=" + preferredLabel + ", altLabels=" + altLabels
				+ ", hiddenLabels=" + hiddenLabels + ", status=" + status + ", modifiedDate=" 
				+ ", scopeNote=" + scopeNote + ", definition=" + definition + ", inScheme=" + inScheme
				+ ", description=" + description + "]";
	}
	*/
	

}
