package uk.gov.hmcts.reform.adoption.adoptioncase.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.gov.hmcts.ccd.sdk.api.CCD;
import uk.gov.hmcts.reform.adoption.adoptioncase.model.access.DefaultAccess;

import static uk.gov.hmcts.ccd.sdk.type.FieldType.Email;
import static uk.gov.hmcts.ccd.sdk.type.FieldType.PhoneUK;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
@Builder
public class SocialWorker {

    @CCD(label = "Social Worker Name",
        access = {DefaultAccess.class})
    private String socialWorkerName;

    @CCD(label = "Social Worker PhoneNumber",
        typeOverride = PhoneUK,
        access = {DefaultAccess.class}
    )
    private String socialWorkerPhoneNumber;

    @CCD(label = "Social Worker Email",
        access = {DefaultAccess.class})
    private String socialWorkerEmail;

    @CCD(label = "Social Worker Team Email",
        typeOverride = Email,
        access = {DefaultAccess.class}
    )
    private String socialWorkerTeamEmail;
}
