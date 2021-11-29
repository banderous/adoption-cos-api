package uk.gov.hmcts.reform.adoption.solicitor.event;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.ccd.sdk.api.CCDConfig;
import uk.gov.hmcts.ccd.sdk.api.ConfigBuilder;
import uk.gov.hmcts.reform.adoption.common.ccd.PageBuilder;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.CaseData;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.ConditionalOrder;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.State;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole;

import static uk.gov.hmcts.reform.adoption.adoptioncase.model.State.AwaitingClarification;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.State.ClarificationSubmitted;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole.APPLICANT_1_SOLICITOR;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole.CASE_WORKER;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole.LEGAL_ADVISOR;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole.SUPER_USER;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.access.Permissions.CREATE_READ_UPDATE;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.access.Permissions.READ;

@Component
public class SolicitorSubmitClarification implements CCDConfig<CaseData, State, UserRole> {
    public static final String SOLICITOR_SUBMIT_CLARIFICATION = "solicitor-submit-clarification";

    @Override
    public void configure(final ConfigBuilder<CaseData, State, UserRole> configBuilder) {
        new PageBuilder(configBuilder
            .event(SOLICITOR_SUBMIT_CLARIFICATION)
            .forStateTransition(AwaitingClarification, ClarificationSubmitted)
            .name("Submit clarification for CO")
            .description("Submit clarification for conditional order")
            .showSummary()
            .explicitGrants()
            .grant(CREATE_READ_UPDATE, APPLICANT_1_SOLICITOR)
            .grant(READ,
                CASE_WORKER,
                SUPER_USER,
                LEGAL_ADVISOR))
            .page("submitClarificationForCO")
            .pageLabel("Submit clarification for conditional order")
            .complex(CaseData::getConditionalOrder)
                .readonly(ConditionalOrder::getRefusalDecision)
                .readonly(ConditionalOrder::getRefusalRejectionReason)
                .readonly(ConditionalOrder::getRefusalClarificationAdditionalInfo)
                .mandatory(ConditionalOrder::getClarificationResponse)
                .mandatory(ConditionalOrder::getClarificationUploadDocuments)
            .done();
    }

}
