package uk.gov.hmcts.reform.adoption.adoptioncase.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.gov.hmcts.ccd.sdk.api.CCD;
import uk.gov.hmcts.reform.adoption.document.model.DivorceDocument;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DivorceGeneralOrder {

    @CCD(
        label = "General Order"
    )
    private DivorceDocument generalOrderDocument;

    @CCD(
        label = "General Order Parties"
    )
    private Set<GeneralOrderDivorceParties> generalOrderDivorceParties;
}
