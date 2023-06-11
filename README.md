                                             **Text analysis and Data Search application with Java**
                                              
This application with PDF of the course module as input, and matches the particular course with its skills and occupation from ESCO(European skills , occupations) database. 

This application serves the extraction of course information (from module descriptions a pdf file), the identification of the most important terms for each course, and matches the particular course with its skills and occupation from ESCO(European skills , occupations) database. 

Pdf file is converted to text and then the text is cleaned using **stop word analysis, stemming and lemmatization**. The most important of each course is identified using **Term Frequency-Inverse Document(TFIDF) algorithm.**

Now, that we have identified the important key words in each module(subject), this term are then mapped to ESCO nomenclature(European Skills, Competences, and Occupations)  to find occupations and skills related to each course.
