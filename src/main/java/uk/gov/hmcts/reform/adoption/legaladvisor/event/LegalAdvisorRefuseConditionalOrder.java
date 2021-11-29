package uk.gov.hmcts.reform.adoption.legaladvisor.event;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.ccd.sdk.api.CCDConfig;
import uk.gov.hmcts.ccd.sdk.api.ConfigBuilder;
import uk.gov.hmcts.reform.adoption.common.ccd.CcdPageConfiguration;
import uk.gov.hmcts.reform.adoption.common.ccd.PageBuilder;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.CaseData;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.State;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole;

import java.util.List;

import static java.util.Collections.emptyList;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.State.AwaitingLegalAdvisorReferral;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.State.ConditionalOrderRefused;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole.APPLICANT_1_SOLICITOR;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole.CASE_WORKER;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole.LEGAL_ADVISOR;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole.SUPER_USER;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.access.Permissions.CREATE_READ_UPDATE;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.access.Permissions.READ;

@Component
public class LegalAdvisorRefuseConditionalOrder implements CCDConfig<CaseData, State, UserRole> {
    public static final String LEGAL_ADVISOR_REFUSE_CONDITIONAL_ORDER = "legal-advisor-refuse-conditional-order";

    private final List<CcdPageConfiguration> pages = emptyList();

    @Override
    public void configure(ConfigBuilder<CaseData, State, UserRole> configBuilder) {
        final PageBuilder pageBuilder = addEventConfig(configBuilder);
        pages.forEach(page -> page.addTo(pageBuilder));
    }

    private PageBuilder addEventConfig(ConfigBuilder<CaseData, State, UserRole> configBuilder) {
        return new PageBuilder(configBuilder
            .event(LEGAL_ADVISOR_REFUSE_CONDITIONAL_ORDER)
            .forStateTransition(AwaitingLegalAdvisorReferral, ConditionalOrderRefused)
            .name("Conditional Order refused")
            .description("Conditional Order refused")
            .explicitGrants()
            .grant(CREATE_READ_UPDATE, LEGAL_ADVISOR)
            .grant(READ,
                CASE_WORKER,
                SUPER_USER,
                APPLICANT_1_SOLICITOR));
    }

}
