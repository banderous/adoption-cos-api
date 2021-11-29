package uk.gov.hmcts.reform.adoption.bulkaction.ccd.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.ccd.sdk.api.CCDConfig;
import uk.gov.hmcts.ccd.sdk.api.CaseDetails;
import uk.gov.hmcts.ccd.sdk.api.ConfigBuilder;
import uk.gov.hmcts.ccd.sdk.api.callback.AboutToStartOrSubmitResponse;
import uk.gov.hmcts.reform.adoption.bulkaction.ccd.BulkActionPageBuilder;
import uk.gov.hmcts.reform.adoption.bulkaction.ccd.BulkActionState;
import uk.gov.hmcts.reform.adoption.bulkaction.data.BulkActionCaseData;
import uk.gov.hmcts.reform.adoption.bulkaction.service.PronouncementListDocService;
import uk.gov.hmcts.reform.adoption.bulkaction.service.ScheduleCaseService;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole;
import uk.gov.hmcts.reform.ccd.client.model.SubmittedCallbackResponse;

import javax.servlet.http.HttpServletRequest;

import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static uk.gov.hmcts.reform.adoption.bulkaction.ccd.BulkActionState.Listed;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole.CASE_WORKER;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.UserRole.SYSTEMUPDATE;
import static uk.gov.hmcts.reform.adoption.adoptioncase.model.access.Permissions.CREATE_READ_UPDATE;

@Component
@Slf4j
public class CaseworkerPrintPronouncement implements CCDConfig<BulkActionCaseData, BulkActionState, UserRole> {
    public static final String CASEWORKER_PRINT_PRONOUNCEMENT = "caseworker-print-for-pronouncement";

    @Autowired
    private ScheduleCaseService scheduleCaseService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private PronouncementListDocService pronouncementListDocService;

    @Override
    public void configure(final ConfigBuilder<BulkActionCaseData, BulkActionState, UserRole> configBuilder) {

        new BulkActionPageBuilder(configBuilder
            .event(CASEWORKER_PRINT_PRONOUNCEMENT)
            .forState(Listed)
            .name("Print for pronouncement")
            .description("Print for pronouncement")
            .showSummary()
            .showEventNotes()
            .aboutToSubmitCallback(this::aboutToSubmit)
            .submittedCallback(this::submitted)
            .aboutToStartCallback(this::aboutToStart)
            .explicitGrants()
            .grant(CREATE_READ_UPDATE, CASE_WORKER, SYSTEMUPDATE))
            .page("printPronouncement")
            .pageLabel("Print Cases for Pronouncement")
            .mandatory(BulkActionCaseData::getPronouncementJudge, null, "District Judge");
    }

    public SubmittedCallbackResponse submitted(
        CaseDetails<BulkActionCaseData, BulkActionState> bulkCaseDetails,
        CaseDetails<BulkActionCaseData, BulkActionState> beforeDetails
    ) {
        scheduleCaseService.updatePronouncementJudgeDetailsForCasesInBulk(bulkCaseDetails,request.getHeader(AUTHORIZATION));
        return SubmittedCallbackResponse.builder().build();
    }

    public AboutToStartOrSubmitResponse<BulkActionCaseData, BulkActionState> aboutToSubmit(
        CaseDetails<BulkActionCaseData, BulkActionState> bulkCaseDetails,
        CaseDetails<BulkActionCaseData, BulkActionState> bulkCaseDetailsBefore) {

        log.info("Solicitor update contact details about to submit callback invoked");

        final BulkActionCaseData caseData = bulkCaseDetails.getData();

        pronouncementListDocService.generateDocument(bulkCaseDetails, bulkCaseDetails.getData().getBulkListCaseDetails());

        return AboutToStartOrSubmitResponse.<BulkActionCaseData, BulkActionState>builder()
            .data(caseData)
            .build();
    }


    public AboutToStartOrSubmitResponse<BulkActionCaseData, BulkActionState> aboutToStart(
        final CaseDetails<BulkActionCaseData, BulkActionState> bulkCaseDetails
    ) {
        final BulkActionCaseData caseData = bulkCaseDetails.getData();

        if (null == caseData.getPronouncementJudge()) {
            caseData.setPronouncementJudge("District Judge");
        }

        return AboutToStartOrSubmitResponse.<BulkActionCaseData, BulkActionState>builder()
            .data(caseData)
            .build();
    }
}
