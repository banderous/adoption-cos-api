package uk.gov.hmcts.reform.adoption.systemupdate.event;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.ccd.sdk.api.CCDConfig;
import uk.gov.hmcts.ccd.sdk.api.ConfigBuilder;
import uk.gov.hmcts.reform.adoption.divorcecase.model.CaseData;
import uk.gov.hmcts.reform.adoption.divorcecase.model.State;
import uk.gov.hmcts.reform.adoption.divorcecase.model.UserRole;

import static uk.gov.hmcts.reform.adoption.divorcecase.model.UserRole.SYSTEMUPDATE;
import static uk.gov.hmcts.reform.adoption.divorcecase.model.access.Permissions.CREATE_READ_UPDATE;

@Component
public class SystemMigrateCase implements CCDConfig<CaseData, State, UserRole> {

    public static final String SYSTEM_MIGRATE_CASE = "system-migrate-case";

    @Override
    public void configure(final ConfigBuilder<CaseData, State, UserRole> configBuilder) {
        configBuilder
            .event(SYSTEM_MIGRATE_CASE)
            .forAllStates()
            .name("Migrate case data")
            .description("Migrate case data to the latest version")
            .grant(CREATE_READ_UPDATE, SYSTEMUPDATE);
    }

}
