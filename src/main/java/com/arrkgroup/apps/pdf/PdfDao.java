package com.arrkgroup.apps.pdf;

import java.util.List;

import com.arrkgroup.apps.model.AssesseesAssessor;

public interface PdfDao {
	public List<AssesseesAssessor> getAssesseesAssessorByCycle(int employeeId,int cycleId);
}
