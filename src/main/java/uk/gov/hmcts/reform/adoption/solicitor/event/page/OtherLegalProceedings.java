package uk.gov.hmcts.reform.adoption.solicitor.event.page;

import uk.gov.hmcts.reform.adoption.common.ccd.CcdPageConfiguration;
import uk.gov.hmcts.reform.adoption.common.ccd.PageBuilder;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.Applicant;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.CaseData;

public class OtherLegalProceedings implements CcdPageConfiguration {

    @Override
    public void addTo(final PageBuilder pageBuilder) {

        pageBuilder
            .page("OtherLegalProceedings")
            .pageLabel("Other legal proceedings")
            .complex(CaseData::getApplicant1)
                .mandatory(Applicant::getLegalProceedings)
                .mandatory(Applicant::getLegalProceedingsDetails, "applicant1LegalProceedings=\"Yes\"")
            .done();
    }
}
