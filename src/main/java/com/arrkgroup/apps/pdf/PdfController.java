package com.arrkgroup.apps.pdf;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.arrkgroup.apps.assessor.assessorassessment.AssessorAssessmentService;
import com.arrkgroup.apps.form.SectionConsolidatedBean;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Section;
import com.arrkgroup.apps.service.ModelObjectService;

/**
 * A Spring controller that allows the users to download a PDF document
 * generated by the iText library.
 *
 * @author www.codejava.net
 *
 */
@Controller
public class PdfController {

	@Autowired
	ModelObjectService modelObjectService;

	@Autowired
	PdfService pdfService;

	@Autowired
	AssessorAssessmentService assessorAssessmentService;
    /**
     * Handle request to download a PDF document
     */
    @RequestMapping(value = "report/downloadPDF", method = RequestMethod.GET)
    public ModelAndView downloadPdf(Model model) {

    	List<Section> allSections=modelObjectService.getAllSections();
    	List allSectionAssessmentScore=new ArrayList();
    	for(AssesseesAssessor assesseesAssessor :(List<AssesseesAssessor>)pdfService.getAssesseesAssessorByCycle(597, 1))
    	{
    		List<SectionConsolidatedBean> list=assessorAssessmentService.findById(String.valueOf(assesseesAssessor.getAssessorId().getId()), String.valueOf(assesseesAssessor.getRoleId().getId()), assesseesAssessor.getProjectId().getId(), assesseesAssessor.getId());
    		allSectionAssessmentScore.add(list);

    	}

    	model.addAttribute("allSectionAssessmentScore", allSectionAssessmentScore);
    	model.addAttribute("empid", 597);
    	model.addAttribute("empname", modelObjectService.findEmployeeById(597).getFullname());
    	model.addAttribute("cycle", modelObjectService.findCycleById(1).getDescription());

       // return a view which will be resolved by a pdf view resolver
        return new ModelAndView("pdfView", "allSections", allSections);
    }







}