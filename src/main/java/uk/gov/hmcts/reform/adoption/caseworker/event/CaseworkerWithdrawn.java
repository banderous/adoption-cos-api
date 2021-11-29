package uk.gov.hmcts.reform.adoption.caseworker.event;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.ccd.sdk.api.CCDConfig;
import uk.gov.hmcts.ccd.sdk.api.ConfigBuilder;
import uk.gov.hmcts.reform.adoption.common.ccd.PageBuilder;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.CaseData;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.State;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole;

import static java.util.EnumSet.allOf;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.State.Withdrawn;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole.CASE_WORKER;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole.LEGAL_ADVISOR;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole.SOLICITOR;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole.SUPER_USER;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.access.Permissions.CREATE_READ_UPDATE;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.access.Permissions.READ;

@Component
public class CaseworkerWithdrawn implements CCDConfig<CaseData, State, UserRole> {
    public static final String CASEWORKER_WITHDRAWN = "caseworker-withdrawn";

    @Override
    public void configure(final ConfigBuilder<CaseData, State, UserRole> configBuilder) {
        new PageBuilder(configBuilder
            .event(CASEWORKER_WITHDRAWN)
            .forStateTransition(allOf(State.class), Withdrawn)
            .name("Withdrawn")
            .description("Withdrawn")
            .explicitGrants()
            .grant(CREATE_READ_UPDATE,
                CASE_WORKER)
            .grant(READ,
                SOLICITOR,
                SUPER_USER,
                LEGAL_ADVISOR));
    }
}
