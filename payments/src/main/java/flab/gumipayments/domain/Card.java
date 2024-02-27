package flab.gumipayments.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card {

    @Enumerated(EnumType.STRING)
    private CardCompany company;

    private String number;

    @Enumerated(EnumType.STRING)
    private CardType type;
}
