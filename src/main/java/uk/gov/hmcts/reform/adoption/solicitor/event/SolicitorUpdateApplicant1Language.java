package uk.gov.hmcts.reform.adoption.solicitor.event;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.ccd.sdk.api.CCDConfig;
import uk.gov.hmcts.ccd.sdk.api.ConfigBuilder;
import uk.gov.hmcts.reform.adoption.common.ccd.PageBuilder;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.Applicant;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.CaseData;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.State;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole;

import static uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole.APPLICANT_1_SOLICITOR;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole.CASE_WORKER;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole.LEGAL_ADVISOR;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole.SUPER_USER;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.access.Permissions.CREATE_READ_UPDATE;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.access.Permissions.READ;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.access.Permissions.READ_UPDATE;

@Component
public class SolicitorUpdateApplicant1Language implements CCDConfig<CaseData, State, UserRole> {

    public static final String SOLICITOR_UPDATE_APPLICANT_1_LANGUAGE = "solicitor-update-applicant1-language";

    @Override
    public void configure(final ConfigBuilder<CaseData, State, UserRole> configBuilder) {
        new PageBuilder(configBuilder
            .event(SOLICITOR_UPDATE_APPLICANT_1_LANGUAGE)
            .forAllStates()
            .name("Update language")
            .description("Update language")
            .showSummary()
            .explicitGrants()
            .grant(CREATE_READ_UPDATE, APPLICANT_1_SOLICITOR)
            .grant(READ_UPDATE, SUPER_USER)
            .grant(READ,
                CASE_WORKER,
                LEGAL_ADVISOR))
            .page("langPref")
            .pageLabel("Select Language")
            .complex(CaseData::getApplicant1)
                .mandatory(Applicant::getLanguagePreferenceWelsh)
                .done();
    }
}
