package uk.gov.hmcts.reform.adoption.adoptioncase.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import uk.gov.hmcts.ccd.sdk.api.HasLabel;

@Getter
@AllArgsConstructor
public enum Gender implements HasLabel {

    @JsonProperty("male")
    MALE("Male"),

    @JsonProperty("female")
    FEMALE("Female"),

    @JsonProperty("intersex")
    INTERSEX("Intersex"),

    @JsonProperty("notGiven")
    NOT_GIVEN("Not given");

    private final String label;
}
