package uk.gov.hmcts.reform.adoption.caseworker.service.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.CaseData;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.Solicitor;
import uk.gov.hmcts.reform.adoption.notification.CommonContent;
import uk.gov.hmcts.reform.adoption.notification.NotificationService;

import java.util.Map;

import static uk.gov.hmcts.reform.adoption.adoptioncase.model.LanguagePreference.ENGLISH;
import static uk.gov.hmcts.reform.adoption.notification.CommonContent.SOLICITOR_NAME;
import static uk.gov.hmcts.reform.adoption.notification.EmailTemplateName.APPLICANT_SOLICITOR_SERVICE;

@Component
@Slf4j
public class SolicitorServiceNotification {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private CommonContent commonContent;

    public void send(final CaseData caseData, final Long caseId) {

        final Solicitor applicantSolicitor = caseData.getApplicant1().getSolicitor();

        log.info("Sending Personal Service email to applicant solicitor.  Case ID: {}", caseId);

        notificationService.sendEmail(
            applicantSolicitor.getEmail(),
            APPLICANT_SOLICITOR_SERVICE,
            templateVars(caseData, caseId),
            ENGLISH
        );
    }

    private Map<String, String> templateVars(final CaseData caseData, final Long caseId) {

        final Map<String, String> templateVars = commonContent.basicTemplateVars(caseData, caseId);
        templateVars.put(SOLICITOR_NAME, caseData.getApplicant1().getSolicitor().getName());
        return templateVars;
    }
}
