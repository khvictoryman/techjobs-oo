package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.Position;
import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job somejob = jobData.findById(id);
        model.addAttribute("jobIdPost", somejob);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if (errors.hasErrors()) {
            return "new-job";
        } else {
            Job newJob = new Job();
            CoreCompetency c = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());
            newJob.setCoreCompetency(c);

            Employer e = jobData.getEmployers().findById(jobForm.getEmployerId());
            newJob.setEmployer(e);

            Location l = jobData.getLocations().findById(jobForm.getLocationId());
            newJob.setLocation(l);

            PositionType p = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());
            newJob.setPositionType(p);

            newJob.setName(jobForm.getName());

            jobData.add(newJob);

            return String.format("redirect:/job?id=%d", newJob.getId());
        }



    }
}
