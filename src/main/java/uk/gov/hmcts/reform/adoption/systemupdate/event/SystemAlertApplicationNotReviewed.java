package uk.gov.hmcts.reform.adoption.systemupdate.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.ccd.sdk.api.CCDConfig;
import uk.gov.hmcts.ccd.sdk.api.CaseDetails;
import uk.gov.hmcts.ccd.sdk.api.ConfigBuilder;
import uk.gov.hmcts.ccd.sdk.api.callback.AboutToStartOrSubmitResponse;
import uk.gov.hmcts.ccd.sdk.type.YesOrNo;
import uk.gov.hmcts.reform.adoption.citizen.notification.JointApplicationOverdueNotification;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.CaseData;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.State;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole;

import static uk.gov.hmcts.reform.adoption.adoptioncase.model.State.AwaitingApplicant2Response;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole.SYSTEMUPDATE;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.access.Permissions.CREATE_READ_UPDATE;

@Component
public class SystemAlertApplicationNotReviewed implements CCDConfig<CaseData, State, UserRole> {

    public static final String SYSTEM_APPLICATION_NOT_REVIEWED = "system-application-not-reviewed";

    @Autowired
    private JointApplicationOverdueNotification jointApplicationOverdueNotification;

    @Override
    public void configure(final ConfigBuilder<CaseData, State, UserRole> configBuilder) {

        configBuilder
            .event(SYSTEM_APPLICATION_NOT_REVIEWED)
            .forState(AwaitingApplicant2Response)
            .name("Alert Applicant 1")
            .description("Alert Applicant 1 that Application has not been reviewed")
            .grant(CREATE_READ_UPDATE, SYSTEMUPDATE)
            .retries(120, 120)
            .aboutToSubmitCallback(this::aboutToSubmit);
    }

    public AboutToStartOrSubmitResponse<CaseData, State> aboutToSubmit(CaseDetails<CaseData, State> details,
                                                                       CaseDetails<CaseData, State> beforeDetails) {

        CaseData data = details.getData();

        jointApplicationOverdueNotification.sendApplicationNotReviewedEmail(data, details.getId());

        data.getApplication().setOverdueNotificationSent(YesOrNo.YES);

        return AboutToStartOrSubmitResponse.<CaseData, State>builder()
            .data(data)
            .build();
    }
}
