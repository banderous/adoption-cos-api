package uk.gov.hmcts.reform.adoption.citizen.event;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.ccd.sdk.api.CCDConfig;
import uk.gov.hmcts.ccd.sdk.api.ConfigBuilder;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.CaseData;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.State;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole;

import static uk.gov.hmcts.reform.adoption.adoptioncase.model.State.Holding;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole.APPLICANT_2;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole.SUPER_USER;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.access.Permissions.CREATE_READ_UPDATE;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.access.Permissions.READ;

@Component
public class CitizenApplicant2ConfirmReceipt implements CCDConfig<CaseData, State, UserRole> {

    public static final String APPLICANT_2_CONFIRM_RECEIPT = "applicant2-confirm-receipt";

    @Override
    public void configure(final ConfigBuilder<CaseData, State, UserRole> configBuilder) {

        configBuilder
            .event(APPLICANT_2_CONFIRM_RECEIPT)
            .forStates(Holding)
            .name("Applicant 2 Confirm Receipt")
            .description("Applicant 2 confirms receipt for joint application")
            .grant(CREATE_READ_UPDATE, APPLICANT_2)
            .grant(READ, SUPER_USER);
    }
}

