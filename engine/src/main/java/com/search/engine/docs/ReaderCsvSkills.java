package com.search.engine.docs;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
public class ReaderCsvSkills {
	@CsvBindByName(column = "conceptType")
	private String conceptType;
	
	@CsvBindByName(column = "conceptUri")
	private String conceptUri;

	@CsvBindByName(column = "altLabels")
	private String altLabels;
	@CsvBindByName(column = "preferredLabel")
	private String preferredLabel;
	@CsvBindByName(column = "description")
	private String description;
	
	@Override
	public String toString() {
		return "CSVReader [conceptType=" + conceptType + "]";
	}	

	
	public String getConceptUri() {
		return conceptUri;
	}


	public void setConceptUri(String conceptUri) {
		this.conceptUri = conceptUri;
	}


	public String getAltLabels() {
		return altLabels;
	}


	public void setAltLabels(String altLabels) {
		this.altLabels = altLabels;
	}


	public String getPreferredLabel() {
		return preferredLabel;
	}


	public void setPreferredLabel(String preferredLabel) {
		this.preferredLabel = preferredLabel;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	/*
	@CsvBindByName(column = 2)
	private String skillType;
	@CsvBindByName(column = 3)
	private String reuseLevel;
	@CsvBindByName(column = 4)
	private String preferredLabel;
	@CsvBindByName(column = "altLabels")
	private String altLabels;
	@CsvBindByName(column = 6)
	private String hiddenLabels;
	@CsvBindByName(column = 7)
	private String status;
	//@CsvBindByName(column = 8)
	//private String modifiedDate;
	@CsvBindByName(column = 9)
	private String scopeNote;
	@CsvBindByName(column = 10)
	private String definition;
	@CsvBindByName(column = 11)
	private String inScheme;
	@CsvBindByName(column = 12)
	private String description;
	
	*/
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
