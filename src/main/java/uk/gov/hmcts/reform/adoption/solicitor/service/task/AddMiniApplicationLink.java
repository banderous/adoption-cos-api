package uk.gov.hmcts.reform.adoption.solicitor.service.task;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.ccd.sdk.api.CaseDetails;
import uk.gov.hmcts.ccd.sdk.type.ListValue;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.CaseData;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.State;
import uk.gov.hmcts.reform.adoption.adoptioncase.task.CaseTask;
import uk.gov.hmcts.reform.adoption.document.model.DivorceDocument;

import java.util.Collection;
import java.util.stream.Stream;

import static uk.gov.hmcts.reform.adoption.document.model.DocumentType.APPLICATION;

@Component
public class AddMiniApplicationLink implements CaseTask {

    @Override
    public CaseDetails<CaseData, State> apply(final CaseDetails<CaseData, State> caseDetails) {
        final CaseData caseData = caseDetails.getData();

        Stream.ofNullable(caseData.getDocumentsGenerated())
            .flatMap(Collection::stream)
            .map(ListValue::getValue)
            .filter(divorceDocument ->
                APPLICATION.equals(divorceDocument.getDocumentType()))
            .map(DivorceDocument::getDocumentLink)
            .findFirst()
            .ifPresent(file -> caseData.getApplication().setMiniApplicationLink(file));
        return caseDetails;
    }
}
