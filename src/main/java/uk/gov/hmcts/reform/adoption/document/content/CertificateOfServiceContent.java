package uk.gov.hmcts.reform.adoption.document.content;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.CaseData;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.CtscContactDetails;

import java.util.HashMap;
import java.util.Map;

import static uk.gov.hmcts.reform.adoption.document.content.DocmosisTemplateConstants.CASE_REFERENCE;
import static uk.gov.hmcts.reform.adoption.document.content.DocmosisTemplateConstants.PETITIONER_FULL_NAME;
import static uk.gov.hmcts.reform.adoption.document.content.DocmosisTemplateConstants.RESPONDENT_FULL_NAME;

@Component
@Slf4j
public class CertificateOfServiceContent {

    @Value("${court.locations.serviceCentre.serviceCentreName}")
    private String serviceCentre;

    @Value("${court.locations.serviceCentre.centreName}")
    private String centreName;

    @Value("${court.locations.serviceCentre.poBox}")
    private String poBox;

    @Value("${court.locations.serviceCentre.town}")
    private String town;

    @Value("${court.locations.serviceCentre.postCode}")
    private String postcode;

    @Value("${court.locations.serviceCentre.email}")
    private String email;

    @Value("${court.locations.serviceCentre.phoneNumber}")
    private String phoneNumber;

    public Map<String, Object> apply(final CaseData caseData, final Long ccdCaseReference) {

        final Map<String, Object> templateContent = new HashMap<>();

        log.info("For ccd case reference {} and type(divorce/dissolution) {} ", ccdCaseReference, caseData.getDivorceOrDissolution());

        templateContent.put(CASE_REFERENCE, ccdCaseReference);
        templateContent.put(PETITIONER_FULL_NAME, caseData.getApplication().getMarriageDetails().getApplicant1Name());
        templateContent.put(RESPONDENT_FULL_NAME, caseData.getApplication().getMarriageDetails().getApplicant2Name());

        final var ctscContactDetails = CtscContactDetails
            .builder()
            .centreName(centreName)
            .emailAddress(email)
            .serviceCentre(serviceCentre)
            .poBox(poBox)
            .town(town)
            .postcode(postcode)
            .phoneNumber(phoneNumber)
            .build();

        templateContent.put("ctscContactDetails", ctscContactDetails);

        return templateContent;
    }
}
